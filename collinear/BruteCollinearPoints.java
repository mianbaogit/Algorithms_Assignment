/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegs = null;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        if (points != null) {
            for (int i = 0; i < points.length; i++) {
                if (points[i] == null)
                    throw new IllegalArgumentException();
                for (int j = i+1; j < points.length; j++) {
                    if (points[i].compareTo(points[j]) == 0)
                        throw new IllegalArgumentException();
                    for (int k = j+1; k < points.length; k++) {
                        double r1 = points[i].slopeTo(points[j]);
                        double r2 = points[j].slopeTo(points[k]);
                        if (r1 == r2)
                            for (int r = k+1; r < points.length; r++) {
                                double r3 = points[j].slopeTo(points[k]);
                                double r4 = points[k].slopeTo(points[r]);
                                if (r3 == r4) {
                                    Point[] temp = new Point[4];
                                    temp[0] = points[i];
                                    temp[1] = points[j];
                                    temp[2] = points[k];
                                    temp[3] = points[r];

                                    Arrays.sort(temp);

                                    LineSegment line = new LineSegment(temp[0], temp[3]);
                                    if (lineSegs == null) {
                                        lineSegs = new LineSegment[1];
                                        lineSegs[0] = line;
                                    } else {
                                        LineSegment[] tlineSegs = new LineSegment[lineSegs.length + 1];
                                        for (int h = 0; h < lineSegs.length; h++) {
                                            tlineSegs[h] =  lineSegs[h];
                                        }
                                        tlineSegs[lineSegs.length] = line;
                                        lineSegs = tlineSegs;
                                    }
                                }
                            }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
    public int numberOfSegments() {       // the number of line segments
        return lineSegs == null ? 0 : lineSegs.length;
    }
    public LineSegment[] segments() {             // the line segments
        if (lineSegs == null) return new LineSegment[0];
        LineSegment[] tlineSegs = new LineSegment[lineSegs.length];
        for (int h = 0; h < lineSegs.length; h++) {
            tlineSegs[h] =  lineSegs[h];
        }
        return tlineSegs;
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

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
