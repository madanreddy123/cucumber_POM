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



// 5. Get root-level response
Object body = json.get("$");

// 6. Set target month
String targetMonth = "2026-07";

System.out.println("\nMATCHING RECORDS:");

if (body instanceof Map<?, ?>) {

    Map<?, ?> root = (Map<?, ?>) body;

    for (Map.Entry<?, ?> entry : root.entrySet()) {

        Object value = entry.getValue();

        // Check nested JSON objects
        if (value instanceof Map<?, ?>) {

            Map<?, ?> nested = (Map<?, ?>) value;

            Object timestamp = nested.get("createdTimestamp");

            // Filter by year and month
            if (timestamp != null &&
                timestamp.toString().startsWith(targetMonth)) {

                System.out.println("Key: " + entry.getKey());

                // Print all matching fields
                for (Map.Entry<?, ?> field : nested.entrySet()) {

                    System.out.println(
                        field.getKey() + " : " + field.getValue()
                    );
                }

                System.out.println("-------------------");
            }
        }
    }
}