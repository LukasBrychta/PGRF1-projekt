package rasterize;

import model.Line;
import raster.Raster;

public abstract class LineRasterizer {
    protected Raster raster;

    public LineRasterizer(Raster raster) {this.raster = raster;}

    public void rasterize(Line line) {
        System.out.println("rasterize predek");
    }
}
