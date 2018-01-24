public class Cercle{

    private Point centre;

    private double rayon;

    public Cercle(Point centre, double rayon) {
        this.centre = centre;
        this.rayon = rayon;
    }

    public Point getCentre() {
        return centre;
    }

    public void setCentre(Point centre) {
        this.centre = centre;
    }

    public double getRayon() {
        return rayon;
    }

    public void setRayon(double rayon) {
        this.rayon = rayon;
    }

    public void translation(double dx, double dy) {
        centre.translation(dx,dy);
    }

    public void homothetie(Point centre, double k) {
        this.centre.homothetie(centre,k);
        rayon *= k;
    }

    public void homothetie(double centreX, double centreY, double k) {
        this.centre.homothetie(centreX,centreY,k);
        rayon *= k;
    }

    @Override
    public String toString() {
        return "Cercle{" +
                "centre=" + centre +
                ", rayon=" + rayon +
                '}';
    }
}