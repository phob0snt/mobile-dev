package ru.mirea.yudaev.data.storage.room;

import android.content.Context;

import ru.mirea.yudaev.domain.models.Movie;
import ru.mirea.yudaev.domain.repository.MovieRepository;

public class MovieRepositoryRoomImpl implements MovieRepository {
    private final MovieDao movieDao;

    public MovieRepositoryRoomImpl(Context context) {
        MovieDatabase database = MovieDatabase.getInstance(context);
        this.movieDao = database.movieDao();
    }

    @Override
    public boolean saveMovie(Movie movie) {
        try {
            MovieEntity entity = new MovieEntity(movie.getId(), movie.getName());
            movieDao.insertMovie(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Movie getMovie() {
        MovieEntity entity = movieDao.getMovieById(2);
        if (entity != null) {
            return new Movie(entity.getId(), entity.getName());
        }
        return new Movie(2, "Аватар");
    }
}
