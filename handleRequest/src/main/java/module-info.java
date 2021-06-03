module handleRequest {
    requires spi;
    requires utils;
    provides spi.Spi with plugin.HandelUrl;
}