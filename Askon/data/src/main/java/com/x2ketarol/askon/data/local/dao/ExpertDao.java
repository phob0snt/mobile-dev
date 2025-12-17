package com.x2ketarol.askon.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.x2ketarol.askon.data.local.entity.ExpertEntity;

import java.util.List;

@Dao
public interface ExpertDao {
    
    @Query("SELECT * FROM experts")
    List<ExpertEntity> getAllExperts();
    
    @Query("SELECT * FROM experts WHERE id = :expertId")
    ExpertEntity getExpertById(String expertId);
    
    @Query("SELECT * FROM experts WHERE name LIKE '%' || :query || '%' OR specialty LIKE '%' || :query || '%'")
    List<ExpertEntity> searchExperts(String query);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExpert(ExpertEntity expert);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExperts(List<ExpertEntity> experts);
    
    @Query("DELETE FROM experts")
    void deleteAll();
}
