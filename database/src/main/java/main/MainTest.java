package main;

import java.util.List;

import com.google.gson.Gson;
import dao.MovieDao;
import entity.Movie;

public class MainTest {
    public static void main(String[] args) {

        MovieDao movieDao = new MovieDao();

        List<Movie> movies = movieDao.getAllMovies(); //movieDao.findMoviesByTitle("Sagan om ringen");

        for (Movie movie : movies) {
            System.out.println(movie);
        }

        System.out.println();
        Gson gson = new Gson();
        String json = gson.toJson(movies);
        System.out.println(json);
        // movieDao.addMoviePackToDatabase();
    }

}
