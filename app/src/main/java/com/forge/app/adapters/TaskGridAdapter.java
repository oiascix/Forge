package com.forge.app.adapters;

import android.graphics.Paint;
import android.view.*;
import androidx.annotation.*;
import androidx.recyclerview.widget.*;
import com.forge.app.R;
import com.forge.app.databinding.ItemTaskGridBinding;
import com.forge.app.models.Task;
import com.forge.app.models.TaskWithStatus;

public class TaskGridAdapter extends ListAdapter<TaskWithStatus, TaskGridAdapter.ViewHolder> {

    public interface OnToggle { void onToggle(Task task, boolean completed); }
    public interface OnClick  { void onClick(int taskId); }

    private final OnToggle onToggle;
    private final OnClick  onClick;

    public TaskGridAdapter(OnToggle onToggle, OnClick onClick) {
        super(DIFF);
        this.onToggle = onToggle;
        this.onClick  = onClick;
    }

    private static final DiffUtil.ItemCallback<TaskWithStatus> DIFF = new DiffUtil.ItemCallback<>() {
        @Override public boolean areItemsTheSame(@NonNull TaskWithStatus a, @NonNull TaskWithStatus b) {
            return a.task.id == b.task.id;
        }
        @Override public boolean areContentsTheSame(@NonNull TaskWithStatus a, @NonNull TaskWithStatus b) {
            return a.completedToday == b.completedToday
                    && a.task.title.equals(b.task.title)
                    && a.task.currentStreak == b.task.currentStreak;
        }
    };

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTaskGridBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder h, int pos) { h.bind(getItem(pos)); }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ItemTaskGridBinding b;
        ViewHolder(ItemTaskGridBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        void bind(TaskWithStatus tws) {
            Task task = tws.task;
            b.tvTitle.setText(task.title);
            b.tvSubtitle.setText(task.subtitle);
            b.ivIcon.setImageResource(iconRes(task.iconType));

            // Streak badge
            if (task.currentStreak > 0) {
                b.tvStreak.setVisibility(View.VISIBLE);
                b.tvStreak.setText("🔥 " + task.currentStreak);
            } else {
                b.tvStreak.setVisibility(View.GONE);
            }

            applyState(tws.completedToday);

            b.toggleTask.setOnClickListener(v -> {
                boolean newState = !tws.completedToday;
                applyState(newState);
                onToggle.onToggle(task, newState);
            });

            b.getRoot().setOnClickListener(v -> onClick.onClick(task.id));
            b.getRoot().setOnLongClickListener(v -> { onClick.onClick(task.id); return true; });
        }

        void applyState(boolean done) {
            if (done) {
                b.toggleTask.setBackgroundResource(R.drawable.toggle_circle_checked);
                b.ivCheck.setVisibility(View.VISIBLE);
                b.tvTitle.setPaintFlags(b.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                b.tvTitle.setAlpha(0.45f);
                b.tvSubtitle.setAlpha(0.45f);
            } else {
                b.toggleTask.setBackgroundResource(R.drawable.toggle_circle_bg);
                b.ivCheck.setVisibility(View.INVISIBLE);
                b.tvTitle.setPaintFlags(b.tvTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                b.tvTitle.setAlpha(1f);
                b.tvSubtitle.setAlpha(1f);
            }
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
