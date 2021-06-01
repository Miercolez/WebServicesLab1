import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {

        MovieDao movieDao = new MovieDao();

        List<Movie> movies = movieDao.findMoviesByTitle("Sagan om ringen");

        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

}
