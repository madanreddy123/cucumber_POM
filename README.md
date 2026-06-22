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


  // Helper method to clean text (remove decimals, $, %, etc.)
private String cleanTextForSearch(String text) {
    if (text == null || text.isEmpty()) {
        return "";
    }

    // Remove currency symbols, percentage, etc.
    String cleaned = text.replaceAll("[$,%\\s]", "");

    // Remove decimal part (.00, .xx)
    cleaned = cleaned.replaceAll("\\.\\d+", "");

    // Handle cases like "100.00%" -> "100", "$0.00" -> "0"
    // Also trim any remaining whitespace
    return cleaned.trim();
}

// Helper method to check if the original row/cell is still visible after search
private boolean isRowVisibleAfterSearch(String columnLocator, String expectedText) {
    try {
        // Re-find the element - if it still exists and is displayed
        WebElement element = driver.findElement(By.xpath(columnLocator));
        return element.isDisplayed();
    } catch (NoSuchElementException e) {
        return false;
    }
}