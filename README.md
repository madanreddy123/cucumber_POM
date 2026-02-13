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



Automating the creation of execution cycles
We have implemented a script to automatically add test cases to the execution cycle based on the execution cycle ID generated within the automation script. The creation of the execution cycle itself is currently a one-time activity performed for each sprint.
Introducing a code review approval step in Git
We propose adding an additional approval step before merging code into the main branch. At present, there is no structured code review process, as each automation scheme operates independently. However, if there are shared dependencies or common frameworks between automation projects, implementing a formal review and approval process would help maintain code quality and consistency.
Automating Selenium WebDriver updates
We explored automating the download of updated Selenium drivers directly from the browser. However, this approach did not function as expected within the client network environment. Attempts were made using Selenium Manager and WebDriverManager, but further investigation may be required to resolve network-related limitations.
Automating report distribution via email
We considered sending automation reports directly to stakeholders via email. However, in cases where there are multiple flaky failures, sending reports regularly may not provide meaningful insights. Currently, we share the Allure report internally, review flaky failures locally, and analyze the root causes before sharing consolidated findings.
Synchronizing automation frameworks across projects
We aim to automate the synchronization of all projects to ensure the codebase remains up to date and consistently maintained. Except for the LPIS automation framework, the skeleton structures across frameworks are aligned, with differences primarily in feature-level automation code.
Tracking automated and pending test cases in JIRA
A tracker has been created in JIRA to monitor test cases that are automated and those pending automation. This approach has already been implemented in LPIS and Agrisnap. In IMT, we are creating execution cycles for each sprint to track automated cases effectively.
Linking development builds with automation builds
We evaluated linking development builds directly to automation builds so that regression suites run automatically after each deployment. However, based on feedback in JIRA, the daytime agents are largely occupied with manual testing activities, and scheduled builds are already running. Due to limited automation agent availability, this implementation is currently not feasible.

    