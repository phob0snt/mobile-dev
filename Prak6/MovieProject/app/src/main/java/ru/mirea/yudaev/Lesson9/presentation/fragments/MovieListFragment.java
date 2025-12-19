package ru.mirea.yudaev.Lesson9.presentation.fragments;

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

import ru.mirea.yudaev.Lesson9.R;
import ru.mirea.yudaev.Lesson9.presentation.adapter.MovieFragmentAdapter;
import ru.mirea.yudaev.Lesson9.presentation.viewmodel.SharedMovieViewModel;
import ru.mirea.yudaev.domain.models.Movie;

/**
 * Фрагмент со списком фильмов
 */
public class MovieListFragment extends Fragment {
    
    private SharedMovieViewModel viewModel;
    private RecyclerView recyclerView;
    private MovieFragmentAdapter adapter;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Получаем shared ViewModel на уровне Activity
        viewModel = new ViewModelProvider(requireActivity()).get(SharedMovieViewModel.class);
        
        recyclerView = view.findViewById(R.id.moviesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Создаём adapter с обработчиком кликов
        adapter = new MovieFragmentAdapter(getMovieList(), movie -> {
            // При клике передаём выбранный фильм в ViewModel
            viewModel.selectMovie(movie);
            
            // Навигация на DetailsFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new MovieDetailsFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
        
        recyclerView.setAdapter(adapter);
    }
    
    private List<Movie> getMovieList() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Побег из Шоушенка", "История банкира", "Драма", 1994, 9.3, ""));
        movies.add(new Movie(2, "Крёстный отец", "Хроника семьи Корлеоне", "Криминал", 1972, 9.2, ""));
        movies.add(new Movie(3, "Тёмный рыцарь", "Борьба с Джокером", "Боевик", 2008, 9.0, ""));
        movies.add(new Movie(4, "Криминальное чтиво", "Истории гангстеров", "Криминал", 1994, 8.9, ""));
        movies.add(new Movie(5, "Список Шиндлера", "Спасение евреев", "Драма", 1993, 8.9, ""));
        return movies;
    }
}
