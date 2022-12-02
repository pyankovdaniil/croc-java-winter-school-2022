package ru.croc.task13.filmdatareaders;

import ru.croc.task13.filmsutil.Film;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public record FilmDataFileReader(String filmsFileName, String watchHistoryFileName) implements FilmDataReader {
    @Override
    public List<Film> readFilms() throws IOException {
        List<Film> films = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new FileReader(new File(filmsFileName).getAbsolutePath())
        );

        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                films.add(Film.parseString(line));
            }
        }

        return films;
    }

    @Override
    public List<List<Integer>> readWatchHistory() throws IOException {
        List<List<Integer>> history = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new FileReader(new File(watchHistoryFileName).getAbsolutePath())
        );

        String line;
        int currentIndex = 0;
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                history.add(new ArrayList<>());
                for (String id : line.split(",")) {
                    history.get(currentIndex).add(Integer.parseInt(id));
                }
            }
            currentIndex++;
        }

        return history;
    }
}
