package dao;

import entity.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class MovieDao {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public void addMoviePackToDatabase() {
        List<Movie> movies = List.of(
                new Movie("Sagan om ringen", 165, "Peter jackson", 2001),
                new Movie("Pulp Fiction", 174, "Quentin Tarantino", 1994),
                new Movie("Forest Gump", 165, "Robert Zemeckis", 1994),
                new Movie("Matrix", 124, "Lana Wachowski", 1999),
                new Movie("The Dark Knight", 152, "Christopher Nolan", 2008));

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        for (Movie movie : movies) {
            em.persist(movie);
        }

        em.getTransaction().commit();

        em.close();
    }

    public List<Movie> getAllMovies() {
        EntityManager em = emf.createEntityManager();

        List<Movie> movies = em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
        em.close();

        return movies;
    }

    public List<Movie> findMoviesByTitle(String title) {
        EntityManager em = emf.createEntityManager();

        List<Movie> movies = em.createQuery("SELECT m FROM Movie m WHERE m.title=:title", Movie.class).setParameter("title", title).getResultList();

        em.close();

        return movies;
    }

    public Movie findMovieById(Long id) {
        EntityManager em = emf.createEntityManager();

        Movie movies = em.createQuery("SELECT m FROM Movie m WHERE m.id=:id", Movie.class).setParameter("id", id).getSingleResult();

        em.close();

        return movies;
    }

    public List<Movie> findMoviesByYear(int releaseYear) {
        EntityManager em = emf.createEntityManager();

        List<Movie> movies = em.createQuery("SELECT m FROM Movie m WHERE m.releaseYear=:releaseYear", Movie.class).setParameter("releaseYear", releaseYear).getResultList();

        em.close();

        return movies;
    }

    public List<Movie> findMoviesByDirector(String director) {
        EntityManager em = emf.createEntityManager();

        List<Movie> movies = em.createQuery("SELECT m FROM Movie m WHERE m.director=:director", Movie.class).setParameter("director", director).getResultList();

        em.close();

        return movies;
    }

    public List<Movie> findMoviesByLength(int length) {
        EntityManager em = emf.createEntityManager();

        List<Movie> movies = em.createQuery("SELECT m FROM Movie m WHERE m.length=:length", Movie.class).setParameter("length", length).getResultList();

        em.close();

        return movies;
    }


}
