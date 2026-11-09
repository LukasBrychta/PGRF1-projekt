package controller;

import model.Line;
import fill.Filler;
import fill.SeedFill;
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
import java.util.ArrayList;

public class Controller2D {
    private final Panel panel;
    private Point2D mousePosition;

    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;

    private ArrayList<Polygon> polygons;
    private ArrayList<Line> lines;
    private Polygon polygon;
    private Line line;
    private Line staticLine = new Line(new Point2D(10, 10), new Point2D(400, 400));
    // Fillers
    private SeedFill seedFiller;



    public Controller2D(Panel panel) {
        this.panel = panel;
        lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);

        // Fillers
        seedFiller = new SeedFill(panel.getRaster());

        polygons = new ArrayList<>();
        lines = new ArrayList<>();
        polygon = new Polygon();
        line = new Line();
        lines.add(this.staticLine);
        initListeners();
    }

    private enum Mode {
        LINE,
        POLYGON
    }

    Mode mode = Mode.LINE;

    private void initListeners() {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                /*if (mode == Mode.LINE && e.getButton() == MouseEvent.BUTTON1) {
                    if (line.getStart() != null) { //Ukončení a přidání úsečky do listu
                        line.setEnd((new Point2D(e.getX(), e.getY())));
                        lines.add(line);
                        line = new Line();
                    } else { //Start nové úsečky
                        line.setStart(new Point2D(e.getX(), e.getY()));
                    }
                }
                if (mode == Mode.POLYGON && e.getButton() == MouseEvent.BUTTON1) {
                    Point2D point = new Point2D(e.getX(), e.getY()); //Vytváření polygonu, přidání bodu do polygonu
                    polygon.addPoint(point);
                }
                if(e.getButton() == MouseEvent.BUTTON3) {
                    seedFiller.setSeed(new Point2D(e.getX(), e.getY()));
                    drawScene();
                }*/

                //1. spocitat tecny vektor -> vytvorit tridu vektor
                int tx = staticLine.getEnd().getX()-staticLine.getStart().getX();
                int ty = staticLine.getEnd().getY()-staticLine.getStart().getY();
                //2. spocitat normalu
                double nx = ty;
                double ny = -tx;
                double nL = Math.sqrt(nx*nx+ny*ny);
                nx = nx/nL;
                ny = ny/nL;
                //3. Spocitat vektor k bodu P (kam jsem kliknul)
                double vx = e.getX() - staticLine.getStart().getX();
                double vy = e.getY() - staticLine.getStart().getY();
                double vL = Math.sqrt(vx*vx+vy*vy);
                vx = vx/vL;
                vy = vx/vL;
                //4. Skalarni soucin
                double dot = nx*vx+ny*vy;
                System.out.println("Dot:" + dot);
                //5. Porovnat znamenka vysledku skalar. soucinu, rozhodnout, jestli je vlevo nebo vpravo
                if(dot < 0)
                    System.out.println("Pravo");
                else
                    System.out.println("Levo");
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (mode == Mode.LINE) {
                    line.setEnd(new Point2D(e.getX(), e.getY()));
                }
                if (mode == Mode.POLYGON) {
                    mousePosition = new Point2D(e.getX(), e.getY()); //Mousetracking pro pružné vykreslování
                }
                drawScene();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'c') {
                    System.out.print("c pressed. ");
                    panel.getRaster().clear();
                    polygons.clear();
                    lines.clear();
                    panel.repaint();
                }
                if (e.getKeyChar() == 'p') {
                    System.out.print("p pressed. ");
                    mode = Mode.POLYGON;
                }
                if (e.getKeyChar() == 'l') {
                    System.out.print("l pressed. ");
                    if(mode == Mode.POLYGON){
                        polygons.add(polygon);
                        polygon = new Polygon();
                        mousePosition = null;
                        drawScene();
                    }
                    mode = Mode.LINE;
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER && mode == Mode.POLYGON) { //Ukládání polygou a vytvoření nového.
                    polygons.add(polygon);
                    polygon = new Polygon();
                    mousePosition = null;
                    drawScene();
                }
            }
        });
    }

    private void drawScene() {
        panel.getRaster().clear();

        if (line.getStart() != null && line.getEnd() != null) { //Pružné vykreslení rozpracované úsečky
            lineRasterizer.rasterize(line);
        }

        for (Line l : lines) {
            lineRasterizer.rasterize(l); //Vykreslení hotových úseček
        }

        for (Polygon p : polygons) {
            polygonRasterizer.rasterize(p); //Vykreslení hotových polygonů
        }

        if (polygon.getSize() > 0 && mousePosition != null) {
            polygonRasterizer.rasterize(polygon); //Vykreslení rozpracovaného polygonu a pružné úsečky z okrajových bodů k pozici myši.
            lineRasterizer.rasterize(new Line(polygon.getPoint(polygon.getSize() - 1), mousePosition));
            lineRasterizer.rasterize(new Line(mousePosition, polygon.getPoint(0)));
        }
        seedFiller.fill();

        panel.repaint();
    }
}
