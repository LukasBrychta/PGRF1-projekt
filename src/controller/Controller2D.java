package controller;

import model.Line;
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
    private Point2D mousePosition;

    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;

    private final Polygon polygon;
    private final Line line;

    public Controller2D(Panel panel) {
        this.panel = panel;
        lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);

        polygon = new Polygon();
        line = new Line();

        initListeners();
    }

    private enum Mode {
        LINE,
        POLYGON
    }

    Mode mode = Mode.LINE;

    private void initListeners() {
        //TODO: Vykreslovani pri tazeni mysi. Mody na zadavani usecek a polygonu.
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (mode == Mode.LINE) {
                    line.setStart(new Point2D(e.getX(), e.getY()));
                }
                if (mode == Mode.POLYGON) {
                    Point2D point = new Point2D(e.getX(), e.getY());
                    polygon.addPoint(point);
                }
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (mode == Mode.LINE) {
                    line.setEnd(new Point2D(e.getX(), e.getY()));
                }
                if (mode == Mode.POLYGON) {
                    mousePosition = new Point2D(e.getX(), e.getY());

                }
                drawScene();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'c') {
                    System.out.print("c pressed");
                    panel.getRaster().clear();
                    panel.repaint();
                }
                if (e.getKeyChar() == 'p') {
                    System.out.println("p pressed");
                    mode = Mode.POLYGON;
                }
                if (e.getKeyChar() == 'l') {
                    System.out.println("l pressed");
                    mode = Mode.LINE;
                }
            }
        });
    }

    private void drawScene() {
        panel.getRaster().clear();

        if (line.getStart() != null && line.getEnd() != null) {
            lineRasterizer.rasterize(line);
        }

        polygonRasterizer.rasterize(polygon);

        if (polygon.getSize() > 0 && mousePosition != null) {
            lineRasterizer.rasterize(new Line(polygon.getPoint(polygon.getSize() - 1), mousePosition));
            lineRasterizer.rasterize(new Line(mousePosition, polygon.getPoint(0)));
        }

        panel.repaint();
    }
}
