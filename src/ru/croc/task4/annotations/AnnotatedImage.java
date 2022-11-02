package ru.croc.task4.annotations;

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
