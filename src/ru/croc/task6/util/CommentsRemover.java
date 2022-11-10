package ru.croc.task6.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommentsRemover {
    public void removeComments(String inputFileName, String outputFileName) throws IOException {
        try (FileReader fileReader = new FileReader(inputFileName);
             FileWriter fileWriter = new FileWriter(outputFileName)) {
            Scanner scanner = new Scanner(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append('\n');
            }

            String codeWithoutComments = removeComments0(stringBuilder.toString());
            fileWriter.write(codeWithoutComments);
        }
    }

    private String removeComments0(String s) {
        CommentPosition[] commentPositions = findCommentPositions(s);

        if (commentPositions.length == 0) {
            return s;
        }

        int currentInd = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i == commentPositions[currentInd].getBegin() && commentPositions[currentInd].isSpecialComment()) {
                stringBuilder.append(' ');
            }
            if (!(i >= commentPositions[currentInd].getBegin()
                    && i <= commentPositions[currentInd].getEnd())) {
                stringBuilder.append(s.charAt(i));
            }
            if (i > commentPositions[currentInd].getEnd() &&
                    (currentInd != (commentPositions.length - 1))) {
                currentInd++;
            }

        }
        return stringBuilder.toString();
    }

    private CommentPosition[] findCommentPositions(String s) {
        List<CommentPosition> positions = new ArrayList<>();

        boolean isInString = false;
        boolean isInOneLineComment = false;
        boolean isInMultLineComment = false;

        int currentBegin = 0;
        int currentEnd = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '"' && !isInOneLineComment && !isInMultLineComment) {
                isInString = !isInString;
            } else if (s.charAt(i) == '/' && !isInString) {
                if ((i != s.length() - 1) && s.charAt(i + 1) == '/') {
                    isInOneLineComment = true;
                    currentBegin = i;
                } else if (i != s.length() - 1 && s.charAt(i + 1) == '*') {
                    isInMultLineComment = true;
                    currentBegin = i;
                }
            }

            if (isInOneLineComment && s.charAt(i) == '\n') {
                currentEnd = i - 1;
                positions.add(new CommentPosition(currentBegin, currentEnd, false));
                isInOneLineComment = false;
            } else if (isInMultLineComment && s.charAt(i) == '*') {
                if ((i != s.length() - 1) && s.charAt(i + 1) == '/') {
                    currentEnd = i + 1;
                    boolean isSpecialComment = false;
                    if (currentBegin != 0 && (Character.isLetter(s.charAt(currentBegin - 1))
                            || s.charAt(currentBegin - 1) == 62) && currentEnd != s.length() - 1 &&
                            Character.isLetter(s.charAt(currentEnd + 1))) {
                        isSpecialComment = true;
                    }
                    positions.add(new CommentPosition(currentBegin, currentEnd, isSpecialComment));
                    isInMultLineComment = false;
                }
            }
        }

        return positions.toArray(new CommentPosition[0]);
    }
}
