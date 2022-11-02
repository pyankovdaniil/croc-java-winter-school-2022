package ru.croc.task5.annotations;

import ru.croc.task5.figures.Figure;

public class Annotation {
    private final String description;
    private final Figure figure;

    public Annotation(String description, Figure figure) {
        this.description = description;
        this.figure = figure;
    }

    public String getDescription() {
        return description;
    }

    public Figure getFigure() {
        return figure;
    }

    @Override
    public String toString() {
        return figure.toString() + ": " + description;
    }
}
