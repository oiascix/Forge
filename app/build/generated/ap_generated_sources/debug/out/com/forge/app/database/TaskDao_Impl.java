package com.forge.app.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.forge.app.models.Task;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Task> __insertionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Task> __deletionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Task> __updateAdapterOfTask;

  public TaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTask = new EntityInsertionAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `tasks` (`id`,`title`,`subtitle`,`iconType`,`isFeatured`,`sortOrder`,`reminderEnabled`,`reminderHour`,`reminderMinute`,`currentStreak`,`bestStreak`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Task entity) {
        statement.bindLong(1, entity.id);
        if (entity.title == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.title);
        }
        if (entity.subtitle == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.subtitle);
        }
        if (entity.iconType == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.iconType);
        }
        final int _tmp = entity.isFeatured ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.sortOrder);
        final int _tmp_1 = entity.reminderEnabled ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        statement.bindLong(8, entity.reminderHour);
        statement.bindLong(9, entity.reminderMinute);
        statement.bindLong(10, entity.currentStreak);
        statement.bindLong(11, entity.bestStreak);
      }
    };
    this.__deletionAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tasks` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Task entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tasks` SET `id` = ?,`title` = ?,`subtitle` = ?,`iconType` = ?,`isFeatured` = ?,`sortOrder` = ?,`reminderEnabled` = ?,`reminderHour` = ?,`reminderMinute` = ?,`currentStreak` = ?,`bestStreak` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Task entity) {
        statement.bindLong(1, entity.id);
        if (entity.title == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.title);
        }
        if (entity.subtitle == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.subtitle);
        }
        if (entity.iconType == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.iconType);
        }
        final int _tmp = entity.isFeatured ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.sortOrder);
        final int _tmp_1 = entity.reminderEnabled ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        statement.bindLong(8, entity.reminderHour);
        statement.bindLong(9, entity.reminderMinute);
        statement.bindLong(10, entity.currentStreak);
        statement.bindLong(11, entity.bestStreak);
        statement.bindLong(12, entity.id);
      }
    };
  }

  @Override
  public long insert(final Task task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfTask.insertAndReturnId(task);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Task task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTask.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Task task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTask.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Task>> getAllTasks() {
    final String _sql = "SELECT * FROM tasks ORDER BY isFeatured DESC, sortOrder ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tasks"}, false, new Callable<List<Task>>() {
      @Override
      @Nullable
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
          final int _cursorIndexOfIconType = CursorUtil.getColumnIndexOrThrow(_cursor, "iconType");
          final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderHour = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderHour");
          final int _cursorIndexOfReminderMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinute");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfBestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "bestStreak");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpSubtitle;
            if (_cursor.isNull(_cursorIndexOfSubtitle)) {
              _tmpSubtitle = null;
            } else {
              _tmpSubtitle = _cursor.getString(_cursorIndexOfSubtitle);
            }
            final String _tmpIconType;
            if (_cursor.isNull(_cursorIndexOfIconType)) {
              _tmpIconType = null;
            } else {
              _tmpIconType = _cursor.getString(_cursorIndexOfIconType);
            }
            final boolean _tmpIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
            _tmpIsFeatured = _tmp != 0;
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            _item = new Task(_tmpTitle,_tmpSubtitle,_tmpIconType,_tmpIsFeatured,_tmpSortOrder);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _item.reminderEnabled = _tmp_1 != 0;
            _item.reminderHour = _cursor.getInt(_cursorIndexOfReminderHour);
            _item.reminderMinute = _cursor.getInt(_cursorIndexOfReminderMinute);
            _item.currentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            _item.bestStreak = _cursor.getInt(_cursorIndexOfBestStreak);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<Task> getAllTasksSync() {
    final String _sql = "SELECT * FROM tasks ORDER BY isFeatured DESC, sortOrder ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
      final int _cursorIndexOfIconType = CursorUtil.getColumnIndexOrThrow(_cursor, "iconType");
      final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
      final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
      final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
      final int _cursorIndexOfReminderHour = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderHour");
      final int _cursorIndexOfReminderMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinute");
      final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
      final int _cursorIndexOfBestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "bestStreak");
      final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Task _item;
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpSubtitle;
        if (_cursor.isNull(_cursorIndexOfSubtitle)) {
          _tmpSubtitle = null;
        } else {
          _tmpSubtitle = _cursor.getString(_cursorIndexOfSubtitle);
        }
        final String _tmpIconType;
        if (_cursor.isNull(_cursorIndexOfIconType)) {
          _tmpIconType = null;
        } else {
          _tmpIconType = _cursor.getString(_cursorIndexOfIconType);
        }
        final boolean _tmpIsFeatured;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
        _tmpIsFeatured = _tmp != 0;
        final int _tmpSortOrder;
        _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
        _item = new Task(_tmpTitle,_tmpSubtitle,_tmpIconType,_tmpIsFeatured,_tmpSortOrder);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
        _item.reminderEnabled = _tmp_1 != 0;
        _item.reminderHour = _cursor.getInt(_cursorIndexOfReminderHour);
        _item.reminderMinute = _cursor.getInt(_cursorIndexOfReminderMinute);
        _item.currentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
        _item.bestStreak = _cursor.getInt(_cursorIndexOfBestStreak);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Task> getFeaturedTask() {
    final String _sql = "SELECT * FROM tasks WHERE isFeatured = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tasks"}, false, new Callable<Task>() {
      @Override
      @Nullable
      public Task call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
          final int _cursorIndexOfIconType = CursorUtil.getColumnIndexOrThrow(_cursor, "iconType");
          final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderHour = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderHour");
          final int _cursorIndexOfReminderMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinute");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfBestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "bestStreak");
          final Task _result;
          if (_cursor.moveToFirst()) {
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpSubtitle;
            if (_cursor.isNull(_cursorIndexOfSubtitle)) {
              _tmpSubtitle = null;
            } else {
              _tmpSubtitle = _cursor.getString(_cursorIndexOfSubtitle);
            }
            final String _tmpIconType;
            if (_cursor.isNull(_cursorIndexOfIconType)) {
              _tmpIconType = null;
            } else {
              _tmpIconType = _cursor.getString(_cursorIndexOfIconType);
            }
            final boolean _tmpIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
            _tmpIsFeatured = _tmp != 0;
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            _result = new Task(_tmpTitle,_tmpSubtitle,_tmpIconType,_tmpIsFeatured,_tmpSortOrder);
            _result.id = _cursor.getInt(_cursorIndexOfId);
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _result.reminderEnabled = _tmp_1 != 0;
            _result.reminderHour = _cursor.getInt(_cursorIndexOfReminderHour);
            _result.reminderMinute = _cursor.getInt(_cursorIndexOfReminderMinute);
            _result.currentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            _result.bestStreak = _cursor.getInt(_cursorIndexOfBestStreak);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Task>> getRegularTasks() {
    final String _sql = "SELECT * FROM tasks WHERE isFeatured = 0 ORDER BY sortOrder ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tasks"}, false, new Callable<List<Task>>() {
      @Override
      @Nullable
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
          final int _cursorIndexOfIconType = CursorUtil.getColumnIndexOrThrow(_cursor, "iconType");
          final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderHour = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderHour");
          final int _cursorIndexOfReminderMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinute");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfBestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "bestStreak");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpSubtitle;
            if (_cursor.isNull(_cursorIndexOfSubtitle)) {
              _tmpSubtitle = null;
            } else {
              _tmpSubtitle = _cursor.getString(_cursorIndexOfSubtitle);
            }
            final String _tmpIconType;
            if (_cursor.isNull(_cursorIndexOfIconType)) {
              _tmpIconType = null;
            } else {
              _tmpIconType = _cursor.getString(_cursorIndexOfIconType);
            }
            final boolean _tmpIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
            _tmpIsFeatured = _tmp != 0;
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            _item = new Task(_tmpTitle,_tmpSubtitle,_tmpIconType,_tmpIsFeatured,_tmpSortOrder);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _item.reminderEnabled = _tmp_1 != 0;
            _item.reminderHour = _cursor.getInt(_cursorIndexOfReminderHour);
            _item.reminderMinute = _cursor.getInt(_cursorIndexOfReminderMinute);
            _item.currentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            _item.bestStreak = _cursor.getInt(_cursorIndexOfBestStreak);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Task> getTaskById(final int id) {
    final String _sql = "SELECT * FROM tasks WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tasks"}, false, new Callable<Task>() {
      @Override
      @Nullable
      public Task call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
          final int _cursorIndexOfIconType = CursorUtil.getColumnIndexOrThrow(_cursor, "iconType");
          final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderHour = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderHour");
          final int _cursorIndexOfReminderMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinute");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfBestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "bestStreak");
          final Task _result;
          if (_cursor.moveToFirst()) {
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpSubtitle;
            if (_cursor.isNull(_cursorIndexOfSubtitle)) {
              _tmpSubtitle = null;
            } else {
              _tmpSubtitle = _cursor.getString(_cursorIndexOfSubtitle);
            }
            final String _tmpIconType;
            if (_cursor.isNull(_cursorIndexOfIconType)) {
              _tmpIconType = null;
            } else {
              _tmpIconType = _cursor.getString(_cursorIndexOfIconType);
            }
            final boolean _tmpIsFeatured;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
            _tmpIsFeatured = _tmp != 0;
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            _result = new Task(_tmpTitle,_tmpSubtitle,_tmpIconType,_tmpIsFeatured,_tmpSortOrder);
            _result.id = _cursor.getInt(_cursorIndexOfId);
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _result.reminderEnabled = _tmp_1 != 0;
            _result.reminderHour = _cursor.getInt(_cursorIndexOfReminderHour);
            _result.reminderMinute = _cursor.getInt(_cursorIndexOfReminderMinute);
            _result.currentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            _result.bestStreak = _cursor.getInt(_cursorIndexOfBestStreak);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Task getTaskByIdSync(final int id) {
    final String _sql = "SELECT * FROM tasks WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
      final int _cursorIndexOfIconType = CursorUtil.getColumnIndexOrThrow(_cursor, "iconType");
      final int _cursorIndexOfIsFeatured = CursorUtil.getColumnIndexOrThrow(_cursor, "isFeatured");
      final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
      final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
      final int _cursorIndexOfReminderHour = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderHour");
      final int _cursorIndexOfReminderMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinute");
      final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
      final int _cursorIndexOfBestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "bestStreak");
      final Task _result;
      if (_cursor.moveToFirst()) {
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpSubtitle;
        if (_cursor.isNull(_cursorIndexOfSubtitle)) {
          _tmpSubtitle = null;
        } else {
          _tmpSubtitle = _cursor.getString(_cursorIndexOfSubtitle);
        }
        final String _tmpIconType;
        if (_cursor.isNull(_cursorIndexOfIconType)) {
          _tmpIconType = null;
        } else {
          _tmpIconType = _cursor.getString(_cursorIndexOfIconType);
        }
        final boolean _tmpIsFeatured;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFeatured);
        _tmpIsFeatured = _tmp != 0;
        final int _tmpSortOrder;
        _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
        _result = new Task(_tmpTitle,_tmpSubtitle,_tmpIconType,_tmpIsFeatured,_tmpSortOrder);
        _result.id = _cursor.getInt(_cursorIndexOfId);
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
        _result.reminderEnabled = _tmp_1 != 0;
        _result.reminderHour = _cursor.getInt(_cursorIndexOfReminderHour);
        _result.reminderMinute = _cursor.getInt(_cursorIndexOfReminderMinute);
        _result.currentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
        _result.bestStreak = _cursor.getInt(_cursorIndexOfBestStreak);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getTotalCount() {
    final String _sql = "SELECT COUNT(*) FROM tasks";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tasks"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getCompletedCountForDate(final String date) {
    final String _sql = "SELECT COUNT(*) FROM task_completions WHERE dateString = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"task_completions"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int getCompletedCountForDateSync(final String date) {
    final String _sql = "SELECT COUNT(*) FROM task_completions WHERE dateString = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
