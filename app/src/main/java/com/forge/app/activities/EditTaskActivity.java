package com.forge.app.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.forge.app.R;
import com.forge.app.databinding.ActivityEditTaskBinding;
import com.forge.app.models.Task;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity {
    private ActivityEditTaskBinding binding;
    private MainViewModel viewModel;
    private int taskId = -1;
    private int pickedHour = 9, pickedMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        taskId = getIntent().getIntExtra("task_id", -1);
        if (taskId != -1) {
            binding.tvHeader.setText("EDIT TASK");
            binding.etTitle.setText(getIntent().getStringExtra("task_title"));
            binding.etSubtitle.setText(getIntent().getStringExtra("task_subtitle"));
            binding.switchFeatured.setChecked(getIntent().getBooleanExtra("task_featured", false));
            selectIcon(getIntent().getStringExtra("task_icon"));
        }

        binding.btnPickTime.setOnClickListener(v ->
                new TimePickerDialog(this, (tp, h, m) -> {
                    pickedHour = h; pickedMinute = m;
                    binding.tvReminderTime.setText(
                            String.format(Locale.getDefault(), "%02d:%02d", h, m));
                }, pickedHour, pickedMinute, true).show()
        );

        binding.switchReminder.setOnCheckedChangeListener((btn, checked) ->
                binding.rowReminderTime.setVisibility(checked ? View.VISIBLE : View.GONE));

        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnSave.setOnClickListener(v -> save());
    }

    private void selectIcon(String icon) {
        if (icon == null) return;
        switch (icon) {
            case "book":  binding.radioIconBook.setChecked(true);  break;
            case "gym":   binding.radioIconGym.setChecked(true);   break;
            case "drop":  binding.radioIconDrop.setChecked(true);  break;
            case "code":  binding.radioIconCode.setChecked(true);  break;
            default:      binding.radioIconBrain.setChecked(true); break;
        }
    }

    private String selectedIcon() {
        int id = binding.radioGroupIcon.getCheckedRadioButtonId();
        if (id == R.id.radio_icon_book)  return "book";
        if (id == R.id.radio_icon_gym)   return "gym";
        if (id == R.id.radio_icon_drop)  return "drop";
        if (id == R.id.radio_icon_code)  return "code";
        return "brain";
    }

    private void save() {
        String title    = binding.etTitle.getText().toString().trim();
        String subtitle = binding.etSubtitle.getText().toString().trim();
        if (title.isEmpty()) { binding.etTitle.setError("Required"); return; }

        Task task = new Task(title, subtitle, selectedIcon(),
                binding.switchFeatured.isChecked(), 99);
        task.reminderEnabled = binding.switchReminder.isChecked();
        task.reminderHour    = pickedHour;
        task.reminderMinute  = pickedMinute;

        if (taskId != -1) {
            task.id = taskId;
            viewModel.updateTask(task);
            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.insertTask(task);
            Toast.makeText(this, "Task created", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
