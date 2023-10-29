package models;

public class Movie extends MediaItem {

    public Movie(int id, String title, double price, int duration, int releaseYear) {
        super(id, title, price, duration, releaseYear);
    }

    public Movie(String title, double price, int duration, int releaseYear) {
        super(title, price, duration, releaseYear);
    }

} 