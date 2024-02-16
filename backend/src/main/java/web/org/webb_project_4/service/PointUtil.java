package web.org.webb_project_4.service;

public class PointUtil {
    private static final double minX = -4;
    private static final double maxX = 4;

    private static final double minY= -4;
    private static final double maxY = 4;

    private static final double minR = -4;
    private static final double maxR = 4;

    public static boolean check(double x, double y, double r){
        if(r == 0){
            return false;
        }
        if(checkData(x, y, r)){
            if(r <= 0) r = r*(-1);
            if(checkIsRectangle(x, y, r) || checkIsCircle(x, y, r) || checkIsTriangle(x, y, r)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkData(double x, double y, double r){
        return (x>= minX && x <= maxX)
                && (y >= minY && y <= maxY)
                && (r >= minR && r <= maxR);
    }

    public static boolean checkIsRectangle(double x, double y, double r){
        return (y >= 0 && y <= r && x >= 0 && x <= r/2);
    }

    public static boolean checkIsCircle(double x, double y, double r){
        if(x<= 0 && y<= 0){
            double lengthFromNull = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
            return lengthFromNull <= r;
        }else{
            return false;
        }
    }

    private static boolean checkIsTriangle(double x, double y, double r){
        double x1 = -r/2;
        double x2 = 0;
        double x3 = 0;
        double y1 = 0;
        double y2 = 0;
        double y3 = r/2;

        double d1 = (x-x2)*(y3-y2) - (x3-x2)*(y-y2);
        double d2 = (x-x3)*(y1-y3) - (x1-x3)*(y-y3);
        double d3 = (x-x1)*(y2-y1) - (x2-x1)*(y-y1);

        boolean hasNegatives = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean hasPositives = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNegatives && hasPositives);
    }
}
