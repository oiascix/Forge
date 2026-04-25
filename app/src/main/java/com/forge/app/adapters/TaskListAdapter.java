package com.forge.app.adapters;

import android.graphics.Paint;
import android.view.*;
import androidx.annotation.*;
import androidx.recyclerview.widget.*;
import com.forge.app.R;
import com.forge.app.databinding.ItemTaskListBinding;
import com.forge.app.models.Task;
import com.forge.app.models.TaskWithStatus;

public class TaskListAdapter extends ListAdapter<TaskWithStatus, TaskListAdapter.ViewHolder> {

    public interface OnEdit   { void onClick(Task task); }
    public interface OnDelete { void onClick(Task task); }
    public interface OnToggle { void onClick(Task task, boolean completed); }
    public interface OnDetail { void onClick(int taskId); }

    private final OnEdit   onEdit;
    private final OnDelete onDelete;
    private final OnToggle onToggle;
    private final OnDetail onDetail;

    public TaskListAdapter(OnEdit e, OnDelete d, OnToggle t, OnDetail detail) {
        super(DIFF);
        this.onEdit = e; this.onDelete = d; this.onToggle = t; this.onDetail = detail;
    }

    private static final DiffUtil.ItemCallback<TaskWithStatus> DIFF = new DiffUtil.ItemCallback<>() {
        @Override public boolean areItemsTheSame(@NonNull TaskWithStatus a, @NonNull TaskWithStatus b) {
            return a.task.id == b.task.id;
        }
        @Override public boolean areContentsTheSame(@NonNull TaskWithStatus a, @NonNull TaskWithStatus b) {
            return a.completedToday == b.completedToday && a.task.title.equals(b.task.title)
                    && a.task.currentStreak == b.task.currentStreak;
        }
    };

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTaskListBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder h, int pos) { h.bind(getItem(pos)); }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ItemTaskListBinding b;
        ViewHolder(ItemTaskListBinding b) { super(b.getRoot()); this.b = b; }

        void bind(TaskWithStatus tws) {
            Task task = tws.task;
            b.tvTitle.setText(task.title);
            b.tvSubtitle.setText(task.subtitle);
            b.tvFeatured.setVisibility(task.isFeatured ? View.VISIBLE : View.GONE);
            b.ivIcon.setImageResource(iconRes(task.iconType));

            // Streak chip
            if (task.currentStreak > 0) {
                b.tvStreak.setVisibility(View.VISIBLE);
                b.tvStreak.setText("🔥 " + task.currentStreak + " day" + (task.currentStreak > 1 ? "s" : ""));
            } else {
                b.tvStreak.setVisibility(View.GONE);
            }

            b.tvTitle.setPaintFlags(tws.completedToday
                    ? b.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                    : b.tvTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

            b.cbCompleted.setOnCheckedChangeListener(null);
            b.cbCompleted.setChecked(tws.completedToday);
            b.cbCompleted.setOnCheckedChangeListener((btn, checked) -> onToggle.onClick(task, checked));

            b.btnEdit.setOnClickListener(v -> onEdit.onClick(task));
            b.btnDelete.setOnClickListener(v -> onDelete.onClick(task));
            b.getRoot().setOnClickListener(v -> onDetail.onClick(task.id));
        }

        int iconRes(String t) {
            if (t == null) return R.drawable.ic_task_default;
            return switch (t) {
                case "brain" -> R.drawable.ic_brain;
                case "book"  -> R.drawable.ic_book;
                case "gym"   -> R.drawable.ic_gym;
                case "drop"  -> R.drawable.ic_drop;
                case "code"  -> R.drawable.ic_code;
                default      -> R.drawable.ic_task_default;
            };
        }
    }
}
