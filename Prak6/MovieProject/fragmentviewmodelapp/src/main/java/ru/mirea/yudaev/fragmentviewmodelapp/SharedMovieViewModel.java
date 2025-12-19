package ru.mirea.yudaev.fragmentviewmodelapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * SharedViewModel для обмена данными между фрагментами
 */
public class SharedMovieViewModel extends ViewModel {
    private final MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();

    public void selectMovie(Movie movie) {
        selectedMovie.setValue(movie);
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }
}
