/**
 * On how to https://stackoverflow.com/questions/1647260/java-dynamic-array-sizes
 * For simplicity, we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.
 */

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        checkNullPoints(points);
        checkDuplicatePoints(points);
        
        // dynamically hold the instances
        List<LineSegment> list = new ArrayList<LineSegment>();
        Arrays.sort(points); // this step would make later comparison step much easier
        for (int a = 0; a < points.length; a++) {
            for (int b = a+1; b < points.length; b++) {
                for (int c = b+1; c < points.length; c++) {
                    if (points[a].slopeTo(points[b]) == points[a].slopeTo(points[c])) { // if a,b,c aren't collinear, terminate early
                    for (int d = c+1; d < points.length; d++) {
                        if (points[a].slopeTo(points[b]) == points[a].slopeTo(points[d])) {
                                // found a collinear point quadruple
                                list.add(new LineSegment(points[a], points[d]));
                                }
                        }
                    }
                    else {
                        continue;
                    }                                                                                                                                                                                                                                                                                                                                                             
                }
            }
        }
        // convert it to array
        segments = list.toArray(new LineSegment[list.size()]);

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
    public int numberOfSegments() {
        return segments.length;
    }
    public LineSegment[] segments() {
        return segments.clone(); // THIS IS A DEFENSIVE COPY! 
    }
    public static void main(String[] args) {
        // test cases here
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

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }