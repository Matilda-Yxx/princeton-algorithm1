import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.LinkedList;
import java.util.Queue;

public class PointSET {

    private final SET<Point2D> set;

    public PointSET() { // construct an empty set of points 
        set = new SET();
    }
    public boolean isEmpty() { return set.isEmpty(); }

    public int size() { return set.size(); }

    public void insert(Point2D p) {
        checkNull(p);
        if ( ! set.contains(p) ) set.add(p);
    }

    public boolean contains(Point2D p) {
        checkNull(p);
        return set.contains(p); 
    }

    public void draw() {
        for ( Point2D p : set ) p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary) 
        checkNull(rect);
        Queue<Point2D> q = new LinkedList<Point2D>();
        for ( Point2D p : set ) {
            if ( rect.contains(p) ) q.add(p);
        }
        return q;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        checkNull(p);
        if (this.size() == 0) return null;
        else {
            Point2D nearestPoint = set.max();
            double minDist = p.distanceTo(set.max());
            for ( Point2D candidate : set ) {
                double curDist = p.distanceTo(candidate);
                if (curDist < minDist) {
                    minDist = curDist;
                    nearestPoint = candidate;
                }
            }
            return nearestPoint;
        }
    }

    private void checkNull (Object o) {
        if (o == null) throw new IllegalArgumentException("Input argument should not be null.");
    }
 
    public static void main(String[] args) {}                 // unit testing of the methods (optional) 
 }