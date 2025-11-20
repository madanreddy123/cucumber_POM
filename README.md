package com.automation.framework.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PerformanceData {

    // Stores simple messages (your existing use case)
    public static List<String> elementTimings = new ArrayList<>();

    // Stores element-specific metrics (render time, visibility time, etc.)
    public static List<Map<String, Object>> elementMetrics = new ArrayList<>();

    // Stores page-level metrics (FCP, DOM Complete, LCP, etc.)
    public static Map<String, Object> pageMetrics = new LinkedHashMap<>();


    // ───────────────────────────────────────────────
    // ▶ EXISTING METHOD — Add simple timing message
    // ───────────────────────────────────────────────
    public static void add(String message) {
        elementTimings.add(message);
    }

    // ───────────────────────────────────────────────
    // ▶ NEW: Add element-level metric map
    //     Example: {"locator": "#username", "renderTime": 320}
    // ───────────────────────────────────────────────
    public static void addElementMetric(Map<String, Object> metric) {
        elementMetrics.add(metric);
    }

    // ───────────────────────────────────────────────
    // ▶ NEW: Add page-level metric entry
    //     Example: addPageMetric("FCP", 386)
    // ───────────────────────────────────────────────
    public static void addPageMetric(String name, Object value) {
        pageMetrics.put(name, value);
    }

    // ───────────────────────────────────────────────
    // ▶ GETTERS
    // ───────────────────────────────────────────────
    public static List<String> getAllMessages() {
        return elementTimings;
    }

    public static List<Map<String, Object>> getAllElementMetrics() {
        return elementMetrics;
    }

    public static Map<String, Object> getAllPageMetrics() {
        return pageMetrics;
    }

    // ───────────────────────────────────────────────
    // ▶ CLEAR ALL DATA (called at end of scenario)
    // ───────────────────────────────────────────────
    public static void clear() {
        elementTimings.clear();
        elementMetrics.clear();
        pageMetrics.clear();
    }

    // ───────────────────────────────────────────────
    // ▶ OPTIONAL: Print everything to console
    // ───────────────────────────────────────────────
    public static void printAll() {
        System.out.println("===== ELEMENT TIMINGS =====");
        elementTimings.forEach(System.out::println);

        System.out.println("===== ELEMENT METRICS =====");
        elementMetrics.forEach(System.out::println);

        System.out.println("===== PAGE METRICS =====");
        pageMetrics.forEach((k,v) -> System.out.println(k + " : " + v));
    }
}