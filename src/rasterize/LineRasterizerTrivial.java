package rasterize;

import raster.Raster;

public class LineRasterizerTrivial extends LineRasterizer {
    public LineRasterizerTrivial(Raster raster) {super(raster);}

    public void rasterize(int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double k = dy / dx;
        double q = y1 - k * x1;
        //TODO: Doresit kvadranty a menit x a y dle naklonu

        for(int i = x1; i <= x2; i++){
            double y = k * i + q;
            raster.setPixel(i, (int)Math.round(y), 0xFFFFFFFF);
        }
    }
}
