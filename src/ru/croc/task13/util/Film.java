package ru.croc.task13.util;

public class Film {
    private int filmId;
    private String filmName;

    public Film(int filmId, String filmName) {
        this.filmId = filmId;
        this.filmName = filmName;
    }

    public static Film parseString(String filmString) {
        int id = Integer.parseInt(filmString.substring(0, filmString.indexOf(',')));
        String name = filmString.substring(filmString.indexOf(',') + 1);
        return new Film(id, name);
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", filmName='" + filmName + '\'' +
                '}';
    }
}
