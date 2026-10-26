package controller;

import fill.Filler;
import fill.SeedFill;
import model.Point2D;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import rasterize.LineRasterizerTrivial;
import rasterize.PolygonRasterizer;
import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller2D {
    private final Panel panel;
    private int x, y;

    // Rasterizers
    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;

    // Fillers
    private SeedFill seedFiller;

    private final Polygon polygon;
    private Point2D lineStart, lineEnd;

    public Controller2D(Panel panel) {
        this.panel = panel;

        // Rasterozers
        lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
        //lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);

        // Fillers
        seedFiller = new SeedFill(panel.getRaster());

        polygon = new Polygon();

        initListeners();
    }

    private void initListeners() {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    int startX = panel.getWidth() / 2;
                    int startY = panel.getHeight() / 2;
                    int endX = e.getX();
                    int endY = e.getY();
                    lineStart = new Point2D(startX, startY);
                    lineEnd = new Point2D(endX, endY);

                    drawScene();
                }

                if(e.getButton() == MouseEvent.BUTTON2) {
                    seedFiller.setSeed(new Point2D(e.getX(), e.getY()));
                    drawScene();
                }

                if(e.getButton() == MouseEvent.BUTTON3) {
                    Point2D point = new Point2D(e.getX(), e.getY());
                    polygon.addPoint(point);
                    drawScene();
                }
            }
        });
    }

    private void drawScene() {
        panel.getRaster().clear();

        if(lineStart != null && lineEnd != null)
            lineRasterizer.rasterize(lineStart.getX(), lineStart.getY(), lineEnd.getX(), lineEnd.getY());

        polygonRasterizer.rasterize(polygon);

        seedFiller.fill();

        panel.repaint();
    }
}
