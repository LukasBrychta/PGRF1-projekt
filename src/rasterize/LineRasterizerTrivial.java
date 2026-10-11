package rasterize;

import model.Line;
import raster.Raster;

public class LineRasterizerTrivial extends LineRasterizer {
    public LineRasterizerTrivial(Raster raster) {
        super(raster);
    }

    @Override
    public void rasterize(Line line) { //Triviální algoritmus pro vykreslení úsečky
        int x1 = line.getStart().getX();
        int x2 = line.getEnd().getX();
        int y1 = line.getStart().getY();
        int y2 = line.getEnd().getY();
        double dx = x2 - x1;
        double dy = y2 - y1;
        double k = dy / dx;
        double q = y1 - k * x1;
        if (x1 == x2 && y1 == y2) { //Reseni pro 1px bod
            raster.setPixel(x1, y1, 0xFFFFFFFF);
        } else if (Math.abs(k) > 1) {
            if (y1 > y2) {
                int xx, yy;
                xx = x2;
                yy = y2;
                x2 = x1;
                y2 = y1;
                x1 = xx;
                y1 = yy;
            }
            if (x1 == x2) { //Reseni pokud je usecka vertikalni
                for (int i = y1; i <= y2; i++) {
                    raster.setPixel(x1, i, 0xFFFFFFFF);
                }
            } else {
                for (int i = y1; i <= y2; i++) {
                    double x = (i - q) / k;
                    raster.setPixel((int) Math.round(x), i, 0xFFFFFFFF);
                }
            }
        } else {
            if (x1 > x2) {
                int xx, yy;
                xx = x2;
                yy = y2;
                x2 = x1;
                y2 = y1;
                x1 = xx;
                y1 = yy;
            }
            for (int i = x1; i <= x2; i++) {
                double y = k * i + q;
                raster.setPixel(i, (int) Math.round(y), 0xFFFFFFFF);
            }
        }
    }

}
