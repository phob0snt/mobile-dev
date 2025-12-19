package ru.mirea.yudaev.data.mapper;

import ru.mirea.yudaev.data.network.dto.MovieDto;
import ru.mirea.yudaev.domain.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Маппер для преобразования между DTO и доменными моделями
 */
public class MovieMapper {
    
    /**
     * Преобразование MovieDto в Movie
     */
    public static Movie mapToDomain(MovieDto dto) {
        if (dto == null) {
            return null;
        }
        
        return new Movie(
                dto.getId(),
                dto.getTitle(),
                dto.getOverview() != null ? dto.getOverview() : "Описание отсутствует",
                mapGenreIdsToString(dto.getGenreIds()),
                extractYearFromDate(dto.getReleaseDate()),
                dto.getVoteAverage(),
                dto.getFullPosterUrl()
        );
    }
    
    /**
     * Преобразование списка MovieDto в список Movie
     */
    public static List<Movie> mapToDomainList(List<MovieDto> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }
        
        List<Movie> movies = new ArrayList<>();
        for (MovieDto dto : dtos) {
            Movie movie = mapToDomain(dto);
            if (movie != null) {
                movies.add(movie);
            }
        }
        return movies;
    }
    
    /**
     * Преобразование списка ID жанров в строку
     * Упрощенная версия - просто берем первый жанр
     * В реальном приложении лучше использовать /genre/movie/list API
     */
    private static String mapGenreIdsToString(List<Integer> genreIds) {
        if (genreIds == null || genreIds.isEmpty()) {
            return "Разное";
        }
        
        // Маппинг основных жанров TMDB
        Integer firstGenreId = genreIds.get(0);
        switch (firstGenreId) {
            case 28: return "Боевик";
            case 12: return "Приключения";
            case 16: return "Мультфильм";
            case 35: return "Комедия";
            case 80: return "Криминал";
            case 99: return "Документальный";
            case 18: return "Драма";
            case 10751: return "Семейный";
            case 14: return "Фэнтези";
            case 36: return "История";
            case 27: return "Ужасы";
            case 10402: return "Музыка";
            case 9648: return "Детектив";
            case 10749: return "Мелодрама";
            case 878: return "Фантастика";
            case 10770: return "ТВ фильм";
            case 53: return "Триллер";
            case 10752: return "Военный";
            case 37: return "Вестерн";
            default: return "Разное";
        }
    }
    
    /**
     * Извлечение года из даты формата "YYYY-MM-DD"
     */
    private static int extractYearFromDate(String releaseDate) {
        if (releaseDate == null || releaseDate.isEmpty() || releaseDate.length() < 4) {
            return 0;
        }
        
        try {
            return Integer.parseInt(releaseDate.substring(0, 4));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
