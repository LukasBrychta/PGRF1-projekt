package fill;

import model.Edge;
import model.Point2D;
import model.Polygon;

import java.util.ArrayList;
import java.util.List;

public class ScanLineFill implements Filler {
    private int fillColor;
    private Polygon polygon;

    public ScanLineFill() {
        setFillColor(0x00ff00);
    }

    @Override
    public void fill() {
        if (polygon.getSize() < 3) return;

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < polygon.getSize(); i++) {
            Point2D pointA = polygon.getPoint(i);
            Point2D pointB = polygon.getPoint((i + 1) % polygon.getSize());

            Edge edge = new Edge(pointA, pointB);
            if (!edge.isHorizontal()) {
                edge.orientate();
                edges.add(edge);
            }
        }

        // TODO: Musíme najít min a max Y
        int yMin = polygon.getPoint(0).getY();
        int yMax = yMin;


        for (int y = yMin; y <= yMax; y++) {
            List<Integer> intersection = new ArrayList<>();

            // TODO: pro danou scan line se ptáme, jestli existuje
            // průsečík s hranou. S jakou???? Se všema!!!
            for(Edge edge : edges) {
                if(!edge.isIntersection(y)) continue;

                // TODO: spočítáme průsečík
                // TODO: průsečík uložíme - do seznamu průsečů
            }

            // Máme průsečíky
            // TODO: Spojíme lichý se sudým
        }

        // TODO: Obtáhneme polygon

    }

    @Override
    public void setFillColor(int color) {
        this.fillColor = color;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
}
