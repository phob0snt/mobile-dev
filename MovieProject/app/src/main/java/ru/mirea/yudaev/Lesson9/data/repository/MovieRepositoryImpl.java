package ru.mirea.yudaev.Lesson9.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.yudaev.Lesson9.domain.repository.MovieRepository;
import ru.mirea.yudaev.Lesson9.domain.models.Movie;

public class MovieRepositoryImpl implements MovieRepository {

  private final SharedPreferences sharedPreferences;
  private static final String PREFS_NAME = "movie_prefs";
  private static final String KEY_MOVIE_ID = "movie_id";
  private static final String KEY_MOVIE_NAME = "movie_name";

  public MovieRepositoryImpl(Context context) {
    this.sharedPreferences = context.getApplicationContext()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
  }

  @Override
  public boolean saveMovie(Movie movie) {
    return sharedPreferences.edit()
            .putInt(KEY_MOVIE_ID, movie.getId())
            .putString(KEY_MOVIE_NAME, movie.getName())
            .commit();
  }

  @Override
  public Movie getMovie() {
    int id = sharedPreferences.getInt(KEY_MOVIE_ID, 1);
    String name = sharedPreferences.getString(KEY_MOVIE_NAME, "Аватар");
    return new Movie(id, name);
  }
}