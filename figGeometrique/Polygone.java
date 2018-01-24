import java.util.ArrayList;

public class Polygone {
   
    private ArrayList<Point> contour;
   
    public Polygone(ArrayList<Point> contour){
        this.contour = contour;
    }
   
    public void ajout(Point p){
        contour.add(new Point(p));
    }
   
    public void translation(double dx, double dy){
        for (Point p : contour){
            p.translation(dx,dy);
        }
    }
   
    public void homothetie(double centreX,double centreY, double k){
        for (Point p : contour){
            p.homothetie(centreX,centreY,k);
        }
    }
    public String toString(){
        String retour = "Contour : ";
        for (Point p : contour){
            retour += p.toString() +" ";
        }
        return retour;
    }
}