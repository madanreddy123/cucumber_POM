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


public void validateColDisplayedInCHCard(List<String> expColNameLs) {
    this.waitForTaskCompletion();

    JavascriptExecutor js = (JavascriptExecutor) driver;

    for (String expColName : expColNameLs) {
        String colXpath = "//td[@aria-label='Column " + expColName + "']";

        try {
            // Find all matching cells for this column
            List<WebElement> colCells = driver.findElements(By.xpath(colXpath));
            
            if (colCells.isEmpty()) {
                System.out.println("Column '" + expColName + "' NOT FOUND in the Change History Card");
                continue;
            }

            // Take the first visible/valid cell
            WebElement colEL = colCells.get(0);

            // === SCROLL HORIZONTALLY + VERTICALLY ===
            js.executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'});", 
                colEL
            );

            // Additional horizontal scroll on table container (more reliable for tables)
            js.executeScript(
                "arguments[0].scrollLeft = arguments[0].scrollWidth;",  // scroll to end first
                colEL.findElement(By.xpath("./ancestor::table"))       // or use your table's container
            );

            // Wait for scroll to settle
            Thread.sleep(800); // or use explicit wait

            // Re-find element after scroll (DOM might change)
            colEL = driver.findElement(By.xpath(colXpath));

            if (colEL.isDisplayed()) {
                System.out.println("Column '" + expColName + "' is DISPLAYED in the Change History Card");
            } else {
                System.out.println("Column '" + expColName + "' is NOT visible even after scrolling");
            }

        } catch (Exception e) {
            System.out.println("Error checking column '" + expColName + "': " + e.getMessage());
            e.printStackTrace();
        }
    }
}