package ru.croc.task5.figures;

import ru.croc.task5.util.Movable;

public abstract class Figure implements Movable {
    public abstract boolean contains(Point point);
}
