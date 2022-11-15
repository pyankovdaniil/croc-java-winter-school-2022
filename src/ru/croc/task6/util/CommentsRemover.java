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
        return s.replaceAll("//.+|/\\*.+\\*/|/\\*[^/]+\\*/", "");
    }
}
