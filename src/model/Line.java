package model;

public class Line {
    private Point2D start;
    private Point2D end;

    public Line() {
    }

    public Line(Point2D start, Point2D end) {
        this.start = start;
        this.end = end;
    }

    public void setStart(Point2D start) {
        this.start = start;
    }

    public void setEnd(Point2D end) {
        this.end = end;
    }

    public Point2D getStart() {
        return start;
    }

    public Point2D getEnd() {
        return end;
    }
}
