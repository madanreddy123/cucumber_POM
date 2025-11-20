@After
public void teardown(Scenario scenario) {

    // Element message logs
    for (String msg : PerformanceData.getAllMessages()) {
        scenario.log(msg);
    }

    // Element metric logs
    for (Map<String, Object> metric : PerformanceData.getAllElementMetrics()) {
        scenario.log(metric.toString());
    }

    // Page metric logs
    for (String key : PerformanceData.getAllPageMetrics().keySet()) {
        scenario.log(key + " : " + PerformanceData.getAllPageMetrics().get(key));
    }

    PerformanceData.clear();
}