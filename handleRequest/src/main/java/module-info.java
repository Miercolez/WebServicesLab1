import plugin.*;

module handleRequest {
    requires spi;
    requires utils;
    requires database;
    requires com.google.gson;
    provides spi.Spi with GetAllMovies, AddMovie, FindMovieById, FindMovieByTitle, FindMovieByDirector, FindMovieByLength, FindMovieByReleaseYear, GetFile;

}