package com.forge.app.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.forge.app.models.*;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
    entities = {Task.class, TaskCompletion.class, ForgeProject.class, UserProfile.class},
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract TaskCompletionDao taskCompletionDao();
    public abstract ProjectDao projectDao();
    public abstract UserProfileDao userProfileDao();

    private static volatile AppDatabase INSTANCE;
    public static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "forge_database")
                            .fallbackToDestructiveMigration()  // 🔥 Эта строка обязательна!
                            .addCallback(seedCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback seedCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            executor.execute(() -> {
                String today = LocalDate.now().toString();

                UserProfileDao profileDao = INSTANCE.userProfileDao();
                profileDao.insert(new UserProfile("Sir", today));

                ProjectDao projectDao = INSTANCE.projectDao();
                projectDao.insert(new ForgeProject("Titanium Blade", 1500, 2000, "two days left"));

                TaskDao taskDao = INSTANCE.taskDao();
                long id1 = taskDao.insert(new Task("FIGMA UI DESIGN", "DAILY PROTOCOL",  "brain", true,  0));
                long id2 = taskDao.insert(new Task("ANKI",            "50 CARDS",        "book",  false, 1));
                long id3 = taskDao.insert(new Task("GYM",             "WELL DONE, SIR",  "gym",   false, 2));
                long id4 = taskDao.insert(new Task("DRINK 2L",        "YOU CAN DO IT",   "drop",  false, 3));
                long id5 = taskDao.insert(new Task("PROGRAMMING",     "<LET'S GO>",      "code",  false, 4));

                // Seed some fake history so streaks are visible from day 1
                TaskCompletionDao cDao = INSTANCE.taskCompletionDao();
                seedHistory(cDao, (int) id1, today, 5);
                seedHistory(cDao, (int) id2, today, 3);
                seedHistory(cDao, (int) id3, today, 7);
                seedHistory(cDao, (int) id4, today, 2);
                seedHistory(cDao, (int) id5, today, 4);
            });
        }
    };

    /** Insert consecutive completion records ending on `endDate` for `days` days. */
    private static void seedHistory(TaskCompletionDao dao, int taskId, String endDate, int days) {
        LocalDate date = LocalDate.parse(endDate);
        for (int i = 0; i < days; i++) {
            dao.insert(new TaskCompletion(taskId, date.minusDays(i).toString()));
        }
    }
}
