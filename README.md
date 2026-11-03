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






import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

import java.util.Map;

public class TimestampFilter {

    public static void main(String[] args) {

        // 1. API request
        Response response = RestAssured
                .given()
                .when()
                .get("YOUR_API_ENDPOINT");

        // 2. Validate response
        System.out.println("Status Code: " + response.getStatusCode());

        // 3. Print complete response body
        System.out.println("COMPLETE RESPONSE:");
        response.prettyPrint();

        // 4. Convert response to JsonPath
        JsonPath json = response.jsonPath();

        // 5. Get all root-level keys and values
        Map<String, Object> body = json.getMap("$");

        // 6. Filter by year and month
        String targetMonth = "2026-07";

        System.out.println("\nMATCHING RECORDS:");

        for (Map.Entry<String, Object> entry : body.entrySet()) {

            Object value = entry.getValue();

            // Check whether the value is a nested JSON object
            if (value instanceof Map<?, ?>) {

                Map<?, ?> nested = (Map<?, ?>) value;

                Object timestamp = nested.get("createdTimestamp");

                // Check timestamp
                if (timestamp != null &&
                    timestamp.toString().startsWith(targetMonth)) {

                    System.out.println("Key: " + entry.getKey());

                    // Print every field separately
                    for (Map.Entry<?, ?> field : nested.entrySet()) {

                        System.out.println(
                            field.getKey() + " : " + field.getValue()
                        );
                    }

                    System.out.println("------------------------");
                }
            }
        }
    }
}
