import plugin.*;

module handleRequest {
    requires spi;
    requires utils;
    requires database;
    requires com.google.gson;
    provides spi.Spi with GetAllMovies, AddMovie, FindMovies, FindMovieByLength, FindMovieByReleaseYear, GetFile, Default;
}