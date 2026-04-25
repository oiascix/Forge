package com.forge.app.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.forge.app.activities.MainViewModel;
import com.forge.app.databinding.FragmentProfileBinding;
import com.forge.app.models.UserProfile;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private MainViewModel viewModel;
    private UserProfile currentProfile;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = FragmentProfileBinding.inflate(i, c, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        viewModel.profile.observe(getViewLifecycleOwner(), p -> {
            if (p == null) return;
            currentProfile = p;
            binding.etName.setText(p.name);
        });

        viewModel.completedTodayCount.observe(getViewLifecycleOwner(), n ->
                binding.tvCompletedToday.setText(String.valueOf(n != null ? n : 0)));

        viewModel.totalCount.observe(getViewLifecycleOwner(), n ->
                binding.tvTotalTasks.setText(String.valueOf(n != null ? n : 0)));

        // Show today's date
        binding.tvTodayDate.setText(viewModel.today);

        binding.btnSave.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().trim();
            if (name.isEmpty()) { binding.etName.setError("Name cannot be empty"); return; }
            if (currentProfile != null) {
                currentProfile.name = name;
                viewModel.updateProfile(currentProfile);
                Toast.makeText(requireContext(), "Profile saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
