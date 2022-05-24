package com.example.mypaint.leafgeo;


public class GeoDecider {

    // Vector Null
    public static boolean is_NullVector(Vector v) {
        final double EPS = (float) 1e-5;

        return (-EPS < v.x && v.x < EPS &&
                -EPS < v.y && v.y < EPS &&
                -EPS < v.z && v.z < EPS);
    }

    // Vector Vertical
    public static boolean is_VectorVertical(Vector v1, Vector v2) {
        return v1.pointMultiply(v2) == 0;
    }

    // Distance Between 2 Point
    public static float dis_OfPoints(Point A, Point B) {
        return B.subtract(A).module();
    }

    // Point is on Line
    // (Point, Line) || (Point, Point, Point)
    public static boolean is_PointOnLine(Point pt, LineSegment ln) {
        return is_NullVector(ln.toVector().crossMultiply(pt.subtract(ln.A)));
    }

    public static boolean is_PointOnLine(Point pt, Point A, Point B) {
        LineSegment ln = new LineSegment(A, B);
        return is_PointOnLine(pt, ln);
    }

    // Point is on LineSegment
    // (Point, Line) || (Point, Point, Point)
    public static boolean is_PointOnLineSegment(Point pt, LineSegment ln) {
        return is_PointOnLine(pt, ln) &&
                (pt.x > Math.min(ln.A.x, ln.B.x)) && (pt.x < Math.max(ln.A.x, ln.B.x)) &&
                (pt.y > Math.min(ln.A.y, ln.B.y)) && (pt.y < Math.max(ln.A.y, ln.B.y));
    }

    public static boolean is_PointOnLineSegment(Point pt, Point A, Point B) {
        LineSegment ln = new LineSegment(A, B);
        return is_PointOnLineSegment(pt, ln);
    }

    // area22D
    // (Point, Line) || (Point, Point, Point)
    public static float area22D(Point pt, LineSegment ln) {
        // (B - A) X (pt - A)
        return ln.toVector().crossMultiply2D(pt.subtract(ln.A));
    }

    public static float area22D(Point pt, Point A, Point B) {
        LineSegment ln = new LineSegment(A, B);
        return area22D(pt, ln);
    }

    // Distance Between Point and LineSegment
    // (Point, LineSegment) || (Point, Point, Point)
    public static float dis_PointToSegment(Point pt, LineSegment ln) {

        log("\n>>>求点到直线的距离");
        log(pt, ln);

        Vector v1_lnAtoB = ln.toVector();
        Vector v2_ptToA = pt.subtract(ln.A);

        double v1_moduleSqure = Math.pow(v1_lnAtoB.module(), 2);
        float v1_ptMul_v2 = v1_lnAtoB.pointMultiply(v2_ptToA);

        float result = 0;

        if (v1_ptMul_v2 <= 0) {
            log(" 投影长度为负");
            result = dis_OfPoints(pt, ln.A);
        } else if (v1_ptMul_v2 > v1_moduleSqure) {
            log("  投影长度超过直线");
            result = dis_OfPoints(pt, ln.B);
        } else {
            log("  投影落在直线上");
            result = v1_lnAtoB.crossMultiply(v2_ptToA).module() / v1_lnAtoB.module();
        }
        log(">>>距离为：" + String.valueOf(result) + " \n");
        return result;
    }

    public static float dis_PointToSegment(Point pt, Point A, Point B) {
        LineSegment ln = new LineSegment(A, B);
        return dis_PointToSegment(pt, ln);
    }

    // is Two 2D LineSegments Intersected?
    public static boolean is_Intersected2D(LineSegment ln1, LineSegment ln2) {

        boolean result = false;

        Point A = ln1.A;
        Point B = ln1.B;
        Point C = ln2.A;
        Point D = ln2.B;

        float abc = area22D(A, B, C);
        float abd = area22D(A, B, D);
        float cda = area22D(C, D, A);
        float cdb = area22D(C, D, B);

        if (((abc > 0 && abd < 0) || (abc < 0 && abd > 0)) && ((cda > 0 && cdb < 0) || (cda < 0 && cdb > 0))) result = true;
        if (abc == 0 && is_PointOnLine(C, A, B)) result = true;
        if (abd == 0 && is_PointOnLine(D, A, B)) result = true;
        if (cda == 0 && is_PointOnLine(A, C, D)) result = true;
        if (cdb == 0 && is_PointOnLine(B, C, D)) result = true;

        log( "\n" + (result ? "线段相交" : "线段不相交") + "\n");
        return result;
    }
    public static boolean is_Intersected2D(Point A, Point B, Point C, Point D){
        LineSegment ln1 = new LineSegment(A, B);
        LineSegment ln2 = new LineSegment(C, D);
        return is_Intersected2D(ln1, ln2);
    }

    // My Log Info
    public static void log(Point pt, LineSegment ln) {
        System.out.println(
                "  Pt:" + pt.toString() +
                        "  A:" + ln.A.toString() +
                        "  B:" + ln.B.toString());
    }

    public static void log(String msg) {
        System.out.println(msg);
    }

}