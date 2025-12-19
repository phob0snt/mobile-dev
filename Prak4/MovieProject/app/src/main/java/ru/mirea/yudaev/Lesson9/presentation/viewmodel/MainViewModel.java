package ru.mirea.yudaev.Lesson9.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.mirea.yudaev.domain.models.Movie;
import ru.mirea.yudaev.domain.repository.MovieRepository;
import ru.mirea.yudaev.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.yudaev.domain.usecases.SaveMovieToFavoriteUseCase;

/**
 * ViewModel для главного экрана приложения.
 * Управляет бизнес-логикой работы с фильмами и обеспечивает реактивность через LiveData.
 */
public class MainViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private final ExecutorService executorService;
    
    // LiveData для текста результата операции
    private final MutableLiveData<String> _movieTextLiveData = new MutableLiveData<>();
    public final LiveData<String> movieTextLiveData = _movieTextLiveData;
    
    // LiveData для состояния загрузки
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;

    public MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Сохранение фильма в избранное
     * @param movieName Название фильма
     */
    public void saveMovie(String movieName) {
        _isLoading.setValue(true);
        executorService.execute(() -> {
            Movie movie = new Movie(2, movieName);
            boolean result = new SaveMovieToFavoriteUseCase(movieRepository).execute(movie);
            
            // Обновляем UI в главном потоке
            _movieTextLiveData.postValue(result ? 
                "Фильм сохранен: " + movieName : 
                "Ошибка: пустое имя фильма");
            _isLoading.postValue(false);
        });
    }

    /**
     * Получение избранного фильма
     */
    public void loadFavoriteMovie() {
        _isLoading.setValue(true);
        executorService.execute(() -> {
            Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
            
            // Обновляем UI в главном потоке
            _movieTextLiveData.postValue(movie != null ? 
                "Избранный фильм: " + movie.getName() : 
                "Нет избранного фильма");
            _isLoading.postValue(false);
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
