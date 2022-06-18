package com.example.coviddefender.db.covidstatus;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

// mapping of query and functions
@Dao
public interface CovidStatusDao {
    //CRUD

    // Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CovidStatus covidStatus);

    // Read
    @Query("SELECT * FROM covid_status_table ORDER BY id DESC")
    LiveData<List<CovidStatus>> getAllCovidStatus();

    // Delete
    @Query("DELETE FROM covid_status_table")
    void deleteAll();

}
