package ru.mirea.yudaev.Lesson9.presentation.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.yudaev.data.firebase.FirebaseAuthRepository;
import ru.mirea.yudaev.data.storage.room.MovieRepositoryRoomImpl;
import ru.mirea.yudaev.data.storage.sharedprefs.UserRepositoryImpl;
import ru.mirea.yudaev.domain.repository.AuthRepository;
import ru.mirea.yudaev.domain.repository.MovieRepository;
import ru.mirea.yudaev.domain.repository.UserRepository;

/**
 * Фабрика для создания ViewModel с инъекцией зависимостей.
 * Обеспечивает правильное создание экземпляров ViewModel с необходимыми зависимостями.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public ViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            MovieRepository movieRepository = new MovieRepositoryRoomImpl(context);
            return (T) new MainViewModel(movieRepository);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            AuthRepository authRepository = new FirebaseAuthRepository();
            UserRepository userRepository = new UserRepositoryImpl(context);
            return (T) new LoginViewModel(authRepository, userRepository);
        } else if (modelClass.isAssignableFrom(MovieListViewModel.class)) {
            MovieRepository movieRepository = new MovieRepositoryRoomImpl(context);
            return (T) new MovieListViewModel(movieRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
