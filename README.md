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


// Delete cookies
driver.manage().deleteAllCookies();

// Clear local storage + session storage
JavascriptExecutor js = (JavascriptExecutor) driver;

js.executeScript("window.localStorage.clear();");
js.executeScript("window.sessionStorage.clear();");

// Refresh browser
driver.navigate().refresh();

// Small wait
Thread.sleep(3000);


=(csa!K2 + csa!L2 - csa!P2 - csa!AZ2) - (csa!V2 + csa!AI2) - csa!AK2

=IF(ISNUMBER(SEARCH("abc",P2)),0-B2,"")

=IF(ISNUMBER(SEARCH("abc",P2)),-B2,C2-B2)