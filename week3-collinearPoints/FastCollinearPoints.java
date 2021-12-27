import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {

    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        checkNullPoints(points);
        checkDuplicatePoints(points);
        
        // dynamically hold the instances
        List<LineSegment> allSegments = new ArrayList<LineSegment>();
        Arrays.sort(points);                                            // to faciliate subsequent operations
        int N = points.length;
        for (int a = 0; a < N; a++) {                                   // have to run from 0 to N-1 since there might be more than 4 points being collinear
            Point curPoint = points[a];
            Point[] orderedBySlope = points.clone();
            Arrays.sort(orderedBySlope, curPoint.slopeOrder());            // sort by slope order wrf current point
            // since orderedBySlope is already sorted by (x,y), after it's been sorted by slope, it's also sorted by (x,y)

            List<Point> buffer = new ArrayList<Point>();                // trace the adjacent, uni-slope points
            int b = 1;
            while (b < N) {
                double curSlope = curPoint.slopeTo(orderedBySlope[b]);          // initialization
                do {                                                    // use do-while if you want to run the loop for at least once
                    buffer.add(orderedBySlope[b++]);
                } while (b < N && curSlope == curPoint.slopeTo(orderedBySlope[b]));
                
                // check if current buffer qualifies
                if (buffer.size() > 2 && curPoint.compareTo(buffer.get(0)) < 0) {
                    // at least 3 other points in the buffer other than current point
                    // first point must be smaller than all other candidates in buffer, to avoid duplicates
                    allSegments.add(new LineSegment(curPoint, buffer.remove(buffer.size() - 1)));
                }
                buffer.clear();                                         // regardless, if curSlope != prevSlope, need to clear buffer.
            }
        }
        // convert it to array
        segments = allSegments.toArray(new LineSegment[allSegments.size()]);
    }
    public int numberOfSegments() {
        // the number of line segments
        return segments.length;
    }
    public LineSegment[] segments() {
         // the line segments
         return segments.clone(); // THIS IS A DEFENSIVE COPY!
    }

    private void checkNullPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("The pointer \"points\" must not be null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("The array \"points\" contain null elements");
            }
        }
    }

    private void checkDuplicatePoints(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length-1; i++) {
            if (points[i].compareTo(points[i+1]) == 0) {
                throw new IllegalArgumentException("Duplicate(s) found in \"points\" array.");
            }
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        // StdDraw.setPenRadius(0.04);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
       for (LineSegment segment : collinear.segments()) {
           StdOut.println(segment);
           segment.draw();
       }
       StdDraw.show();
    }
 }