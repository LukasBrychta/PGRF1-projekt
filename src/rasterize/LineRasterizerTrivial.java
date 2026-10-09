package rasterize;

import raster.Raster;

public class LineRasterizerTrivial extends LineRasterizer {
    public LineRasterizerTrivial(Raster raster) {super(raster);}

    @Override
    public void rasterize(int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double k = dy / dx;
        double q = y1 - k * x1;
        //TODO: Doresit svisla cara+1px bod. Smernice: y = kx + q
        if (Math.abs(k) > 1){
            if(y1 > y2){
                int xx, yy;
                xx = x2;
                yy = y2;
                x2 = x1;
                y2 = y1;
                x1 = xx;
                y1 = yy;

            }
            for(int i = y1; i <= y2;i++){
                double x = (i - q)/k;
                raster.setPixel((int)Math.round(x), i, 0xFFFFFFFF);
            }
        } else {
            if(x1 > x2){
                int xx, yy;
                xx = x2;
                yy = y2;
                x2 = x1;
                y2 = y1;
                x1 = xx;
                y1 = yy;
            }
            for(int i = x1; i <= x2; i++){
                double y = k * i + q;
                raster.setPixel(i, (int)Math.round(y), 0xFFFFFFFF);
            }
        }
    }


}
