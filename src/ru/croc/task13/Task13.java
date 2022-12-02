package ru.croc.task13;

import ru.croc.task13.filmdatareaders.FilmDataFileReader;
import ru.croc.task13.filmdatareaders.FilmDataReader;
import ru.croc.task13.filmsutil.RecommendationFinder;

import java.io.IOException;
import java.util.*;

public class Task13 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String watchedFilmsString = scanner.nextLine();

        List<Integer> watchedFilms = new ArrayList<>();
        for (String filmId : watchedFilmsString.split(",")) {
            watchedFilms.add(Integer.parseInt(filmId));
        }

        FilmDataReader filmDataReader = new FilmDataFileReader("src\\ru\\croc\\task13\\films.txt",
                "src\\ru\\croc\\task13\\watchHistory.txt");

        RecommendationFinder.getRecommendation(filmDataReader.readFilms(),
                filmDataReader.readWatchHistory(), watchedFilms);
    }
}
