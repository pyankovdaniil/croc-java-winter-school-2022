package ru.croc.task12.commentsutil;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class BlackListFilterImplementation implements BlackListFilter {
    @Override
    public void filterComments(List<String> comments, Set<String> blackList) {
        int currentPosition = 0;
        for (String comment : comments) {
            String lowerCaseComment = comment.toLowerCase();
            for (String blackWord : blackList) {
                if (lowerCaseComment.contains(blackWord)) {
                    int blackWordBeginIndex = lowerCaseComment.indexOf(blackWord);

                    boolean checkPreviousSymbol = blackWordBeginIndex == 0
                            || !Character.isLetter(lowerCaseComment.charAt(blackWordBeginIndex - 1));

                    boolean checkNextSymbol = blackWordBeginIndex + blackWord.length() >= lowerCaseComment.length() - 1
                            || !Character.isLetter(lowerCaseComment.charAt(blackWordBeginIndex + blackWord.length()));

                    if (checkPreviousSymbol && checkNextSymbol) {
                        lowerCaseComment = lowerCaseComment.replace(blackWord,
                                String.join("", Collections.nCopies(blackWord.length(), "*")));
                    }
                }
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0 ; i < lowerCaseComment.length(); i++) {
                if (comment.charAt(i) == Character.toUpperCase(lowerCaseComment.charAt(i))) {
                    stringBuilder.append(Character.toUpperCase(lowerCaseComment.charAt(i)));
                } else {
                    stringBuilder.append(lowerCaseComment.charAt(i));
                }
            }

            comments.set(currentPosition++, stringBuilder.toString());
        }
    }
}
