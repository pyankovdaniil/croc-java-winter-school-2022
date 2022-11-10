package ru.croc.task6.util;

public class CommentPosition {
    private final int begin;
    private final int end;
    private final boolean isSpecialComment;

    public CommentPosition(int begin, int end, boolean isSpecialComment) {
        this.begin = begin;
        this.end = end;
        this.isSpecialComment = isSpecialComment;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public boolean isSpecialComment() {
        return isSpecialComment;
    }
}
