package GLS_Package_Tracking;
import The_Point_exercise.PointDAO;

public class Main {
    public static void main(String[] args) {
        PackageDAO packageDAO = new PackageDAO();
        packageDAO.savePackage(new Package("11", "John", "Simon"));
    }

}
