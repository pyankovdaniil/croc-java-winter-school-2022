package ru.croc.task5;

import ru.croc.task5.annotations.AnnotatedImage;
import ru.croc.task5.annotations.Annotation;
import ru.croc.task5.figures.Circle;
import ru.croc.task5.figures.Figure;
import ru.croc.task5.figures.Point;
import ru.croc.task5.figures.Rectangle;

import java.util.Optional;

public class Test {
    public static void main(String[] args) {
        Figure circle = new Circle(new Point(15, 15), 10);
        Figure rectangle = new Rectangle(new Point(25, 25), new Point(150, 150));

        Annotation annotation1 = new Annotation("Car wheel", circle);
        Annotation annotation2 = new Annotation("Billboard", rectangle);

        Annotation[] annotations = {annotation1, annotation2};

        AnnotatedImage annotatedImage = new AnnotatedImage("/img/testImage2.jpg", annotations);
        System.out.println(annotatedImage);

        Optional<Annotation> annotation = annotatedImage.findAnnotationByLabel("wheel");
        if (annotation.isPresent()) {
            Figure someFigure = annotation.get().getFigure();
            someFigure.move(50, 0);
            // Out: Circle (65.0, 15.0), 10.0
            System.out.println(someFigure);
            // Out: Circle (65.0, 15.0), 10.0: Car wheel
            System.out.println(annotation.get() + "\n");
        }

        annotation = annotatedImage.findAnnotationByPoint(100, 100);
        if (annotation.isPresent()) {
            Figure someFigure = annotation.get().getFigure();
            someFigure.move(50, 130);
            // Out: Rectangle (75.0, 155.0), (200.0, 280.0)
            System.out.println(someFigure);
            // Out: Rectangle (75.0, 155.0), (200.0, 280.0): Billboard
            System.out.println(annotation.get());
        }
    }
}
