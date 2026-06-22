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


public void validateTheSearchFieldForAllTheColumns() {
    List<String> columnLocators = Arrays.asList(
        column1, column2, column3, column4, column5,
        column6, column7, column8, column9, column10
    );

    List<String> searchLocators = Arrays.asList(
        search1, search2, search3, search4, search5,
        search6, search7, search8, search9, search10
    );

    for (int i = 0; i < columnLocators.size(); i++) {
        String columnLocator = columnLocators.get(i);
        String searchLocator = searchLocators.get(i);

        WebElement columnElement = driver.findElement(By.xpath(columnLocator));
        String originalText = columnElement.getText().trim();

        if (originalText.isEmpty()) {
            System.out.println("Column " + (i + 1) + " is empty, skipping.");
            continue;
        }

        String searchText = cleanTextForSearch(originalText);
        System.out.println("Column " + (i + 1) + " - Original: '" + originalText + "' | Search: '" + searchText + "'");

        WebElement searchField = driver.findElement(By.xpath(searchLocator));
        searchField.clear();
        searchField.sendKeys(searchText);

        // Special handling for Search Field 3 (dropdown appears)
        if (i == 2) {  // index 2 = search3
            try {
                // Wait a bit for dropdown to appear
                Thread.sleep(1200);
                
                WebElement optionToClick = driver.findElement(By.xpath(clickonoptioncopm));
                if (optionToClick.isDisplayed()) {
                    optionToClick.click();
                    System.out.println("Clicked on dropdown option: CO-PM_ADJ");
                }
            } catch (Exception e) {
                System.out.println("Dropdown option not found or clickable for Column 3: " + e.getMessage());
            }
        } else {
            // Normal wait for other columns
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Validate row is still visible
        boolean rowVisible = isRowVisibleAfterSearch(columnLocator);

        if (rowVisible) {
            System.out.println("✅ Validation passed for Column " + (i + 1));
        } else {
            System.out.println("❌ Validation failed for Column " + (i + 1));
        }

        // Clear search field for next iteration (except maybe last one)
        searchField.clear();
    }
}

// Clean text helper (same as before)
private String cleanTextForSearch(String text) {
    if (text == null || text.isEmpty()) return "";
    
    String cleaned = text.replaceAll("[$,%\\s]", "");     // Remove $, %, spaces
    cleaned = cleaned.replaceAll("\\.\\d+", "");         // Remove .00, .xx etc.
    return cleaned.trim();
}

// Check if row is still visible
private boolean isRowVisibleAfterSearch(String columnLocator) {
    try {
        WebElement element = driver.findElement(By.xpath(columnLocator));
        return element.isDisplayed();
    } catch (NoSuchElementException e) {
        return false;
    }
}