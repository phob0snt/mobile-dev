package ru.mirea.yudaev.data.network;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.yudaev.domain.models.Movie;

/**
 * Заглушка для имитации сетевого API с данными о фильмах
 */
public class FakeMovieApi {
    private final List<Movie> movieStub = new ArrayList<>();

    public FakeMovieApi() {
        initializeMovies();
    }

    private void initializeMovies() {
        movieStub.add(new Movie(
                1, 
                "Побег из Шоушенка", 
                "История банкира, несправедливо осужденного за убийство жены и её любовника",
                "Драма",
                1994,
                9.3,
                "https://m.media-amazon.com/images/M/MV5BNDE3ODcxYzMtY2YzZC00NmNlLWJiNDMtZDViZWM2MzIxZDYwXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg"
        ));
        
        movieStub.add(new Movie(
                2, 
                "Крёстный отец", 
                "Хроника криминальной семьи Корлеоне в Нью-Йорке 1940-х годов",
                "Криминал, Драма",
                1972,
                9.2,
                "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg"
        ));
        
        movieStub.add(new Movie(
                3, 
                "Тёмный рыцарь", 
                "Когда угроза, известная как Джокер, обрушивается на жителей Готэма",
                "Боевик, Криминал",
                2008,
                9.0,
                "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_.jpg"
        ));
        
        movieStub.add(new Movie(
                4, 
                "Криминальное чтиво", 
                "Переплетение историй гангстеров и боксёра в Лос-Анджелесе",
                "Криминал, Драма",
                1994,
                8.9,
                "https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg"
        ));
        
        movieStub.add(new Movie(
                5, 
                "Список Шиндлера", 
                "История немецкого промышленника, спасшего евреев во время Холокоста",
                "Биография, Драма",
                1993,
                8.9,
                "https://m.media-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg"
        ));
        
        movieStub.add(new Movie(
                6, 
                "Начало", 
                "Вор, крадущий корпоративные секреты через сны людей",
                "Боевик, Фантастика",
                2010,
                8.8,
                "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg"
        ));
        
        movieStub.add(new Movie(
                7, 
                "Матрица", 
                "Хакер узнаёт шокирующую правду о реальности мира",
                "Фантастика, Боевик",
                1999,
                8.7,
                "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg"
        ));
        
        movieStub.add(new Movie(
                8, 
                "Интерстеллар", 
                "Команда исследователей путешествует сквозь червоточину в поисках нового дома",
                "Фантастика, Драма",
                2014,
                8.6,
                "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg"
        ));
        
        movieStub.add(new Movie(
                9, 
                "Зелёная миля", 
                "История надзирателя и заключённого с необычным даром",
                "Драма, Фантастика",
                1999,
                8.6,
                "https://m.media-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1_.jpg"
        ));
        
        movieStub.add(new Movie(
                10, 
                "Бойцовский клуб", 
                "Измученный бессонницей менеджер и мыловар создают подпольный бойцовский клуб",
                "Драма",
                1999,
                8.8,
                "https://m.media-amazon.com/images/M/MV5BNDIzNDU0YzEtYzE5Ni00ZjlkLTk5ZjgtNjM3NWE4YzA3Nzk3XkEyXkFqcGdeQXVyMjUzOTY1NTc@._V1_.jpg"
        ));
        
        movieStub.add(new Movie(
                11, 
                "Форрест Гамп", 
                "История простодушного парня с низким IQ, ставшего свидетелем важных событий",
                "Драма, Комедия",
                1994,
                8.8,
                "https://m.media-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg"
        ));
        
        movieStub.add(new Movie(
                12, 
                "Властелин колец: Возвращение короля", 
                "Финальная битва за Средиземье и судьба Кольца Всевластия",
                "Фэнтези, Приключения",
                2003,
                8.9,
                "https://m.media-amazon.com/images/M/MV5BNzA5ZDNlZWMtM2NhNS00NDJjLTk4NDItYTRmY2EwMWZlMTY3XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg"
        ));
    }

    /**
     * Получить все фильмы из заглушки
     */
    public List<Movie> fetchMovies() {
        return new ArrayList<>(movieStub);
    }

    /**
     * Поиск фильмов по жанру
     */
    public List<Movie> fetchMoviesByGenre(String genre) {
        List<Movie> filtered = new ArrayList<>();
        for (Movie movie : movieStub) {
            if (movie.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                filtered.add(movie);
            }
        }
        return filtered;
    }
}
