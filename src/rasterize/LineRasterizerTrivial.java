package rasterize;

import raster.Raster;

public class LineRasterizerTrivial extends LineRasterizer {
    public LineRasterizerTrivial(Raster raster) {
        super(raster);
    }

    @Override
    public void rasterize(int x1, int y1, int x2, int y2) {
        // 1. spočítat k a q
        float k = (y2 - y1) / (float) (x2 - x1);
        float q = y1 - k * x1;

        // 2. napsat for cyklus od x1 po x2
        for(int x = x1; x <= x2; x++) {
            int y = Math.round(k * x + q);
            // 3. obarvit pixel
            raster.setPixel(x, y, 0xffffff);
        }
    }
}
