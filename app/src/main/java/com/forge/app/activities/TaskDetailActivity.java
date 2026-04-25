package com.forge.app.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.forge.app.databinding.ActivityTaskDetailBinding;
import com.forge.app.models.Task;
import com.forge.app.utils.DateUtils;
import java.util.*;

public class TaskDetailActivity extends AppCompatActivity {
    private ActivityTaskDetailBinding binding;
    private MainViewModel viewModel;
    private Task currentTask;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskId = getIntent().getIntExtra("task_id", -1);
        if (taskId == -1) { finish(); return; }

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.btnBack.setOnClickListener(v -> finish());

        viewModel.getTaskById(taskId).observe(this, task -> {
            if (task == null) return;
            currentTask = task;
            bindTask(task);
        });

        viewModel.getCompletionDates(taskId).observe(this, dates -> {
            if (dates == null) dates = new ArrayList<>();
            updateStreakUI(dates);
            binding.calendarView.setCompletedDates(dates);
        });
    }

    private void bindTask(Task task) {
        binding.tvTitle.setText(task.title);
        binding.tvSubtitle.setText(task.subtitle);

        // Reminder toggle
        binding.switchReminder.setChecked(task.reminderEnabled);
        updateReminderTimeLabel(task);
        binding.switchReminder.setOnCheckedChangeListener((btn, checked) -> {
            currentTask.reminderEnabled = checked;
            binding.rowReminderTime.setVisibility(checked ? View.VISIBLE : View.GONE);
            viewModel.updateTask(currentTask);
        });
        binding.rowReminderTime.setVisibility(task.reminderEnabled ? View.VISIBLE : View.GONE);

        binding.btnPickTime.setOnClickListener(v -> {
            new TimePickerDialog(this, (tp, hour, minute) -> {
                currentTask.reminderHour   = hour;
                currentTask.reminderMinute = minute;
                updateReminderTimeLabel(currentTask);
                viewModel.updateTask(currentTask);
            }, currentTask.reminderHour, currentTask.reminderMinute, true).show();
        });
    }

    private void updateReminderTimeLabel(Task task) {
        binding.tvReminderTime.setText(
                String.format(Locale.getDefault(), "%02d:%02d", task.reminderHour, task.reminderMinute));
    }

    private void updateStreakUI(List<String> dates) {
        int current = DateUtils.computeStreak(dates);
        int best    = DateUtils.computeBestStreak(dates);
        int total   = dates.size();

        binding.tvCurrentStreak.setText(String.valueOf(current));
        binding.tvBestStreak.setText(String.valueOf(best));
        binding.tvTotalDone.setText(String.valueOf(total));

        if (current > 0) {
            binding.tvStreakEmoji.setText(current >= 7 ? "🔥🔥" : "🔥");
            binding.tvStreakMessage.setText(current >= 30 ? "Legendary!"
                    : current >= 14 ? "On fire!"
                    : current >= 7  ? "One week strong!"
                    : "Keep going!");
        } else {
            binding.tvStreakEmoji.setText("💤");
            binding.tvStreakMessage.setText("Start your streak today");
        }
    }
}
