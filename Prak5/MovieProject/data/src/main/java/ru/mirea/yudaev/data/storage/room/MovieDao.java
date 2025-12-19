package ru.mirea.yudaev.data.storage.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntity movie);

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    MovieEntity getMovieById(int id);

    @Query("SELECT * FROM movies")
    List<MovieEntity> getAllMovies();
}
