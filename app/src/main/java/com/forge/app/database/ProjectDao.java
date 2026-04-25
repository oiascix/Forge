package com.forge.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.forge.app.models.ForgeProject;
import java.util.List;

@Dao
public interface ProjectDao {
    @Insert long insert(ForgeProject project);
    @Update void update(ForgeProject project);
    @Delete void delete(ForgeProject project);

    @Query("SELECT * FROM projects ORDER BY id DESC")
    LiveData<List<ForgeProject>> getAllProjects();

    @Query("SELECT * FROM projects WHERE isActive = 1 LIMIT 1")
    LiveData<ForgeProject> getActiveProject();
}
