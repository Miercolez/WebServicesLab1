package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int length;
    private String director;
    private int releaseYear;

    public Movie() {
    }

    public Movie(String title, int length, String producer, int releaseYear) {
        this.title = title;
        this.length = length;
        director = producer;
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "entity.Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", length=" + length +
                ", Producer='" + director + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
