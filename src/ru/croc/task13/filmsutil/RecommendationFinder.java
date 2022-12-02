package ru.croc.task13.filmsutil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecommendationFinder {
    private RecommendationFinder() {
    }

    public static void getRecommendation(List<Film> films, List<List<Integer>> watchHistory,
                                         List<Integer> watchedFilms) {
        List<Integer> matchedFilms = new ArrayList<>();
        int numberOfWatchedFilms = watchedFilms.size();
        for (List<Integer> personWatchList : watchHistory) {
            int numberOfMatchedFilms = 0;
            for (int filmId : personWatchList) {
                if (watchedFilms.contains(filmId)) {
                    numberOfMatchedFilms++;
                }
            }
            if (2 * numberOfMatchedFilms >= numberOfWatchedFilms) {
                matchedFilms.addAll(personWatchList);
            }
        }
        matchedFilms.removeAll(watchedFilms);

        int maxCount = 0;
        String recommendedFilmName = "No recommendations for you!";
        for (Film film : films) {
            int currentCount = Collections.frequency(matchedFilms, film.getFilmId());
            if (currentCount > maxCount) {
                maxCount = currentCount;
                recommendedFilmName = film.getFilmName();
            }
        }

        System.out.println("We got film for you: " + recommendedFilmName);
    }
}
