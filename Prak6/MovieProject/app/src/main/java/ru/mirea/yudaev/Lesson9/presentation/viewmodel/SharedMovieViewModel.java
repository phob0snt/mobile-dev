package ru.mirea.yudaev.Lesson9.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.yudaev.domain.models.Movie;

/**
 * Shared ViewModel для обмена данными между фрагментами
 * Используется на уровне Activity, чтобы несколько фрагментов могли
 * получить доступ к одному экземпляру
 */
public class SharedMovieViewModel extends ViewModel {
    
    private final MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();
    
    /**
     * Выбрать фильм для отображения в деталях
     */
    public void selectMovie(Movie movie) {
        selectedMovie.setValue(movie);
    }
    
    /**
     * Получить LiveData выбранного фильма
     */
    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }
}
