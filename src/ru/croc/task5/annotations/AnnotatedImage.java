package ru.croc.task5.annotations;

import ru.croc.task5.figures.Point;

import java.util.Optional;

public class AnnotatedImage {
    private final String imagePath;
    private final Annotation[] annotations;

    public AnnotatedImage(String imagePath, Annotation... annotations) {
        this.imagePath = imagePath;
        this.annotations = annotations;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public Optional<Annotation> findAnnotationByPoint(int x, int y) {
        Point point = new Point(x, y);
        for (Annotation annotation : annotations) {
            if (annotation.getFigure().contains(point)) {
                return Optional.of(annotation);
            }
        }
        return Optional.empty();
    }

    public Optional<Annotation> findAnnotationByLabel(String label) {
        for (Annotation annotation : annotations) {
            if (annotation.getDescription().contains(label)) {
                return Optional.of(annotation);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Annotations for image: \"").append(imagePath).append("\"\n");
        for (Annotation annotation : annotations) {
            builder.append(annotation.toString()).append('\n');
        }
        return builder.toString();
    }
}
