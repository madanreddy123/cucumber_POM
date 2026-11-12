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



if (fsaToken.containsKey(env.dbToken)) {

    credResponsedbToken = fsaToken.get(env.dbToken);
    logger.info("Getting db token from cache");

} else {

    FSAVaultReadResponse vaultSecretResponse =
        securityCredentialsClient.retrieveFsaVaultSecret(
            "ap109996/dev/credential-tokens"
        );

    List<FSAVaultBean> vaultBeans =
        vaultSecretResponse.getFsaVaultBeanList();

    String username = null;
    String password = null;

    for (FSAVaultBean bean : vaultBeans) {
        if ("db_comm_uses_DIT".equalsIgnoreCase(bean.getToken())) {
            username = bean.getUsername();
            password = bean.getPassword();
            break;
        }
    }

    if (username == null || password == null) {
        throw new IllegalStateException("DB credentials not found");
    }

    credResponsedbToken = new SecurityCredentialsResponse();
    credResponsedbToken.setUserName(username);
    credResponsedbToken.setUserAccessPhrase(password);

    fsaToken.put(env.dbToken, credResponsedbToken);

    logger.info("Getting db credentials from Vault");
}

env.setFsaDbUsername(credResponsedbToken.getUserName());
env.setFsaDbUserAccessPhrase(
    credResponsedbToken.getUserAccessPhrase()
);