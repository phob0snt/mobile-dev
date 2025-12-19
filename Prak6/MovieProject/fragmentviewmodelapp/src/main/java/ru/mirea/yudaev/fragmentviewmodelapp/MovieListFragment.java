package ru.mirea.yudaev.fragmentviewmodelapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Фрагмент со списком фильмов
 */
public class MovieListFragment extends Fragment {

    private SharedMovieViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Получаем SharedViewModel на уровне Activity
        viewModel = new ViewModelProvider(requireActivity()).get(SharedMovieViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.moviesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Movie> movies = createMovieList();
        MovieAdapter adapter = new MovieAdapter(movies, movie -> {
            // При клике устанавливаем фильм в SharedViewModel
            viewModel.selectMovie(movie);

            // Переходим к фрагменту деталей
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, MovieDetailsFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(adapter);
    }

    private List<Movie> createMovieList() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Начало", "Кристофер Нолан", 2010, "Фантастика", 8.8));
        movies.add(new Movie("Интерстеллар", "Кристофер Нолан", 2014, "Фантастика", 8.6));
        movies.add(new Movie("Бойцовский клуб", "Дэвид Финчер", 1999, "Триллер", 8.8));
        movies.add(new Movie("Матрица", "Вачовски", 1999, "Фантастика", 8.7));
        movies.add(new Movie("Форрест Гамп", "Роберт Земекис", 1994, "Драма", 8.9));
        movies.add(new Movie("Побег из Шоушенка", "Фрэнк Дарабонт", 1994, "Драма", 9.3));
        movies.add(new Movie("Зелёная миля", "Фрэнк Дарабонт", 1999, "Драма", 8.6));
        movies.add(new Movie("Криминальное чтиво", "Квентин Тарантино", 1994, "Криминал", 8.9));
        return movies;
    }
}
