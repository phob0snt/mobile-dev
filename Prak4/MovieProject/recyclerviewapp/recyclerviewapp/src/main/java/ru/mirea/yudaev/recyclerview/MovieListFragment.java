package ru.mirea.yudaev.recyclerview;

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
 * Фрагмент со списком фильмов (RecyclerView)
 */
public class MovieListFragment extends Fragment {

    private SharedMovieViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedMovieViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.moviesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Movie> movies = getMovieList();
        
        MovieAdapter adapter = new MovieAdapter(movies, movie -> {
            // Передаём выбранный фильм в ViewModel
            viewModel.selectMovie(movie);

            // Навигация к деталям
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new MovieDetailsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(adapter);
    }

    private List<Movie> getMovieList() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Побег из Шоушенка", 1994, "Драма", 9.3));
        movies.add(new Movie("Крёстный отец", 1972, "Криминал", 9.2));
        movies.add(new Movie("Тёмный рыцарь", 2008, "Боевик", 9.0));
        movies.add(new Movie("Криминальное чтиво", 1994, "Криминал", 8.9));
        movies.add(new Movie("Список Шиндлера", 1993, "Драма", 8.9));
        movies.add(new Movie("Форрест Гамп", 1994, "Драма", 8.8));
        movies.add(new Movie("Начало", 2010, "Фантастика", 8.8));
        movies.add(new Movie("Бойцовский клуб", 1999, "Драма", 8.8));
        movies.add(new Movie("Матрица", 1999, "Фантастика", 8.7));
        movies.add(new Movie("Интерстеллар", 2014, "Фантастика", 8.6));
        return movies;
    }
}
