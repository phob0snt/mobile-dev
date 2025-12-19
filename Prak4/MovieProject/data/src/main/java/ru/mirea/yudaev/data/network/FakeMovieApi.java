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
                9.3
        ));
        
        movieStub.add(new Movie(
                2, 
                "Крёстный отец", 
                "Хроника криминальной семьи Корлеоне в Нью-Йорке 1940-х годов",
                "Криминал, Драма",
                1972,
                9.2
        ));
        
        movieStub.add(new Movie(
                3, 
                "Тёмный рыцарь", 
                "Когда угроза, известная как Джокер, обрушивается на жителей Готэма",
                "Боевик, Криминал",
                2008,
                9.0
        ));
        
        movieStub.add(new Movie(
                4, 
                "Криминальное чтиво", 
                "Переплетение историй гангстеров и боксёра в Лос-Анджелесе",
                "Криминал, Драма",
                1994,
                8.9
        ));
        
        movieStub.add(new Movie(
                5, 
                "Список Шиндлера", 
                "История немецкого промышленника, спасшего евреев во время Холокоста",
                "Биография, Драма",
                1993,
                8.9
        ));
        
        movieStub.add(new Movie(
                6, 
                "Начало", 
                "Вор, крадущий корпоративные секреты через сны людей",
                "Боевик, Фантастика",
                2010,
                8.8
        ));
        
        movieStub.add(new Movie(
                7, 
                "Матрица", 
                "Хакер узнаёт шокирующую правду о реальности мира",
                "Фантастика, Боевик",
                1999,
                8.7
        ));
        
        movieStub.add(new Movie(
                8, 
                "Интерстеллар", 
                "Команда исследователей путешествует сквозь червоточину в поисках нового дома",
                "Фантастика, Драма",
                2014,
                8.6
        ));
        
        movieStub.add(new Movie(
                9, 
                "Зелёная миля", 
                "История надзирателя и заключённого с необычным даром",
                "Драма, Фантастика",
                1999,
                8.6
        ));
        
        movieStub.add(new Movie(
                10, 
                "Бойцовский клуб", 
                "Измученный бессонницей менеджер и мыловар создают подпольный бойцовский клуб",
                "Драма",
                1999,
                8.8
        ));
        
        movieStub.add(new Movie(
                11, 
                "Форрест Гамп", 
                "История простодушного парня с низким IQ, ставшего свидетелем важных событий",
                "Драма, Комедия",
                1994,
                8.8
        ));
        
        movieStub.add(new Movie(
                12, 
                "Властелин колец: Возвращение короля", 
                "Финальная битва за Средиземье и судьба Кольца Всевластия",
                "Фэнтези, Приключения",
                2003,
                8.9
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
