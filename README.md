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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;
import java.util.Map;

public class TimestampFilter {

    public static void main(String[] args) throws Exception {

        // STEP 1: Call API
        Response response = RestAssured
                .given()
                .header("Accept", "application/json")
                .when()
                .get("YOUR_API_ENDPOINT");

        // STEP 2: Validate status
        System.out.println("Status Code: " + response.getStatusCode());

        if (response.getStatusCode() != 200) {
            System.out.println("API request failed");
            return;
        }

        // STEP 3: Print complete response body
        System.out.println("COMPLETE RESPONSE:");
        response.prettyPrint();

        // STEP 4: Convert response into JsonNode
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(response.asString());

        // STEP 5: Select required month and year
        String targetMonth = "2026-07";

        System.out.println("\nFILTERED RECORDS:");

        // STEP 6: Read all root-level JSON keys
        Iterator<Map.Entry<String, JsonNode>> fields =
                root.fields();

        while (fields.hasNext()) {

            Map.Entry<String, JsonNode> entry = fields.next();

            String keyName = entry.getKey();

            JsonNode nested = entry.getValue();

            // STEP 7: Check nested JSON objects
            if (nested.isObject()) {

                // STEP 8: Get timestamp
                JsonNode timestamp =
                        nested.get("createdTimestamp");

                // STEP 9: Filter by month and year
                if (timestamp != null &&
                    timestamp.isTextual() &&
                    timestamp.asText().startsWith(targetMonth)) {

                    System.out.println("\nMatching Key: " + keyName);

                    // STEP 10: Print all fields separately
                    Iterator<Map.Entry<String, JsonNode>> details =
                            nested.fields();

                    while (details.hasNext()) {

                        Map.Entry<String, JsonNode> field =
                                details.next();

                        System.out.println(
                                field.getKey() + " : " + field.getValue()
                        );
                    }

                    System.out.println("--------------------------");
                }
            }
        }
    }
}
