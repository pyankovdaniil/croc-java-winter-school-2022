package ru.croc.task4;

import ru.croc.task4.annotations.AnnotatedImage;
import ru.croc.task4.annotations.Annotation;
import ru.croc.task4.figures.Circle;
import ru.croc.task4.figures.Figure;
import ru.croc.task4.figures.Point;
import ru.croc.task4.figures.Rectangle;

public class Task4 {
    public static void main(String[] args) {
        Figure circle1 = new Circle(new Point(10, 1000), 100);
        Figure circle2 = new Circle(new Point(15, 15), 10);

        Figure rectangle1 = new Rectangle(new Point(10, 10), new Point(200, 200));
        Figure rectangle2 = new Rectangle(new Point(25, 25), new Point(100, 100));

        Annotation annotation1 = new Annotation("Sun", circle1);
        Annotation annotation2 = new Annotation("Hospital", rectangle1);
        Annotation annotation3 = new Annotation("Pool", circle2);
        Annotation annotation4 = new Annotation("Jack's house", rectangle2);

        Annotation[] annotations = {annotation1, annotation2, annotation3, annotation4};

        AnnotatedImage annotatedImage = new AnnotatedImage("/img/testImage1.jpg", annotations);
        System.out.println(annotatedImage);
    }
}
