package ru.mirea.yudaev.Lesson9.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.yudaev.Lesson9.R;
import ru.mirea.yudaev.domain.models.Movie;

/**
 * Адаптер для отображения списка фильмов в RecyclerView
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies = new ArrayList<>();
    private OnMovieClickListener onMovieClickListener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieAdapter(OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    /**
     * Обновление списка фильмов
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /**
     * ViewHolder для элемента списка фильмов
     */
    class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView posterImageView;
        private final TextView titleTextView;
        private final TextView genreTextView;
        private final TextView yearTextView;
        private final TextView descriptionTextView;
        private final TextView ratingTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.moviePosterImageView);
            titleTextView = itemView.findViewById(R.id.movieTitleTextView);
            genreTextView = itemView.findViewById(R.id.movieGenreTextView);
            yearTextView = itemView.findViewById(R.id.movieYearTextView);
            descriptionTextView = itemView.findViewById(R.id.movieDescriptionTextView);
            ratingTextView = itemView.findViewById(R.id.movieRatingTextView);
        }

        public void bind(Movie movie) {
            titleTextView.setText(movie.getName());
            genreTextView.setText(movie.getGenre());
            yearTextView.setText(String.valueOf(movie.getYear()));
            descriptionTextView.setText(movie.getDescription());
            ratingTextView.setText(String.format("%.1f", movie.getRating()));

            // Загрузка изображения постера с помощью Glide
            if (movie.getImageUrl() != null && !movie.getImageUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(movie.getImageUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background) // плейсхолдер во время загрузки
                        .error(R.drawable.ic_launcher_background) // изображение при ошибке
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // кеширование
                        .into(posterImageView);
            } else {
                posterImageView.setImageResource(R.drawable.ic_launcher_background);
            }

            itemView.setOnClickListener(v -> {
                if (onMovieClickListener != null) {
                    onMovieClickListener.onMovieClick(movie);
                }
            });
        }
    }
}
