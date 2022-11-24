package ru.croc.task13;

import ru.croc.task13.util.RecommendationFinder;

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

        RecommendationFinder.getRecommendation("src\\ru\\croc\\task13\\films.txt",
                "src\\ru\\croc\\task13\\watchHistory.txt", watchedFilms);
    }
}
