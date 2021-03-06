package com.example.nutritiontrackerguiv4.database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface DayDAO {

    @Insert
    void insert(Day day);

    @Update
    void update(Day day);

    @Delete
    void delete(Day day);

    @Query("SELECT * FROM Days")
    List<Day> getAllDays();

    @Query("SELECT * FROM Days WHERE Day_ID=:dayId")
    List<Day> findAllInfoForDays(final int dayId);


}
