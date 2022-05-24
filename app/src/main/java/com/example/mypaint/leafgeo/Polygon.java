package com.example.mypaint.leafgeo;

import android.util.Log;

import java.util.ArrayList;

/**
 * 多边形 polygon
 *  Point 的循环数组
 */

public class Polygon {

    private static final String TAG = "CLass: polygon";
    // Data Structure: Vertex, (Fixed Array)
    protected ArrayList<Point> vertexes;

    // Basic Constructors
    public Polygon(ArrayList<Point> pts){
        this.vertexes = pts;
    }
    public Polygon(){this.vertexes = new ArrayList<Point>();}

    // Basic Getters
    public int getSize(){
        return vertexes.size();
    }

    public Point getVertex(int index){
        return this.vertexes.get(index);
    }

    // tools
    public Point getPrev(Point cur){
        int index = vertexes.indexOf(cur);
        if ( index == 0){
            return getVertex(getSize() - 1);
        } else  {
            return getVertex(index - 1);
        }
    }
    public Point getNext(Point cur){
        int index = vertexes.indexOf(cur);
        if ( index == getSize() - 1){
            return getVertex(0);
        } else  {
            return getVertex(index + 1);
        }
    }
    public Point getFirstPoint(){
        return vertexes.get(0);
    }
    public Point getLastPoint(){
        return vertexes.get(vertexes.size() - 1);
    }

    // Modify
    public void addPoint(Point pt){
        this.vertexes.add(pt);
    }

    public void clear() {
        this.vertexes.clear();
    }
    // Basic Decider
    /**
     * 1. Direction: 左拐、右拐、CCW or CW ?
     * 严格秃顶点是左拐的，多边形就逆时针。
     */

    public boolean isCCW(){
        // 坐标系是反的！！！

        // int m = 0;
        // for (int i = 0; i < this.getSize(); i++) {
        //     Point pt_i = getVertex(i);
        //     Point pt_m = getVertex(m);
        //     if (pt_i.x < pt_m.x || 
        //         (pt_i.x == pt_m.x && pt_i.y < pt_m.y )){
        //             m = i;      // 相当于左下角的点
        //         }
        // }
        
        Point cur = getVertex(0);
        int size = getSize();
        for (Point point : vertexes) {
            if(point.x < cur.x || (cur.x == point.x && cur.y > point.y)){
                cur = point;    // 得到左下角的点
            }
        }

        return (isVertexLeft(cur));

    }

    protected boolean isVertexLeft(Point cur){
        return (GeoDecider.area22D(getNext(cur), getPrev(cur), cur) > 0);
    }

    /**
     * 2, 凸性判断
     * 每个顶点都是左拐或又拐的, 才是凸
     */
    public boolean isConvex(){
        int leftFlag = 0;
        int rightFlag = 0;
        for (Point point : vertexes) {
            if (isVertexLeft(point)) leftFlag++; 
            else rightFlag++;
            if (leftFlag * rightFlag != 0) return false;
        }
        return true;
    }


    /**
     * 3. 简单性判断
     * 对任意两个不相邻的边对，进行相交性判断
     */
    public boolean isSimple(){
        if (this.getSize() < 3) return false;

        ArrayList<LineSegment> lns = toLineSet();

        // 先判断第一条线（除了第一条线，其他的线都可以判断到最后一条线
        for(int j = 2; j <= lns.size()-2; j++){
            if(GeoDecider.is_Intersected2D(lns.get(0), lns.get(j))) {
                Log.d(TAG, "isSimple: 判断第一条线时出问题");
                return false;
            }
        }
        // 从第二条线(i = 1)开始到倒数第三条线(i = size()-3)，每条线都可以判断到最后一条线(size-1), 其中倒数第二条线已经在之前判断完了
        for (int i = 1; i <= lns.size() - 3; i++) {
            // 对中间的每条线来说：在判断第 2（i=1）条线时，需要判断的线为(i = 3) ~ (i = size-1)，
            for (int j = i + 2; j<=lns.size()-1 ; j++) { // j 从i的隔一条线开始，到最后一条线结束
                if (GeoDecider.is_Intersected2D(lns.get(i), lns.get(j))) {
                    Log.d(TAG, "isSimple: 判断线 i：" + String.valueOf(i) + " j:" + String.valueOf(j));
                    return false;
                }
            }
        }
        return true;
    } 

