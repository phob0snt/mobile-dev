package ru.mirea.yudaev.Lesson9.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.yudaev.Lesson9.R;
import ru.mirea.yudaev.domain.models.Movie;

/**
 * Адаптер для отображения списка фильмов во фрагменте
 */
public class MovieFragmentAdapter extends RecyclerView.Adapter<MovieFragmentAdapter.ViewHolder> {
    
    private final List<Movie> movies;
    private final OnMovieClickListener listener;
    
    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
    
    public MovieFragmentAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_simple, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie, listener);
    }
    
    @Override
    public int getItemCount() {
        return movies.size();
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView yearTextView;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.movieTitleTextView);
            yearTextView = itemView.findViewById(R.id.movieYearTextView);
        }
        
        public void bind(Movie movie, OnMovieClickListener listener) {
            titleTextView.setText(movie.getName());
            yearTextView.setText(String.valueOf(movie.getYear()));
            
            itemView.setOnClickListener(v -> listener.onMovieClick(movie));
        }
    }
}
