public class Point {

    private double x, y;
    private static int nInstance = 0;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        nInstance++;
    }

    public Point() {
        this(0, 0);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static int getnInstance() {
        return nInstance;
    }

    public void translation(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void homothetie(Point centre, double k) {
        x = (1 - k) + centre.getX() + k * getX();
        y = (1 - k) + centre.getY() + k * getY();
    }

    public void homothetie(double centreX, double centreY, double k) {
        x = (1 - k) + centreX + k * getX();
        y = (1 - k) + centreY + k * getY();
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

}