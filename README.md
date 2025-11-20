public void waitForVisibilityOfElement(By locator, int timeInSec) {
    long start = System.currentTimeMillis();
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Safely extract CSS selector from locator
    String cssSelector = locator.toString().replace("By.cssSelector: ", "").replace("'", "\\'");

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

        // 4️⃣ Render/Paint time using performance.now()
        Double renderTime = (Double) js.executeScript(
                "let start = performance.now();" +
                "let el = document.querySelector('" + cssSelector + "');" +
                "if(el && el.offsetHeight > 0 && el.offsetWidth > 0) {" +
                "  return performance.now() - start;" +
                "} else { return 0; }"
        );

        // 5️⃣ JS execution time for element (expensive components)
        Double jsExecution = (Double) js.executeScript(
                "let t0 = performance.now();" +
                "document.querySelector('" + cssSelector + "');" +
                "return performance.now() - t0;"
        );

        // 6️⃣ Layout + Paint timing (browser internal)
        Map<String, Object> paintTimings = (Map<String, Object>)
                js.executeScript(
                        "return performance.getEntriesByType('paint')" +
                        ".reduce((m, p) => { m[p.name] = p.startTime; return m; }, {});"
                );

        // 7️⃣ Network timing (resource load time)
        Map<String, Object> networkTimings = (Map<String, Object>)
                js.executeScript(
                        "let map = {};" +
                        "performance.getEntries().forEach(e => {" +
                        "  if(e.initiatorType === 'xmlhttprequest') {" +
                        "    map[e.name] = e.duration;" +
                        "  }" +
                        "}); return map;"
                );

        // 8️⃣ Element size (helps find heavy elements)
        Long height = (Long) js.executeScript(
                "let el = document.querySelector('" + cssSelector + "');" +
                "return el ? el.offsetHeight : 0;"
        );
        Long width = (Long) js.executeScript(
                "let el = document.querySelector('" + cssSelector + "');" +
                "return el ? el.offsetWidth : 0;"
        );

        long totalLoadTime = System.currentTimeMillis() - start;

        // Store every detail in PerformanceData
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