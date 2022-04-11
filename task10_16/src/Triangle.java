public class Triangle {
    public Point[] points;
    public Triangle(Point[] points){
        if(points.length != 3) throw new IllegalArgumentException();
        this.points = points;
    }

    public boolean checkIfInAllCoordQuarter(){
        boolean intersectOyUpper0 = false;
        boolean intersectOyLower0 = false;
        boolean intersectOxLeft0 = false;
        boolean intersectOxRight0 = false;
        for (int i = 0; i < 3; i++) {
            Point a = points[i];
            Point b = points[i == 2 ? 0 : i + 1];
            double xIntersect = xCoordIntersectOy(a, b);
            double yIntersect = yCoordIntersectOx(a, b);
            if (Math.min(a.x, b.x) <= xIntersect && xIntersect <= Math.max(a.x, b.x)) {
                if (xIntersect >= 0) intersectOxRight0 = true;
                if (xIntersect <= 0) intersectOxLeft0 = true;
            }

            if (Math.min(a.y, b.y) <= yIntersect && yIntersect <= Math.max(a.y, b.y)) {
                if (yIntersect >= 0) intersectOyLower0 = true;
                if (yIntersect <= 0) intersectOyUpper0 = true;
            }
        }
        return intersectOyUpper0 && intersectOyLower0 && intersectOxLeft0 && intersectOxRight0;
    }

    public double yCoordIntersectOx(Point p1, Point p2){
        double A = p1.y - p2.y;
        double B = p2.x - p1.x;
        double C = p1.x * p2.y - p2.x * p1.y;
        return -C / B;
    }

    public double xCoordIntersectOy(Point p1, Point p2){
        double A = p1.y - p2.y;
        double B = p2.x - p1.x;
        double C = p1.x * p2.y - p2.x * p1.y;
        return -C / A;
    }

    public static Triangle getFromString(String input){
        String[] splitted = input.split(" ");
        if(splitted.length != 6) throw new IllegalArgumentException();
        Point[] points = new Point[3];
        for(int i=0; i<3; i++){
            points[i] = new Point(Integer.parseInt(splitted[i*2]), Integer.parseInt(splitted[i*2+1]));
        }
        return new Triangle(points);
    }

    public String toString(){
        StringBuilder string = new StringBuilder();
        for(int i=0; i<3; i++){
            string.append(points[i].x).append(" ").append(points[i].y);
            if(i != 2) string.append(" ");
        }
        return string.toString();
    }
}
