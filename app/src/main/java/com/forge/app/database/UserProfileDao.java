package com.forge.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.forge.app.models.UserProfile;

@Dao
public interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) void insert(UserProfile p);
    @Update void update(UserProfile p);

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    LiveData<UserProfile> getProfile();

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    UserProfile getProfileSync();
}
