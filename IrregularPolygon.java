import java.awt.geom.*;  // for Point2D.Double
import java.util.ArrayList;  // for ArrayList
import gpdraw.*;  // for DrawingTool

public class IrregularPolygon {
    private ArrayList<Point2D.Double> myPolygon = new ArrayList<Point2D.Double>();

    // constructor
    public IrregularPolygon() {}

    // Add a point to the IrregularPolygon
    public void add(Point2D.Double aPoint) {
        myPolygon.add(aPoint);
    }

    // Calculate the perimeter of the polygyon
    public double perimeter() {
        double perimeter = 0.0;
        int n = myPolygon.size();

        for (int i = 0; i < n; i++) {
            Point2D.Double current = myPolygon.get(i);
            Point2D.Double next = myPolygon.get((i + 1) % n);  // Wrap around to the first point
            perimeter += current.distance(next);
        }
        return perimeter;
    }

    // Calculate the area using the Shoelace formula
    public double area() {
        double sum1 = 0.0;  // Sum of x_i * y_(i+1)
        double sum2 = 0.0;  // Sum of y_i * x_(i+1)
        int n = myPolygon.size();

        for (int i = 0; i < n; i++) {
            Point2D.Double current = myPolygon.get(i);
            Point2D.Double next = myPolygon.get((i + 1) % n);  // Wrap around to the first point
            sum1 += current.x * next.y;
            sum2 += current.y * next.x;
        }
        return Math.abs(0.5 * (sum1 - sum2));
    }

    // Draw the polygon using DrawingTool
    public void draw() {
        try {
            DrawingTool pen = new DrawingTool(new SketchPad(500, 500));
            if (myPolygon.size() > 0) {
                pen.up();
                Point2D.Double firstPoint = myPolygon.get(0);
                pen.move(firstPoint.x, firstPoint.y);
                pen.down();

                for (int i = 1; i < myPolygon.size(); i++) {
                    Point2D.Double nextPoint = myPolygon.get(i);
                    pen.move(nextPoint.x, nextPoint.y);
                }
                pen.move(firstPoint.x, firstPoint.y);  // Close the polygon
            }
        } catch (java.awt.HeadlessException e) {
            System.out.println("Exception: No graphics support available.");
        }
    }
}