package com.forge.app.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String today() {
        return LocalDate.now().format(FMT);
    }

    public static String yesterday() {
        return LocalDate.now().minusDays(1).format(FMT);
    }

    public static String daysAgo(int n) {
        return LocalDate.now().minusDays(n).format(FMT);
    }

    public static boolean isToday(String dateString) {
        return today().equals(dateString);
    }

    /** Compute current streak from a sorted-desc list of completion date strings. */
    public static int computeStreak(java.util.List<String> datesSortedDesc) {
        if (datesSortedDesc == null || datesSortedDesc.isEmpty()) return 0;

        LocalDate cursor = LocalDate.now();
        int streak = 0;

        // Allow today to not yet be completed (streak continues from yesterday)
        if (!datesSortedDesc.get(0).equals(cursor.format(FMT))) {
            cursor = cursor.minusDays(1);
        }

        for (String ds : datesSortedDesc) {
            LocalDate d = LocalDate.parse(ds, FMT);
            if (d.equals(cursor)) {
                streak++;
                cursor = cursor.minusDays(1);
            } else if (d.isBefore(cursor)) {
                break; // gap found
            }
        }
        return streak;
    }

    /** Compute best-ever streak from any list of date strings. */
    public static int computeBestStreak(java.util.List<String> datesSortedDesc) {
        if (datesSortedDesc == null || datesSortedDesc.isEmpty()) return 0;
        int best = 0, current = 1;
        for (int i = 1; i < datesSortedDesc.size(); i++) {
            LocalDate prev = LocalDate.parse(datesSortedDesc.get(i - 1), FMT);
            LocalDate curr = LocalDate.parse(datesSortedDesc.get(i), FMT);
            if (prev.minusDays(1).equals(curr)) {
                current++;
            } else {
                best = Math.max(best, current);
                current = 1;
            }
        }
        return Math.max(best, current);
    }
}
