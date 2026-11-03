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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class TimestampFilter {

    public static void main(String[] args) throws Exception {

        // 1. Call API
        Response response = RestAssured
                .given()
                .when()
                .get("YOUR_API_ENDPOINT");

        // 2. Print status code
        System.out.println("Status Code: " + response.getStatusCode());

        // 3. Print complete response
        System.out.println("COMPLETE RESPONSE:");
        response.prettyPrint();

        // 4. Convert JSON response into Java Map
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> root = mapper.readValue(
                response.asString(),
                new TypeReference<Map<String, Object>>() {}
        );

        // 5. Select month and year
        String targetMonth = "2026-07";

        System.out.println("\nMATCHING RECORDS:");

        // 6. Iterate through root-level keys
        for (Map.Entry<String, Object> entry : root.entrySet()) {

            Object value = entry.getValue();

            // 7. Check nested JSON objects
            if (value instanceof Map<?, ?>) {

                Map<?, ?> nested = (Map<?, ?>) value;

                Object timestamp = nested.get("createdTimestamp");

                // 8. Filter by timestamp
                if (timestamp != null &&
                    timestamp.toString().startsWith(targetMonth)) {

                    System.out.println("\nKey: " + entry.getKey());

                    // 9. Print all matching fields
                    for (Map.Entry<?, ?> field : nested.entrySet()) {

                        System.out.println(
                                field.getKey() + " : " + field.getValue()
                        );
                    }

                    System.out.println("----------------------");
                }
            }
        }
    }
}
