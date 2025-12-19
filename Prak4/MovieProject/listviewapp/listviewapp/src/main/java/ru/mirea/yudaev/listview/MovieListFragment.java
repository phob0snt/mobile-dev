package ru.mirea.yudaev.listview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Фрагмент со списком фильмов (ListView)
 */
public class MovieListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.moviesListView);

        String[] movies = {
                "Побег из Шоушенка (1994)",
                "Крёстный отец (1972)",
                "Тёмный рыцарь (2008)",
                "Криминальное чтиво (1994)",
                "Список Шиндлера (1993)",
                "Форрест Гамп (1994)",
                "Начало (2010)",
                "Бойцовский клуб (1999)",
                "Матрица (1999)",
                "Интерстеллар (2014)"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                movies
        );

        listView.setAdapter(adapter);

        // Обработка клика на элемент
        listView.setOnItemClickListener((parent, itemView, position, id) -> {
            String selectedMovie = movies[position];
            
            // Создаём фрагмент с деталями
            MovieDetailsFragment detailsFragment = MovieDetailsFragment.newInstance(selectedMovie);
            
            // Навигация к деталям
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
