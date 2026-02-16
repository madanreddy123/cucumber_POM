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



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public boolean setCellData(String sheetName, int rowNum, String data) {

    if (rowNum <= 0) {
        return false;
    }

    try (FileInputStream fis = new FileInputStream(path);
         Workbook workbook = new XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            return false;  // Sheet not found
        }

        // Get or create row (rowNum starts from 1)
        Row row = sheet.getRow(rowNum - 1);
        if (row == null) {
            row = sheet.createRow(rowNum - 1);
        }

        // Column 0 (First column)
        Cell cell = row.getCell(0);
        if (cell == null) {
            cell = row.createCell(0);
        }

        // Set string value
        cell.setCellValue(data);

        // Write back to file
        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos);
        }

        return true;

    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}


