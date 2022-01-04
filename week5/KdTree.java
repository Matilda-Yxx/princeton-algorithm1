import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.LinkedList;
import java.util.Queue;

public class KdTree {
    private Node root;
    private int size;
    private enum Separator {VERT, HORI}

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private Separator sprt;

        public Node (Point2D p, RectHV rect, Node lb, Node rt, Separator sprt) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
            this.sprt = sprt;
        }

        public Separator getSeparator() { return this.sprt; }
        public Point2D getPoint() { return this.p; }
        public boolean isRightOrTopOf(Point2D other) {
            // check if should insert newNode to the right of the curNode
            if ( this.sprt == Separator.VERT ) return (this.p.x() > other.x());
            else return (this.p.y() > other.y());
        }

        public boolean equals(Point2D other) {
            return (this.p.equals(other));
        }

        public Separator getNextSpr() {
            return sprt == Separator.VERT ? Separator.HORI : Separator.VERT;
        }

        public RectHV getRectLB() {
            if ( sprt == Separator.VERT ) {
                return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
            } else {
                return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
            }
        }

        public RectHV getRectRT() {
            if ( sprt == Separator.VERT ) {
                return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            } else {
                return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
            }
        }

        public RectHV getRect() {
            return this.rect;
        }

        public boolean assignedRectIntersectWith(RectHV other) {
            return this.rect.intersects(other);
        }

        public double distToPoint(Point2D other) {
            return this.p.distanceSquaredTo(other);
        }

        public double assignedRectNearestDistToPoint(Point2D other) {
            return this.rect.distanceSquaredTo(other);
        }
     }


    public KdTree() {
        root = null;
        size = 0;
    }
    
    public boolean isEmpty() { return size == 0; }
    
    public int size() { return size; }
    
    public void insert(Point2D p) {
        checkNull(p);
        if (this.contains(p)) return;
        if (this.size == 0) {
            root = new Node(p, new RectHV(0, 0, 1, 1), null, null, Separator.VERT);
        } else {
            Node curNode = root;
            Node prevNode;
            do {
                // if not null, keep traversing the tree until hitting null
                
                if (curNode.equals(p)) return; // stop insertion
                
                prevNode = curNode;
                if (prevNode.isRightOrTopOf(p)) { 
                    curNode = prevNode.lb; 
                }
                else curNode = prevNode.rt;
            } while (curNode != null);

            if(prevNode.isRightOrTopOf(p)) { 
                prevNode.lb = new Node(p, prevNode.getRectLB(), null, null, prevNode.getNextSpr());
            } else {
                prevNode.rt = new Node(p, prevNode.getRectRT(), null, null, prevNode.getNextSpr());
            }
        }
        size ++;
    }

    public boolean contains(Point2D p) {
        checkNull(p);

        if (this.size == 0) {
            return false; 
        } else {
            Node curNode = root;
            Node prevNode;
            do {
                if (curNode.equals(p)) return true;
                
                prevNode = curNode;
                if (prevNode.isRightOrTopOf(p)) { 
                    curNode = prevNode.lb;
                }
                else curNode = prevNode.rt;
            } while (curNode != null);
        }
        return false;
    }
    
    public void draw() {
        draw(root);
    }

    private void draw(Node curNode) {
        // if (curNode == null) return;
        
        // StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.setPenRadius(0.01);
        // curNode.getPoint().draw();
        // StdDraw.setPenRadius(0.003);
        // if (curNode.getSeparator() == Separator.VERT) {
        //     StdDraw.setPenColor(StdDraw.BLUE);
        //     StdDraw.line(curNode.getPoint().x(), curNode.getRect().ymin(), curNode.getPoint().x(), curNode.getRect().ymax());
        // } else {
        //     StdDraw.setPenColor(StdDraw.RED);
        //     StdDraw.line(curNode.getRect().xmin(), curNode.getPoint().y(), curNode.getRect().xmax(), curNode.getPoint().y());
        // }
        // draw(curNode.rt);
        // draw(curNode.lb);
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        checkNull(rect);
        Queue<Point2D> allEligibleNodes = new LinkedList<Point2D>();
        Node curNode = root;
        findAll(allEligibleNodes, root, rect);
        return allEligibleNodes;
    }

    private void findAll(Queue<Point2D> allEligibleNodes, Node curNode, RectHV rect) {
        
        if(curNode == null) return;

        Point2D curPoint = curNode.getPoint();
        if (rect.contains(curPoint)) allEligibleNodes.add(curPoint);

        // check if the assigned rectange of curNode intersects the given rectangle
        // if not, no need to search the current tree
        // if (!curNode.assignedRectIntersectWith(rect)) return;
        if (curNode.getSeparator() == Separator.VERT) {
            if (curPoint.x() < rect.xmin()) findAll(allEligibleNodes, curNode.rt, rect);
            else if (curPoint.x() > rect.xmax()) findAll(allEligibleNodes, curNode.lb, rect);
            else {
                findAll(allEligibleNodes, curNode.lb, rect);
                findAll(allEligibleNodes, curNode.rt, rect);
            }
        } else {
            if (curPoint.y() < rect.ymin()) findAll(allEligibleNodes, curNode.rt, rect);
            else if (curPoint.y() > rect.ymax()) findAll(allEligibleNodes, curNode.lb, rect);
            else {
                findAll(allEligibleNodes, curNode.lb, rect);
                findAll(allEligibleNodes, curNode.rt, rect);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        checkNull(p);
        return findNearest(root, root, p).getPoint();
    }

    private Node findNearest(Node champion, Node curNode, Point2D queryPoint) {
        // base case
        if (curNode == null) return champion;

        // prune a tree if it has no chance to beat the current champion
        double distToRect = curNode.assignedRectNearestDistToPoint(queryPoint);
        double minDist = champion.distToPoint(queryPoint);
        if (distToRect > minDist) return champion;

        // update champion if curNode is closer
        double curDist = curNode.distToPoint(queryPoint);
        if (curDist < minDist) champion = curNode;

        // look into LB tree if query point is within the assigned rectangle
        if (curNode.isRightOrTopOf(queryPoint)) {
            champion = findNearest(champion, curNode.lb, queryPoint);
            champion = findNearest(champion, curNode.rt, queryPoint);
        } else {
            champion = findNearest(champion, curNode.rt, queryPoint);
            champion = findNearest(champion, curNode.lb, queryPoint);
        }

        return champion;
    }


    private void checkNull (Object o) {
        if (o == null) throw new IllegalArgumentException("Input argument should not be null.");
    }

    public static void main(String[] args) {

        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            StdOut.println(kdtree.contains(p));
        }

        kdtree.draw();
        // StdDraw.setPenRadius(0.05);
        // kdtree.nearest(new Point2D(0.81, 0.3)).draw();
    }
 }