package functions;

import dao.MovieDao;
import entity.Movie;

import java.util.List;

public class Functions {
    static MovieDao movieDao = new MovieDao();

    public static void addMoviePackToDatabase() {
        movieDao.addMoviePackToDatabase();
    }

    public static void addMovie(Movie movie){
        movieDao.addMovie(movie);
    }

    public static List<Movie> getAllMovies() {
        return movieDao.getAllMovies();
    }

    public static Movie findMovieById(Long id) {
        return movieDao.findMovieById(id);
    }

    public static List<Movie> findMoviesByTitle(String title) {
        return movieDao.findMoviesByTitle(title);
    }

    public static List<Movie> findMoviesByLength(int length) {
        return movieDao.findMoviesByLength(length);
    }

    public static List<Movie> findMoviesByDirector(String director) {
        return movieDao.findMoviesByDirector(director);
    }

    public static List<Movie> findMoviesByYear(int year) {
        return movieDao.findMoviesByYear(year);
    }
}
