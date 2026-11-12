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






package config;

import io.restassured.response.Response;
import io.restassured.http.ContentType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static io.restassured.RestAssured.given;

public class VaultCredentialUtil {

    public static SecurityCredentialsResponse getCredentialFromVault(
            String vaultUrl,
            String vaultToken,
            String mount,
            String secretPath,
            String usernameSubKey,
            String passwordSubKey) {

        String endpoint = "/v1/" + mount
                + "/data/" + secretPath;

        Response response = given()
                .baseUri(vaultUrl)
                .header("X-Vault-Token", vaultToken)
                .accept(ContentType.JSON)
                .when()
                .get(endpoint);

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Vault retrieval failed. HTTP status: "
                    + response.statusCode());
        }

        JsonObject root = JsonParser.parseString(
                response.asString()).getAsJsonObject();

        JsonObject secrets = root
                .getAsJsonObject("data")
                .getAsJsonObject("data");

        if (!secrets.has(usernameSubKey)
                || !secrets.has(passwordSubKey)) {
            throw new RuntimeException(
                    "Required Vault Subkeys are missing");
        }

        SecurityCredentialsResponse credentials =
                new SecurityCredentialsResponse();

        credentials.setUserName(
                secrets.get(usernameSubKey).getAsString());

        credentials.setUserAccessPhrase(
                secrets.get(passwordSubKey).getAsString());

        return credentials;
    }
}




