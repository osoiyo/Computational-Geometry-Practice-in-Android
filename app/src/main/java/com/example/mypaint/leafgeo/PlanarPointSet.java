package com.example.mypaint.leafgeo;

import java.nio.channels.Pipe;
import java.util.ArrayList;

public class PlanarPointSet extends Polygon{
    public PlanarPointSet(ArrayList<Point> pts) {
        super(pts);
    }
    public PlanarPointSet() {
        super();
    }

    /**
     * 平面点集的凸包： 卷包裹算法
     * 1. 取极点
     */

    public Point getPolePoint(){
        Point pole = getFirstPoint();
        for (Point pt : vertexes) {
            if (pt.y < pole.y){
                pole = pt;
            }
        }
        return pole;
    }

    public void setPoint(int index, Point pt){
        this.vertexes.get(index).x  = pt.x;
        this.vertexes.get(index).y  = pt.y;
        this.vertexes.get(index).z  = pt.z;
    }

    @Deprecated
    public Polygon wrapHull(){
        Polygon result = new Polygon();
        // 取坐标极点, 并加入结果集
        Point pole = this.getPolePoint();
        result.addPoint(pole);
        Point first = this.getFirstPoint();
//        do{
//            // 每次都先假装添加第一个点（数组的尾部）
//            result.addPoint(first);
//            if(pole.equal(first)) result.
//        } while ( result.getLastPoint() != result.getFirstPoint() ); // 有转回来了
        return result;
    }


}
