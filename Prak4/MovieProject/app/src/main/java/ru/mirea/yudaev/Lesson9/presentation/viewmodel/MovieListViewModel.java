package ru.mirea.yudaev.Lesson9.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.mirea.yudaev.domain.models.Movie;
import ru.mirea.yudaev.domain.repository.MovieRepository;
import ru.mirea.yudaev.domain.usecases.GetMovieListUseCase;

/**
 * ViewModel для отображения списка фильмов с использованием RecyclerView
 */
public class MovieListViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private final ExecutorService executorService;
    
    // LiveData для списка фильмов
    private final MutableLiveData<List<Movie>> _movies = new MutableLiveData<>();
    public final LiveData<List<Movie>> movies = _movies;
    
    // LiveData для состояния загрузки
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;
    
    // LiveData для ошибок
    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public final LiveData<String> error = _error;

    public MovieListViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Загрузка списка фильмов
     */
    public void loadMovies() {
        _isLoading.setValue(true);
        executorService.execute(() -> {
            try {
                List<Movie> movieList = new GetMovieListUseCase(movieRepository).execute();
                _movies.postValue(movieList);
                _isLoading.postValue(false);
            } catch (Exception e) {
                _error.postValue("Ошибка загрузки фильмов: " + e.getMessage());
                _isLoading.postValue(false);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
