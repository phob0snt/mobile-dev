package ru.mirea.yudaev.data.storage.room;

import android.content.Context;

import java.util.List;

import ru.mirea.yudaev.data.network.FakeMovieApi;
import ru.mirea.yudaev.domain.models.Movie;
import ru.mirea.yudaev.domain.repository.MovieRepository;

public class MovieRepositoryRoomImpl implements MovieRepository {
    private final MovieDao movieDao;
    private final FakeMovieApi fakeMovieApi;

    public MovieRepositoryRoomImpl(Context context) {
        MovieDatabase database = MovieDatabase.getInstance(context);
        this.movieDao = database.movieDao();
        this.fakeMovieApi = new FakeMovieApi();
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

    @Override
    public List<Movie> getAllMovies() {
        return fakeMovieApi.fetchMovies();
    }
}
