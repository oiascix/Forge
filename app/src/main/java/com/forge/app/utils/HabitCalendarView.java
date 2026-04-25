package com.forge.app.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import java.time.LocalDate;
import java.util.*;

/**
 * Draws a 12-week (84 day) GitHub-style contribution heatmap.
 * Each cell is a rounded square; completed days are orange, empty days are dark grey.
 */
public class HabitCalendarView extends View {

    private static final int WEEKS  = 15;
    private static final int DAYS   = 7;
    private static final float GAP  = 4f;   // dp between cells

    private final Paint paintEmpty   = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintDone    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintToday   = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintLabel   = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect         = new RectF();

    private Set<String> completedSet = new HashSet<>();
    private float cellSize;
    private float gap;

    public HabitCalendarView(Context ctx) { super(ctx); init(); }
    public HabitCalendarView(Context ctx, AttributeSet a) { super(ctx, a); init(); }
    public HabitCalendarView(Context ctx, AttributeSet a, int def) { super(ctx, a, def); init(); }

    private void init() {
        float density = getResources().getDisplayMetrics().density;
        gap = GAP * density;

        paintEmpty.setColor(0xFF2E3236);
        paintDone.setColor(0xFFE8420A);
        paintToday.setColor(0xFFFF6B35);
        paintLabel.setColor(0xFF606469);
        paintLabel.setTextSize(9 * density);
        paintLabel.setTextAlign(Paint.Align.CENTER);
    }

    public void setCompletedDates(List<String> dates) {
        completedSet = new HashSet<>(dates);
        invalidate();
    }

    @Override
    protected void onMeasure(int wSpec, int hSpec) {
        int w = MeasureSpec.getSize(wSpec);
        float density = getResources().getDisplayMetrics().density;
        float labelH = 14 * density;
        cellSize = (w - gap * (WEEKS + 1)) / WEEKS;
        int h = (int) (labelH + gap + (cellSize + gap) * DAYS + gap);
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float density = getResources().getDisplayMetrics().density;
        float labelH  = 14 * density;
        float radius  = 3 * density;

        LocalDate today = LocalDate.now();
        // Start from (WEEKS*7 - 1) days ago, aligned to Sunday
        LocalDate start = today.minusDays((long) WEEKS * DAYS - 1);

        String[] dayLabels = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (int week = 0; week < WEEKS; week++) {
            for (int day = 0; day < DAYS; day++) {
                LocalDate date = start.plusDays((long) week * DAYS + day);
                String ds = date.toString();

                float x = gap + week * (cellSize + gap);
                float y = labelH + gap + day * (cellSize + gap);
                rect.set(x, y, x + cellSize, y + cellSize);

                Paint p;
                if (date.equals(today))          p = paintToday;
                else if (completedSet.contains(ds)) p = paintDone;
                else                              p = paintEmpty;

                canvas.drawRoundRect(rect, radius, radius, p);
            }

            // Month label at top (only when month changes)
            LocalDate weekStart = start.plusDays((long) week * DAYS);
            if (week == 0 || weekStart.getDayOfMonth() <= 7) {
                float cx = gap + week * (cellSize + gap) + cellSize / 2f;
                String month = weekStart.getMonth().name().substring(0, 3);
                canvas.drawText(month, cx, labelH - 2 * density, paintLabel);
            }
        }
    }
}
