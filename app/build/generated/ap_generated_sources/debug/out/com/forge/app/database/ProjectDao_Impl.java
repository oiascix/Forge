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
import com.forge.app.models.ForgeProject;
import java.lang.Class;
import java.lang.Exception;
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
public final class ProjectDao_Impl implements ProjectDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ForgeProject> __insertionAdapterOfForgeProject;

  private final EntityDeletionOrUpdateAdapter<ForgeProject> __deletionAdapterOfForgeProject;

  private final EntityDeletionOrUpdateAdapter<ForgeProject> __updateAdapterOfForgeProject;

  public ProjectDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfForgeProject = new EntityInsertionAdapter<ForgeProject>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `projects` (`id`,`name`,`currentHeat`,`maxHeat`,`timeLeft`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ForgeProject entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        statement.bindLong(3, entity.currentHeat);
        statement.bindLong(4, entity.maxHeat);
        if (entity.timeLeft == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.timeLeft);
        }
        final int _tmp = entity.isActive ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
    this.__deletionAdapterOfForgeProject = new EntityDeletionOrUpdateAdapter<ForgeProject>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `projects` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ForgeProject entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfForgeProject = new EntityDeletionOrUpdateAdapter<ForgeProject>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `projects` SET `id` = ?,`name` = ?,`currentHeat` = ?,`maxHeat` = ?,`timeLeft` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ForgeProject entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        statement.bindLong(3, entity.currentHeat);
        statement.bindLong(4, entity.maxHeat);
        if (entity.timeLeft == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.timeLeft);
        }
        final int _tmp = entity.isActive ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.id);
      }
    };
  }

  @Override
  public long insert(final ForgeProject project) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfForgeProject.insertAndReturnId(project);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final ForgeProject project) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfForgeProject.handle(project);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final ForgeProject project) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfForgeProject.handle(project);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<ForgeProject>> getAllProjects() {
    final String _sql = "SELECT * FROM projects ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"projects"}, false, new Callable<List<ForgeProject>>() {
      @Override
      @Nullable
      public List<ForgeProject> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCurrentHeat = CursorUtil.getColumnIndexOrThrow(_cursor, "currentHeat");
          final int _cursorIndexOfMaxHeat = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHeat");
          final int _cursorIndexOfTimeLeft = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLeft");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<ForgeProject> _result = new ArrayList<ForgeProject>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ForgeProject _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final int _tmpCurrentHeat;
            _tmpCurrentHeat = _cursor.getInt(_cursorIndexOfCurrentHeat);
            final int _tmpMaxHeat;
            _tmpMaxHeat = _cursor.getInt(_cursorIndexOfMaxHeat);
            final String _tmpTimeLeft;
            if (_cursor.isNull(_cursorIndexOfTimeLeft)) {
              _tmpTimeLeft = null;
            } else {
              _tmpTimeLeft = _cursor.getString(_cursorIndexOfTimeLeft);
            }
            _item = new ForgeProject(_tmpName,_tmpCurrentHeat,_tmpMaxHeat,_tmpTimeLeft);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _item.isActive = _tmp != 0;
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
  public LiveData<ForgeProject> getActiveProject() {
    final String _sql = "SELECT * FROM projects WHERE isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"projects"}, false, new Callable<ForgeProject>() {
      @Override
      @Nullable
      public ForgeProject call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCurrentHeat = CursorUtil.getColumnIndexOrThrow(_cursor, "currentHeat");
          final int _cursorIndexOfMaxHeat = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHeat");
          final int _cursorIndexOfTimeLeft = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLeft");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final ForgeProject _result;
          if (_cursor.moveToFirst()) {
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final int _tmpCurrentHeat;
            _tmpCurrentHeat = _cursor.getInt(_cursorIndexOfCurrentHeat);
            final int _tmpMaxHeat;
            _tmpMaxHeat = _cursor.getInt(_cursorIndexOfMaxHeat);
            final String _tmpTimeLeft;
            if (_cursor.isNull(_cursorIndexOfTimeLeft)) {
              _tmpTimeLeft = null;
            } else {
              _tmpTimeLeft = _cursor.getString(_cursorIndexOfTimeLeft);
            }
            _result = new ForgeProject(_tmpName,_tmpCurrentHeat,_tmpMaxHeat,_tmpTimeLeft);
            _result.id = _cursor.getInt(_cursorIndexOfId);
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _result.isActive = _tmp != 0;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
