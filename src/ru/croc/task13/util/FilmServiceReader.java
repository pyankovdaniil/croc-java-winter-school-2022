package ru.croc.task13.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilmServiceReader {
    private FilmServiceReader() {
    }

    public static List<Film> readFilmsFromFile(String filmsFileName) throws IOException {
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

    public static List<List<Integer>> readWatchHistoryFromFile(String fileName) throws IOException {
        List<List<Integer>> history = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new FileReader(new File(fileName).getAbsolutePath())
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