    public ArrayList<LineSegment> toLineSet(){
        ArrayList<LineSegment> lns = new ArrayList<>();
        if (getSize() < 2) {
            Log.d(TAG, "toLineSet: Only One Point");
            return lns;
        }
        for (int i = 0; i < getSize()-1; i++) {
            lns.add(new LineSegment(getVertex(i), getVertex(i+1)));
        }
        if (getSize() > 2){
            // 超过两个点就需要闭合了
            lns.add(new LineSegment(getLastPoint(), getFirstPoint()));
        }
//        Log.d(TAG, "toLineSet: " + lns.size());
        return lns;
    }

    protected boolean isNearLine(LineSegment ln1, LineSegment ln2, ArrayList<LineSegment> lns){
        int index1 = lns.indexOf(ln1);
        int index2 = lns.indexOf(ln2);
        int distance = Math.abs(index1 - index2);
        return (distance == 1 ||
                (index1 * index2 == 0 && distance == lns.size() - 1)
                );
    }

    /** 
     * 多边形面积计算
     * 取任意一点，对每条边都应用 Area2 求和
     */
    public float getArea2(){
        if (getSize() < 3){
            return 0;
        }

        float area2 = 0;
        // 使用现有的函数计算
        for (LineSegment ln : toLineSet()) {
            area2 += GeoDecider.area22D(new Point(0, 0), ln);
            // area2 += (ln.A.x - ln.B.x) * (ln.A.y + ln.B.y);
        }
        return area2;
    }

    /**
     * 多边形 形心计算
     * 原理：计算每个三角形形心 * 其面积，求和。再除以多边形总面积
     */
    public Point getCentroid(){

        // 一个点返回自身，两个点返回中心
        if (getSize() == 1) return getVertex(0);
        if (getSize() == 2) return getVertex(0).add(getVertex(1)).scalarMultiply(0.5f).toPoint();

        float area2 = getArea2();
        Point pt0 = new Point(0, 0, 0);
        Vector temp = new Point(0, 0, 0);

        for (LineSegment ln : toLineSet()) {
            temp = temp.add( getTriangleCentroid(pt0, ln.A, ln.B).scalarMultiply(GeoDecider.area22D(pt0, ln)) );
        }

        return temp.scalarMultiply(1.0f/area2).toPoint();
    }
    protected Point getTriangleCentroid(Vector pt, Vector A, Vector B){
        return pt.add(A).add(B).scalarMultiply((1.0f/3.0f)).toPoint();
    }

    /**
     * 点与多边形的关系，内、外、上
     * @param pt
     * @return 0, -1, 1
     * -1:  点在多边形上
     * 1： 点在多边形内
     * 0： 点在多边形外
     */
    public int whereIsPoint(Point pt){
        int cnt = 0;

        for (LineSegment ln : toLineSet()) {
            float detal = GeoDecider.area22D(pt, ln);

            if (detal == 0.0f){
                return -1;   // 点在多边形上
            }

            if (ln.A.y >= pt.y && pt.y >= ln.B.y){
                // A 上， B 下
                if(detal < 0) cnt++; //
            }
            if (pt.y >= ln.A.y && pt.y <= ln.B.y){
                // A 下， B 上
                if(detal > 0) cnt++;
            }
        }
        return (cnt%2==0) ? 0:1;
    }


