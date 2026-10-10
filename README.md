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


Fix the following UI/layout and data-display problems on the UPVCio Business Management Dashboard page:

1. **Overlapping / broken naming preview labels (right side of the Client Volume chart)**
   - The three status labels are currently rendering on top of each other and are unreadable:
     - "Completed Clients (1)"
     - "Confirmed Orders (1)"
     - "Quoted Clients (1)"
   - Fix the layout so these three labels appear clearly stacked or properly spaced next to the donut chart without any text overlapping or colliding.
   - Keep the colored dots (green, blue, orange) aligned with their respective labels.
   - Ensure the numbers in parentheses remain visible and correctly associated with each label.

2. **Currency and Period dropdowns are too small**
   - The "Currency" and "Period" select/dropdown controls in the top-right corner of the dashboard are currently too small and hard to click/read.
   - Increase their size (both height and width) so they are comfortably usable.
   - Make the text inside the dropdowns larger and more readable.
   - Ensure proper spacing and alignment with the rest of the header.

3. **Default month view – show only current month**
   - Currently the left-side monthly breakdown is showing multiple previous months (May 2026, Jun 2026, Jul 2026, Aug 2026, Sept 2026) even when they have zero clients.
   - By default, the dashboard should display **only the current month’s details** (October 2026 in this case).
   - Hide or collapse previous months that have no data unless the user explicitly expands or selects a wider period.
   - Keep the ability to view historical months if the user changes the Period filter, but the default state should be current month only.

Additional requirements:
- Keep the overall design consistent with the existing purple sidebar and clean white content area.
- Maintain responsiveness so the fixes work well on different screen sizes.
- Do not change any business logic or underlying data — only fix the visual layout, sizing, and default filtering of these elements.
- After the fix, the three status labels should be cleanly readable, the Currency/Period selectors should be easy to interact with, and the monthly list should default to showing only the current month.