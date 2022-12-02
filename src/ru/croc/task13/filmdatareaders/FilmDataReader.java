package ru.croc.task13.filmdatareaders;

import ru.croc.task13.filmsutil.Film;

import java.io.IOException;
import java.util.List;

public interface FilmDataReader {
    List<Film> readFilms() throws IOException;
    List<List<Integer>> readWatchHistory() throws IOException;
}
