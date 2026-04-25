# FORGE — Habit Tracker

Dark-themed habit tracker with streaks, history calendar, and daily reminders.

## Features

| Feature | Details |
|---------|---------|
| **Daily toggle** | Check off habits each day; state resets at midnight |
| **Streak tracking** | Current streak + best streak, computed from full history |
| **History calendar** | 15-week GitHub-style heatmap per habit |
| **Daily reminders** | AlarmManager-based, per-task time picker |
| **Day progress bar** | X / N done today on home screen |
| **Midnight reset** | WorkManager job fires at 00:01 to update streaks |
| **Boot persistence** | Alarms rescheduled after device reboot |

## Architecture

```
models/
  Task.java              — habit definition + streak cache + reminder config
  TaskCompletion.java    — one row per (taskId, date), drives all history
  TaskWithStatus.java    — joined POJO: task + completedToday flag
  ForgeProject.java      — Core Heat project
  UserProfile.java       — name + lastSeenDate

database/
  TaskDao               — CRUD + today's count queries
  TaskCompletionDao     — insert/delete/query completion history
  AppDatabase           — Room DB with seed data + fake history

activities/
  MainViewModel         — MediatorLiveData merging tasks + today's completions
  MainActivity          — bottom nav host + notification permission
  TaskDetailActivity    — streak stats + heatmap + reminder toggle
  EditTaskActivity      — create/edit task with reminder time picker
  EditProjectActivity   — create/edit Core Heat project

fragments/
  HomeFragment          — greeting, day progress, Core Heat, featured card, grid
  ForgeFragment         — all tasks + projects management
  ProfileFragment       — name, today/total stats

adapters/
  TaskGridAdapter       — 2×2 grid with streak badge + toggle
  TaskListAdapter       — list with streak chip + edit/delete + detail tap
  ProjectAdapter        — project cards

utils/
  DateUtils             — today(), streak computation (current + best)
  HabitCalendarView     — custom View: 15-week contribution heatmap
  NotificationHelper    — AlarmManager scheduling + channel creation
  NotificationReceiver  — BroadcastReceiver fires the notification
  BootReceiver          — reschedules alarms after reboot

workers/
  MidnightResetWorker   — WorkManager: updates lastSeenDate + recalculates streaks
```

## How streaks work

- `TaskCompletion` stores `(taskId, "yyyy-MM-dd")` — one row per completion
- `DateUtils.computeStreak()` walks backwards from today; counts consecutive days
- Today doesn't have to be completed yet — streak continues from yesterday
- `MidnightResetWorker` recalculates and caches `currentStreak` / `bestStreak` on each Task row nightly

## Setup

1. Unzip → `File → Open` in Android Studio → select `ForgeApp/`
2. Let Gradle sync (downloads dependencies automatically)
3. Run on emulator or device — **API 26+** required

## Permissions requested at runtime

- `POST_NOTIFICATIONS` (Android 13+) — for habit reminders
- `SCHEDULE_EXACT_ALARM` — for precise daily alarm scheduling
# Forge