    /**
     * 三角剖分（切耳算法）
     * 工具函数：判断一个顶点是否是耳朵: 提前判断所有的顶点是否是耳朵，记录他们的下标.
     * 对每个下标进行操作：如果前面的已经被删掉了，跳过，否则记录下三角形， 然后把当前顶点删掉。
     * 然后把剩下的递归判断。结束是 Size <= 3
     */
    public ArrayList<Triangle> getTriangulation(){
        ArrayList<Triangle> triangles = new ArrayList<>();

        Polygon polyCopy = this.copy();

        if (polyCopy.getSize() < 3){
            Log.d(TAG, "getTriangulation: Size < 3");
            return triangles;
        }

        if (polyCopy.getSize() == 3){
            triangles.add(new Triangle(getVertex(0), getVertex(1), getVertex(2)));
            return triangles;
        }

        ArrayList<Point> earPoints = polyCopy.getEarPoints();

        if (earPoints.isEmpty()){
            Log.d(TAG, "getTriangulation: Empty EarPoints");
        }
        
        ArrayList<Point> markedPoint = new ArrayList<>();

        // 执行一轮polyCopy的删减顶点 与 三角形集合的增加顶点:
        for (Point ear : earPoints) {

            if(markedPoint.contains(ear)){ // 被标记过的点就跳过
                continue;
            }

            // 没被标记过: 标记，加三角形, 删掉：
            Point prev = polyCopy.getPrev(ear);
            Point next = polyCopy.getNext(ear);

            // 标记
            markedPoint.add(ear);
            markedPoint.add(prev);
            markedPoint.add(next);

            // 加三角形
            triangles.add(new Triangle(prev, ear, next));

            // 删掉这个ear
            polyCopy.vertexes.remove(ear);
        }

        // 递归调用
        triangles.addAll(polyCopy.getTriangulation());
        return triangles;
    }

    public ArrayList<Point> getEarPoints(){
        ArrayList<Point> earPoints = new ArrayList<>();

        boolean isEarFlag;
        for (Point cur : vertexes){

            isEarFlag = true;

            Point prev = getPrev(cur);
            Point next = getNext(cur);

            // 如果是顺时针， 需要判定 <=0; 如果是逆时针，需要判定 >=0   (不知道为什么)
            boolean flagCCW = isCCW();
            float temp_area2 = GeoDecider.area22D(prev, cur, next);

            // 如果是CCW，>0 时才可能是耳朵， 如果是CW，<0 时才可能是耳朵, 借此先排除一些非凸顶点
            if( (flagCCW && temp_area2 <= 0) || (!flagCCW && temp_area2 >=0) ){
                isEarFlag = false; // Not necessary
                continue;
            };

            // 非凸顶点排除完了，走到这一步的点都是凸顶点
            // 对这个凸顶点构造一个三角形，对其他所有点，与这个三角形的三条边进行判断。
            for (Point pt: vertexes ) {
                if (pt == cur || pt == prev || pt == next){
                    // 是三角形的顶点？ 跳过
                    continue;
                }
                // 为什么这三个都是正的，就能够判定点在三角形内？
                float areaABpt = GeoDecider.area22D(prev, cur, pt);
                float areaBCpt = GeoDecider.area22D(cur, next, pt);
                float areaCApt = GeoDecider.area22D(next, prev, pt);
                boolean areaFlagForCW = (areaABpt>0 && areaBCpt>0 && areaCApt>0);
                boolean areaFlagForCCW = (areaABpt<0 && areaBCpt<0 && areaCApt<0);

                // 如果是 CCW，
//                if(!isCCW() && GeoDecider.area22D(prev, cur, pt)  > 0 &&
//                   GeoDecider.area22D(cur, next, pt) > 0 &&
//                   GeoDecider.area22D(next, prev, pt) > 0
//                    // 为什么这样判断？？？ ：
                if( (isCCW() && areaFlagForCW) ||
                        (!isCCW() && areaFlagForCCW)
                ){
                    isEarFlag = false;
                }
            }

            if(isEarFlag){
                earPoints.add(cur);
            }
        }

        return earPoints;
    }

    // 硬拷贝
    public Polygon copy(){
        ArrayList<Point> copy = new ArrayList<>();
        for (Point pt: vertexes) {
            copy.add(new Point(pt.x, pt.y));
        }
        return new Polygon(copy);
    }



}
