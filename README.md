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


document.evaluate("//a[text() = 'CERTIFICATIONS']", document, null, 



// Retrieve the credential-tokens secret from Vault
FSAVaultReadResponse vaultSecretResponse =
        securityCredentialsClient.retrieveFsaVaultSecret(
                "ap109996/dev/credential-tokens"
        );

// Get the list of Vault secret entries
List<FSAVaultBean> vaultBeans =
        vaultSecretResponse.getFsaVaultBeanList();

// Extract the username and password
String username = null;
String password = null;

for (FSAVaultBean bean : vaultBeans) {

    // Replace these getter names with the actual getters
    // available in your FSAVaultBean class.
    String key = bean.getKey();
    String value = bean.getValue();

    if ("YOUR_USERNAME_SUBKEY".equals(key)) {
        username = value;
    }

    if ("YOUR_PASSWORD_SUBKEY".equals(key)) {
        password = value;
    }
}

// Check that both credentials were retrieved
if (username == null || password == null) {
    throw new IllegalStateException(
            "Username or password subkey not found in credential-tokens"
    );
}

// Set the credentials in the environment
env.setFsaDbUsername(username);
env.setFsaDbUserAccessPhrase(password);


