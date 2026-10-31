var element = document.evaluate(
    "//a[contains(text(), 'SKILLS')]",
    document,
    null,
    XPathResult.FIRST_ORDERED_NODE_TYPE,
    null
).singleNodeValue;

element.click();


var input = document.evaluate(
    "//input[@name='username']",
    document,
    null,
    XPathResult.FIRST_ORDERED_NODE_TYPE,
    null
).singleNodeValue;

input.value = "myUserName";


var textarea = document.evaluate(
    "//textarea[@id='message']",
    document,
    null,
    XPathResult.FIRST_ORDERED_NODE_TYPE,
    null
).singleNodeValue;

textarea.value = "Hello world!";

var button = document.evaluate(
    "//button[contains(text(), 'Submit')]",
    document,
    null,
    XPathResult.FIRST_ORDERED_NODE_TYPE,
    null
).singleNodeValue;

button.click();


var checkbox = document.evaluate(
    "//input[@type='checkbox' and @id='agree']",
    document,
    null,
    XPathResult.FIRST_ORDERED_NODE_TYPE,
    null
).singleNodeValue;

checkbox.checked = true;


var radio = document.evaluate(
    "//input[@type='radio' and @value='male']",
    document,
    null,
    XPathResult.FIRST_ORDERED_NODE_TYPE,
    null
).singleNodeValue;

radio.checked = true;


document.evaluate("//a[text() = 'CERTIFICATIONS']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue?.click();



export default {
  async fetch(request, env) {
    const url = new URL(request.url);

    try {
      if (request.method === "GET" && url.pathname === "/") {
        return json({ status: "UPVCio payment API is running" });
      }

      if (request.method === "POST" && url.pathname === "/api/checkout") {
        return await createCheckout(request, env);
      }

      if (request.method === "POST" && url.pathname === "/api/stripe/webhook") {
        return await handleWebhook(request, env);
      }

      if (request.method === "GET" && url.pathname === "/success") {
        return new Response(
          "Payment completed. You can return to UPVCio."
        );
      }

      if (request.method === "GET" && url.pathname === "/cancel") {
        return new Response(
          "Payment was cancelled. You can return to UPVCio."
        );
      }

      return new Response("Not found", { status: 404 });
    } catch (error) {
      console.error("Payment API error:", error);
      return json({ error: "Payment service error" }, 500);
    }
  }
};

function json(data, status = 200) {
  return new Response(JSON.stringify(data), {
    status,
    headers: { "Content-Type": "application/json" }
  });
}

async function createCheckout(request, env) {
  if (!env.STRIPE_SECRET_KEY || !env.PRICE_MONTHLY) {
    return json({ error: "Payment settings are missing" }, 500);
  }

  let body;

  try {
    body = await request.json();
  } catch {
    return json({ error: "Invalid request" }, 400);
  }

  const email = typeof body.email === "string"
    ? body.email.trim()
    : "";

  if (email && (
    email.length > 254 ||
    !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
  )) {
    return json({ error: "Invalid email address" }, 400);
  }

  const form = new URLSearchParams();

  form.set("mode", "subscription");
  form.set("line_items[0][price]", env.PRICE_MONTHLY);
  form.set("line_items[0][quantity]", "1");

  form.set(
    "success_url",
    "https://upvcio-payment-api.madanreddy319.workers.dev/success"
  );

  form.set(
    "cancel_url",
    "https://upvcio-payment-api.madanreddy319.workers.dev/cancel"
  );

  if (email) {
    form.set("customer_email", email);
  }

  form.set("subscription_data[metadata][application]", "UPVCio");

  const response = await fetch(
    "https://api.stripe.com/v1/checkout/sessions",
    {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${env.STRIPE_SECRET_KEY}`,
        "Content-Type": "application/x-www-form-urlencoded"
      },
      body: form
    }
  );

  const result = await response.json();

  if (!response.ok) {
    console.error("Stripe checkout error:", result);
    return json({ error: "Could not create payment session" }, 400);
  }

  return json({ checkoutUrl: result.url });
}

async function verifyStripeSignature(payload, signature, secret) {
  if (!signature || !secret) return false;

  const parts = signature.split(",");
  const timestamp = parts.find(p => p.startsWith("t="))?.slice(2);
  const signatures = parts
    .filter(p => p.startsWith("v1="))
    .map(p => p.slice(3));

  if (!timestamp || signatures.length === 0) return false;

  const timestampNumber = Number(timestamp);

  if (
    !Number.isFinite(timestampNumber) ||
    Math.abs(Date.now() / 1000 - timestampNumber) > 300
  ) {
    return false;
  }

  const encoder = new TextEncoder();

  const key = await crypto.subtle.importKey(
    "raw",
    encoder.encode(secret),
    { name: "HMAC", hash: "SHA-256" },
    false,
    ["sign"]
  );

  const signedPayload = `${timestamp}.${payload}`;

  const digest = await crypto.subtle.sign(
    "HMAC",
    key,
    encoder.encode(signedPayload)
  );

  const expected = Array.from(new Uint8Array(digest))
    .map(b => b.toString(16).padStart(2, "0"))
    .join("");

  return signatures.some(signatureValue => {
    if (signatureValue.length !== expected.length) return false;

    let difference = 0;

    for (let i = 0; i < expected.length; i++) {
      difference |= expected.charCodeAt(i) ^ signatureValue.charCodeAt(i);
    }

    return difference === 0;
  });
}

async function handleWebhook(request, env) {
  if (!env.DB || !env.STRIPE_WEBHOOK_SECRET) {
    return json({ error: "Webhook settings are missing" }, 500);
  }

  const payload = await request.text();
  const signature = request.headers.get("Stripe-Signature");

  const valid = await verifyStripeSignature(
    payload,
    signature,
    env.STRIPE_WEBHOOK_SECRET
  );

  if (!valid) {
    return json({ error: "Invalid webhook signature" }, 400);
  }

  const event = JSON.parse(payload);

  if (event.type.startsWith("customer.subscription.")) {
    const subscription = event.data.object;

    const customerId = typeof subscription.customer === "string"
      ? subscription.customer
      : subscription.customer?.id;

    const priceId = subscription.items?.data?.[0]?.price?.id || null;

    await env.DB.prepare(`
      INSERT INTO subscriptions (
        stripe_subscription_id,
        stripe_customer_id,
        customer_email,
        status,
        price_id,
        current_period_end,
        cancel_at_period_end,
        updated_at
      )
      VALUES (?, ?, ?, ?, ?, ?, ?, ?)
      ON CONFLICT(stripe_subscription_id) DO UPDATE SET
        stripe_customer_id = excluded.stripe_customer_id,
        customer_email = excluded.customer_email,
        status = excluded.status,
        price_id = excluded.price_id,
        current_period_end = excluded.current_period_end,
        cancel_at_period_end = excluded.cancel_at_period_end,
        updated_at = excluded.updated_at
    `).bind(
      subscription.id,
      customerId || null,
      subscription.customer_email || null,
      subscription.status,
      priceId,
      subscription.current_period_end || null,
      subscription.cancel_at_period_end ? 1 : 0,
      Math.floor(Date.now() / 1000)
    ).run();
  }

  return json({ received: true });
}
