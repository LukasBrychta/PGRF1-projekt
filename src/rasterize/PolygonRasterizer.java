package rasterize;

import model.Line;
import model.Point2D;
import model.Polygon;

public class PolygonRasterizer {
    private LineRasterizer lineRasterizer;

    public PolygonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void setLineRasterizer(LineRasterizer lineRasterizer) {this.lineRasterizer = lineRasterizer;}

    public void rasterize(Polygon polygon) {
        int size = polygon.getSize();
        if (size < 2) {
            return;
        }

        for (int i = 0; i < size; i++) {
            Point2D pointA = polygon.getPoint(i);
            Point2D pointB = polygon.getPoint((i + 1) % size);
            lineRasterizer.rasterize(new Line(pointA, pointB));
        }
    }
}
