package ru.croc.task14.commentsutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public interface BlackListFilter<E> {
    default Collection<E> filterComments(Predicate<E> isCorrectComment, Iterable<E> comments) {
        Collection<E> correctComments = new ArrayList<>();

        comments.forEach(comment -> {
            if (isCorrectComment.test(comment)) {
                correctComments.add(comment);
            }
        });

        return correctComments;
    }
}
