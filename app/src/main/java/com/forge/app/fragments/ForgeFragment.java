package com.forge.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.forge.app.activities.*;
import com.forge.app.adapters.*;
import com.forge.app.databinding.FragmentForgeBinding;
import com.forge.app.models.TaskWithStatus;
import java.util.ArrayList;
import java.util.List;

public class ForgeFragment extends Fragment {
    private FragmentForgeBinding binding;
    private MainViewModel viewModel;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = FragmentForgeBinding.inflate(i, c, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        // Projects
        ProjectAdapter projAdapter = new ProjectAdapter(
                p -> {
                    Intent i = new Intent(requireContext(), EditProjectActivity.class);
                    i.putExtra("project_id", p.id); i.putExtra("project_name", p.name);
                    i.putExtra("project_heat", p.currentHeat); i.putExtra("project_max_heat", p.maxHeat);
                    i.putExtra("project_time_left", p.timeLeft);
                    startActivity(i);
                },
                p -> viewModel.deleteProject(p));
        binding.rvProjects.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvProjects.setAdapter(projAdapter);
        viewModel.allProjects.observe(getViewLifecycleOwner(), list -> {
            if (list != null) projAdapter.submitList(list);
        });

        // Tasks
        TaskListAdapter taskAdapter = new TaskListAdapter(
                task -> {
                    Intent i = new Intent(requireContext(), EditTaskActivity.class);
                    i.putExtra("task_id", task.id); i.putExtra("task_title", task.title);
                    i.putExtra("task_subtitle", task.subtitle); i.putExtra("task_icon", task.iconType);
                    i.putExtra("task_featured", task.isFeatured);
                    startActivity(i);
                },
                task -> viewModel.deleteTask(task),
                (task, checked) -> viewModel.toggleCompletion(task, checked),
                taskId -> {
                    Intent i = new Intent(requireContext(), TaskDetailActivity.class);
                    i.putExtra("task_id", taskId);
                    startActivity(i);
                }
        );
        binding.rvAllTasks.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvAllTasks.setAdapter(taskAdapter);
        viewModel.tasksWithStatus.observe(getViewLifecycleOwner(), list -> {
            if (list != null) taskAdapter.submitList(list);
        });

        binding.fabAddProject.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), EditProjectActivity.class)));
        binding.fabAddTask.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), EditTaskActivity.class)));
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
