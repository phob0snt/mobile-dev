package ru.mirea.yudaev.retrofitapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Адаптер для отображения списка фильмов
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieDto> movies;

    public MovieAdapter(List<MovieDto> movies) {
        this.movies = movies;
    }

    public void updateMovies(List<MovieDto> newMovies) {
        this.movies = newMovies;
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
        MovieDto movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView posterImageView;
        private final TextView titleTextView;
        private final TextView yearTextView;
        private final TextView ratingTextView;
        private final TextView overviewTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            overviewTextView = itemView.findViewById(R.id.overviewTextView);
        }

        public void bind(MovieDto movie) {
            titleTextView.setText(movie.getTitle());
            
            // Извлечение года из даты релиза
            String releaseDate = movie.getReleaseDate();
            if (releaseDate != null && !releaseDate.isEmpty()) {
                String year = releaseDate.split("-")[0];
                yearTextView.setText(year);
            } else {
                yearTextView.setText("—");
            }
            
            ratingTextView.setText(String.format("★ %.1f", movie.getVoteAverage()));
            overviewTextView.setText(movie.getOverview());

            // Загрузка изображения с помощью Glide
            String posterUrl = movie.getFullPosterUrl();
            if (!posterUrl.isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(posterUrl)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.ic_menu_close_clear_cancel)
                        .into(posterImageView);
            } else {
                posterImageView.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        }
    }
}
