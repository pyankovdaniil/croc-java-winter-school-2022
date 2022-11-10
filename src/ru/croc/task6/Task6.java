package ru.croc.task6;

import ru.croc.task6.util.CommentsRemover;

import java.io.IOException;

public class Task6 {
    public static void main(String[] args) {
        CommentsRemover commentsRemover = new CommentsRemover();
        try {
            commentsRemover.removeComments(
                    "D:\\JavaTrainings\\CROC\\java-winter-school-2022\\src\\ru\\croc\\task6\\input.txt",
                    "D:\\JavaTrainings\\CROC\\java-winter-school-2022\\src\\ru\\croc\\task6\\output.txt");
        } catch (IOException e) {
            System.out.println("Caught exception with message: " + e.getMessage());
        }
    }
}
