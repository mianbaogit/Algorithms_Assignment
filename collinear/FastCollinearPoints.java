/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegs = null;

    public FastCollinearPoints(Point[] points) {   // finds all line segments containing 4 or more points
        if (points != null) {
            for (int i = 0; i < points.length-1; i++) {
                if (points[i] == null)
                    throw new IllegalArgumentException();

                Point[] subs = Arrays.copyOfRange(points, i+1, points.length);
                Arrays.sort(subs, points[i].slopeOrder());
                Point startInd = points[i];
                Point endInd = startInd;
                int numOfP = 1;

                for (int j = 0; j < subs.length; j++) {
                    if (points[i].compareTo(subs[j]) == 0)
                        throw new IllegalArgumentException();
                    if (j == 0) {
                        startInd = startInd.compareTo(subs[j]) > 0 ? subs[j] : startInd;
                        endInd = endInd.compareTo(subs[j]) > 0 ? endInd : subs[j];
                    } else if (points[i].slopeTo(subs[j]) == points[i].slopeTo(subs[j-1])) {
                        numOfP++;
                        startInd = startInd.compareTo(subs[j]) > 0 ? subs[j] : startInd;
                        endInd = endInd.compareTo(subs[j]) > 0 ? endInd : subs[j];

                    } else {
                        if (numOfP >= 3) {
                            addLineSegment(startInd, endInd);
                        }
                        startInd = points[i].compareTo(subs[j]) > 0 ? subs[j] : points[i];
                        endInd = points[i].compareTo(subs[j]) > 0 ? points[i] : subs[j];
                        numOfP = 1;

                    }
                }

                if (numOfP >= 3) {
                    addLineSegment(startInd, endInd);
                }
            }
        } else {
            throw new java.lang.IllegalArgumentException();
        }
    }
    public int numberOfSegments() {       // the number of line segments
        return lineSegs == null ? 0 : lineSegs.length;
    }
    public LineSegment[] segments()   {             // the line segments
        if (lineSegs == null) return new LineSegment[0];
        LineSegment[] tlineSegs = new LineSegment[lineSegs.length ];
        for (int h = 0; h < lineSegs.length; h++) {
            tlineSegs[h] =  lineSegs[h];
        }
        return tlineSegs;
    }
    private void addLineSegment(Point startInd, Point endInd) {
        LineSegment line = new LineSegment(startInd, endInd);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
