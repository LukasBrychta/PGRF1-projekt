package rasterize;

import model.Line;
import model.Point2D;
import model.Polygon;

public class PolygonRasterizer {
    private LineRasterizer lineRasterizer;

    public PolygonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void rasterize(Polygon polygon) {
        int size = polygon.getSize();
        if (size == 2) { //2 body, netřeba iterovat a vykreslovat úsečku tam a zpátky.
            Point2D pointA = polygon.getPoint(0);
            Point2D pointB = polygon.getPoint(1);
            lineRasterizer.rasterize(new Line(pointA, pointB));
        } else {
            for (int i = 0; i < size; i++) {
                Point2D pointA = polygon.getPoint(i);
                Point2D pointB = polygon.getPoint((i + 1) % size); //Modulo vrací 0, tedy 1. bod v polygonu.
                lineRasterizer.rasterize(new Line(pointA, pointB));
            }
        }
    }
}
