package com.forge.app.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.forge.app.R;
import com.forge.app.databinding.ActivityEditProjectBinding;
import com.forge.app.models.ForgeProject;

public class EditProjectActivity extends AppCompatActivity {
    private ActivityEditProjectBinding binding;
    private MainViewModel viewModel;
    private int projectId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        projectId = getIntent().getIntExtra("project_id", -1);
        if (projectId != -1) {
            binding.tvHeader.setText("EDIT PROJECT");
            binding.etName.setText(getIntent().getStringExtra("project_name"));
            binding.etHeat.setText(String.valueOf(getIntent().getIntExtra("project_heat", 0)));
            binding.etMaxHeat.setText(String.valueOf(getIntent().getIntExtra("project_max_heat", 2000)));
            binding.etTimeLeft.setText(getIntent().getStringExtra("project_time_left"));
        }

        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnSave.setOnClickListener(v -> save());
    }

    private void save() {
        String name     = binding.etName.getText().toString().trim();
        String heatStr  = binding.etHeat.getText().toString().trim();
        String maxStr   = binding.etMaxHeat.getText().toString().trim();
        String timeLeft = binding.etTimeLeft.getText().toString().trim();

        if (name.isEmpty())    { binding.etName.setError("Required");    return; }
        if (heatStr.isEmpty()) { binding.etHeat.setError("Required");    return; }
        if (maxStr.isEmpty())  { binding.etMaxHeat.setError("Required"); return; }

        ForgeProject p = new ForgeProject(name,
                Integer.parseInt(heatStr), Integer.parseInt(maxStr), timeLeft);

        if (projectId != -1) {
            p.id = projectId;
            viewModel.updateProject(p);
            Toast.makeText(this, "Project updated", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.insertProject(p);
            Toast.makeText(this, "Project created", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
