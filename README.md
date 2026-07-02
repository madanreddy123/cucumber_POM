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
            List<WebElement> colCells = driver.findElements(By.xpath(colXpath));
            
            if (colCells.isEmpty()) {
                System.out.println("❌ Column '" + expColName + "' NOT FOUND");
                continue;
            }

            WebElement colEL = colCells.get(0);

            // === HORIZONTAL + VERTICAL SCROLL ===
            js.executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'});", 
                colEL
            );

            // Scroll the table container horizontally (more reliable)
            try {
                WebElement tableOrContainer = colEL.findElement(By.xpath("./ancestor::table | ./ancestor::div[contains(@class, 'scroll') or contains(@class, 'table')]"));
                
                js.executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth * 0.8;", tableOrContainer);
                
                Thread.sleep(700); // small delay for scroll to complete
            } catch (Exception ignored) {
                // fallback if ancestor not found
            }

            // Re-locate element after scrolling
            colEL = driver.findElement(By.xpath(colXpath));

            if (colEL.isDisplayed()) {
                System.out.println("✅ Column '" + expColName + "' is DISPLAYED");
            } else {
                System.out.println("⚠️  Column '" + expColName + "' is NOT visible");
            }

        } catch (Exception e) {
            System.out.println("❌ Error checking column '" + expColName + "': " + e.getMessage());
            // e.printStackTrace();
        }
    }
}