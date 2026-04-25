package com.forge.app.adapters;

import android.view.*;
import androidx.annotation.*;
import androidx.recyclerview.widget.*;
import com.forge.app.databinding.ItemProjectBinding;
import com.forge.app.models.ForgeProject;

public class ProjectAdapter extends ListAdapter<ForgeProject, ProjectAdapter.ViewHolder> {
    public interface OnEdit   { void onClick(ForgeProject p); }
    public interface OnDelete { void onClick(ForgeProject p); }

    private final OnEdit onEdit; private final OnDelete onDelete;
    public ProjectAdapter(OnEdit e, OnDelete d) { super(DIFF); this.onEdit=e; this.onDelete=d; }

    private static final DiffUtil.ItemCallback<ForgeProject> DIFF = new DiffUtil.ItemCallback<>() {
        @Override public boolean areItemsTheSame(@NonNull ForgeProject a, @NonNull ForgeProject b) { return a.id==b.id; }
        @Override public boolean areContentsTheSame(@NonNull ForgeProject a, @NonNull ForgeProject b) {
            return a.name.equals(b.name) && a.currentHeat==b.currentHeat; }
    };

    @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new ViewHolder(ItemProjectBinding.inflate(LayoutInflater.from(p.getContext()), p, false));
    }
    @Override public void onBindViewHolder(@NonNull ViewHolder h, int pos) { h.bind(getItem(pos)); }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ItemProjectBinding b;
        ViewHolder(ItemProjectBinding b) { super(b.getRoot()); this.b=b; }
        void bind(ForgeProject p) {
            b.tvName.setText(p.name);
            b.tvHeat.setText(p.currentHeat + "° / " + p.maxHeat + "°");
            b.tvTimeLeft.setText(p.timeLeft);
            b.progressBar.setProgress((int)((p.currentHeat/(float)p.maxHeat)*100));
            b.btnEdit.setOnClickListener(v -> onEdit.onClick(p));
            b.btnDelete.setOnClickListener(v -> onDelete.onClick(p));
        }
    }
}
