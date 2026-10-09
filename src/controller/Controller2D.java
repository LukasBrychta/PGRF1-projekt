package controller;

import model.Point2D;
import model.Polygon;
import rasterize.LineRasterizer;
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

    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;

    private final Polygon polygon;
    private Point2D lineStart, lineEnd;

    public Controller2D(Panel panel) {
        this.panel = panel;
        lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);

        polygon = new Polygon();

        initListeners();
    }

    private void initListeners() {
        //TODO: Vykreslovani pri tazeni mysi. Mody na zadavani usecek a polygonu. Moznost kliknout na existujici bod a presunout.
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int startX = panel.getWidth() / 2;
                int startY = panel.getHeight() / 2;
                int endX = e.getX();
                int endY = e.getY();
                //TODO: Implementovat tridu Line, moznost vybrat zacatek a konec
                lineStart = new Point2D(startX, startY);
                lineEnd = new Point2D(endX, endY);

                drawScene();

                if (e.getButton() == MouseEvent.BUTTON3) {
                    Point2D point = new Point2D(e.getX(), e.getY());
                    polygon.addPoint(point);

                    drawScene();
                }
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'c') {
                    System.out.print("c pressed");
                    drawScene();
                }else if(e.getKeyChar() == 'p') {
                    System.out.println("p pressed");

                }
            }
        });
    }

    private void drawScene() {
        panel.getRaster().clear();

        lineRasterizer.rasterize(lineStart.getX(), lineStart.getY(), lineEnd.getX(), lineEnd.getY());
        polygonRasterizer.rasterize(polygon);

        panel.repaint();
    }
}
