public void waitForVisibilityOfElement(By locator, int timeInSec) {
    long start = System.currentTimeMillis();
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Determine if the locator is CSS or XPath
    boolean isXPath = locator.toString().startsWith("By.xpath:");
    String locatorValue = locator.toString()
            .replace("By.cssSelector: ", "")
            .replace("By.xpath: ", "")
            .replace("'", "\\'"); // escape quotes for JS

    try {
        // 1️⃣ DOM availability time
        long domStart = System.currentTimeMillis();
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(timeInSec))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        long domEnd = System.currentTimeMillis();
        long domTime = domEnd - domStart;

        // 2️⃣ Visibility time
        long visStart = System.currentTimeMillis();
        element = new WebDriverWait(driver, Duration.ofSeconds(timeInSec))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        long visEnd = System.currentTimeMillis();
        long visibilityTime = visEnd - visStart;

        // 3️⃣ Clickability time
        long clickStart = System.currentTimeMillis();
        element = new WebDriverWait(driver, Duration.ofSeconds(timeInSec))
                .until(ExpectedConditions.elementToBeClickable(locator));
        long clickEnd = System.currentTimeMillis();
        long clickableTime = clickEnd - clickStart;

        // 4️⃣ Render/Paint time
        String renderScript = isXPath
                ? "let start = performance.now();" +
                  "let el = document.evaluate('" + locatorValue + "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                  "if(el && el.offsetHeight > 0 && el.offsetWidth > 0) { return performance.now() - start; } else { return 0; }"
                : "let start = performance.now();" +
                  "let el = document.querySelector('" + locatorValue + "');" +
                  "if(el && el.offsetHeight > 0 && el.offsetWidth > 0) { return performance.now() - start; } else { return 0; }";

        Double renderTime = (Double) js.executeScript(renderScript);

        // 5️⃣ JS execution time for element
        String jsExecScript = isXPath
                ? "let t0 = performance.now();" +
                  "document.evaluate('" + locatorValue + "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                  "return performance.now() - t0;"
                : "let t0 = performance.now();" +
                  "document.querySelector('" + locatorValue + "');" +
                  "return performance.now() - t0;";

        Double jsExecution = (Double) js.executeScript(jsExecScript);

        // 6️⃣ Layout + Paint timings
        Map<String, Object> paintTimings = (Map<String, Object>) js.executeScript(
                "return performance.getEntriesByType('paint')" +
                        ".reduce((m, p) => { m[p.name] = p.startTime; return m; }, {});"
        );

        // 7️⃣ Network timings
        Map<String, Object> networkTimings = (Map<String, Object>) js.executeScript(
                "let map = {};" +
                "performance.getEntries().forEach(e => {" +
                "  if(e.initiatorType === 'xmlhttprequest') { map[e.name] = e.duration; }" +
                "}); return map;"
        );

        // 8️⃣ Element size
        String sizeScript = isXPath
                ? "let el = document.evaluate('" + locatorValue + "', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                  "return el ? [el.offsetWidth, el.offsetHeight] : [0,0];"
                : "let el = document.querySelector('" + locatorValue + "');" +
                  "return el ? [el.offsetWidth, el.offsetHeight] : [0,0];";

        List<Long> size = (List<Long>) js.executeScript(sizeScript);
        Long width = size.get(0);
        Long height = size.get(1);

        long totalLoadTime = System.currentTimeMillis() - start;

        // Store performance data
        PerformanceData.add(
                "Element: " + locator +
                        " | DOM Found: " + domTime + " ms" +
                        " | Visible: " + visibilityTime + " ms" +
                        " | Clickable: " + clickableTime + " ms" +
                        " | Render Time: " + renderTime + " ms" +
                        " | JS Exec: " + jsExecution + " ms" +
                        " | Size: " + width + "x" + height +
                        " | Total Load: " + totalLoadTime + " ms" +
                        " | Paint Timings: " + paintTimings +
                        " | Network Calls: " + networkTimings
        );

    } catch (Exception ex) {
        PerformanceData.add("Element: " + locator + " FAILED to load → " + ex.getMessage());
        throw ex;
    }
}