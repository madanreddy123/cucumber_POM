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



 Performance & Progress:
This quarter I worked on improving my knowledge of the application and strengthening my skills in test case design, execution, and testing for my assigned modules. I successfully completed eUTF Labs testing for the month of June. I also did regression testing for the CUW and Vote Calc modules to make sure they are stable and working well.
Feedback & Behaviors:
I independently refactored the automation code for the CUW and Vote Calc applications. This made the code cleaner and easier to maintain. I took the initiative to improve the code, and my changes were successfully reviewed and approved.
Development & Growth:
To grow my skills, I created automation scripts for the new Angular Portfolio Adjustment screen. The code was committed, reviewed, and successfully merged into the main branch. These tasks helped me learn more about automation and Angular applications for future work.
Workplace Expectations:
I maintained good attendance, followed team rules, and completed all required training on time.