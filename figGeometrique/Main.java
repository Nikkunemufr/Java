public class Main {

    public static void main(String[] args) {
        Point point1 = new Point(1, 2);
        System.out.println(Point.getnInstance());
        Point point2 = new Point();
        System.out.println(Point.getnInstance());
        Point point3 = new Point(8, 2);
        System.out.println(Point.getnInstance());

        point2.translation(1,1);
        System.out.println(point2);

        point2.homothetie(new Point(5,5),2);
        System.out.println(point2);
        point3.homothetie(5,5,2);
        System.out.println(point3);

        Cercle cercle1 = new Cercle(point1, 5);
        cercle1.translation(1,1);
        System.out.println(cercle1);

        Cercle cercle2 = new Cercle(point1, 5);
        cercle2.homothetie(new Point(1,1),3);
        System.out.println(cercle2);
        cercle2.homothetie(1,1,3);
        System.out.println(cercle2);

    }
}