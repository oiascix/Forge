package com.forge.app.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import com.forge.app.R;
import com.forge.app.activities.MainViewModel;
import com.forge.app.adapters.TaskGridAdapter;
import com.forge.app.databinding.FragmentHomeBinding;
import com.forge.app.models.TaskWithStatus;
import com.forge.app.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private MainViewModel viewModel;
    private TaskGridAdapter adapter;
    private Handler clockHandler;
    private Runnable clockRunnable;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        setupClock();
        setupGrid();
        observeData();
    }

    private void setupClock() {
        clockHandler = new Handler(Looper.getMainLooper());
        clockRunnable = new Runnable() {
            @Override public void run() {
                if (binding == null) return;
                String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                binding.tvTime.setText("Time: " + time);
                updateGreeting();
                clockHandler.postDelayed(this, 30_000);
            }
        };
        clockHandler.post(clockRunnable);
    }

    private void updateGreeting() {
        int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting = h >= 5 && h < 12 ? "Good Morning"
                : h >= 12 && h < 17 ? "Good Afternoon"
                : h >= 17 && h < 21 ? "Good Evening"
                : "Good Night";
        String subtitle = h >= 5 && h < 12 ? "Time to work"
                : h >= 12 && h < 17 ? "Stay focused"
                : h >= 17 && h < 21 ? "Keep forging"
                : "Rest and recover";
        binding.tvSubtitle.setText(subtitle);
        viewModel.profile.observe(getViewLifecycleOwner(), p -> {
            if (p != null) binding.tvGreeting.setText(greeting + ", " + p.name);
        });
    }

    private void setupGrid() {
        adapter = new TaskGridAdapter(
                (task, completed) -> viewModel.toggleCompletion(task, completed),
                taskId -> {
                    // Open detail screen
                    android.content.Intent i = new android.content.Intent(
                            requireContext(), com.forge.app.activities.TaskDetailActivity.class);
                    i.putExtra("task_id", taskId);
                    startActivity(i);
                }
        );
        GridLayoutManager lm = new GridLayoutManager(requireContext(), 2);
        binding.rvTasks.setLayoutManager(lm);
        binding.rvTasks.setAdapter(adapter);
        binding.rvTasks.setNestedScrollingEnabled(false);
    }

    private void observeData() {
        // Project / Core Heat
        viewModel.activeProject.observe(getViewLifecycleOwner(), project -> {
            if (project == null) return;
            binding.tvProjectName.setText("Forging: " + project.name + " (" + project.timeLeft + ")");
            binding.tvHeatValue.setText(project.currentHeat + "°");
            int pct = (int) ((project.currentHeat / (float) project.maxHeat) * 100);
            binding.progressHeat.setProgress(pct);
        });

        // Featured task
        viewModel.featuredTask.observe(getViewLifecycleOwner(), task -> {
            if (task == null) return;
            binding.cardFeatured.tvFeaturedTitle.setText(task.title);
            binding.cardFeatured.tvFeaturedSubtitle.setText(task.subtitle);
            // Streak badge on featured card
            if (task.currentStreak > 0) {
                binding.cardFeatured.tvFeaturedStreak.setVisibility(View.VISIBLE);
                binding.cardFeatured.tvFeaturedStreak.setText("🔥 " + task.currentStreak);
            } else {
                binding.cardFeatured.tvFeaturedStreak.setVisibility(View.GONE);
            }
        });

        // Completion state of featured task today
        viewModel.completedTaskIdsToday.observe(getViewLifecycleOwner(), ids -> {
            viewModel.featuredTask.observe(getViewLifecycleOwner(), task -> {
                if (task == null || binding == null) return;
                boolean done = ids != null && ids.contains(task.id);
                applyFeaturedState(done);
                binding.cardFeatured.toggleFeatured.setOnClickListener(v ->
                        viewModel.toggleCompletion(task, !done));
            });
        });

        // Grid tasks with today's status
        viewModel.tasksWithStatus.observe(getViewLifecycleOwner(), list -> {
            if (list == null) return;
            List<TaskWithStatus> grid = list.stream()
                    .filter(t -> !t.task.isFeatured)
                    .collect(Collectors.toList());
            adapter.submitList(grid);
        });

        // Day progress bar
        viewModel.completedTodayCount.observe(getViewLifecycleOwner(), done -> {
            if (done == null) done = 0;
            int total = viewModel.totalCount.getValue() != null ? viewModel.totalCount.getValue() : 0;
            binding.tvDayProgress.setText(done + " / " + total + " done today");
            int pct = total > 0 ? (int) ((done / (float) total) * 100) : 0;
            binding.progressDay.setProgress(pct);
        });
        viewModel.totalCount.observe(getViewLifecycleOwner(), total -> {
            int done = viewModel.completedTodayCount.getValue() != null
                    ? viewModel.completedTodayCount.getValue() : 0;
            if (total == null) total = 0;
            binding.tvDayProgress.setText(done + " / " + total + " done today");
            int pct = total > 0 ? (int) ((done / (float) total) * 100) : 0;
            binding.progressDay.setProgress(pct);
        });
    }

    private void applyFeaturedState(boolean done) {
        if (binding == null) return;
        if (done) {
            binding.cardFeatured.toggleFeatured.setBackgroundResource(R.drawable.toggle_circle_checked);
            binding.cardFeatured.ivCheckFeatured.setVisibility(View.VISIBLE);
            binding.cardFeatured.tvFeaturedTitle.setAlpha(0.45f);
        } else {
            binding.cardFeatured.toggleFeatured.setBackgroundResource(R.drawable.toggle_circle_bg);
            binding.cardFeatured.ivCheckFeatured.setVisibility(View.INVISIBLE);
            binding.cardFeatured.tvFeaturedTitle.setAlpha(1f);
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (clockHandler != null) clockHandler.removeCallbacks(clockRunnable);
        binding = null;
    }
}
