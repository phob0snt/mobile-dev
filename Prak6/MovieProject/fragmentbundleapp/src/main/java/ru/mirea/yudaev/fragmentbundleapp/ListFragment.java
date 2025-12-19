package ru.mirea.yudaev.fragmentbundleapp;

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
 * Фрагмент со списком элементов для демонстрации навигации
 */
public class ListFragment extends Fragment {

    private static final String[] MOVIES = {
            "Начало", "Интерстеллар", "Бойцовский клуб",
            "Матрица", "Форрест Гамп", "Побег из Шоушенка",
            "Зелёная миля", "Криминальное чтиво", "Престиж"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.movieListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                MOVIES
        );
        listView.setAdapter(adapter);

        // Навигация к фрагменту деталей
        listView.setOnItemClickListener((parent, itemView, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putString("movie_title", MOVIES[position]);
            bundle.putInt("movie_position", position + 1);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, DetailsFragment.class, bundle)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
