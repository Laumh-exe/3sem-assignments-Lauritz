package The_Point_exercise;

public class Main {
    public static void main(String[] args) {
        PointDAO pointDAO = new PointDAO();

        pointDAO.store100();

        System.out.println("Average" + pointDAO.findAverage());

        System.out.println("Number of points: " + pointDAO.findNumberOfPoints());

        pointDAO.getAll().forEach(System.out::println);

        pointDAO.close();

    }

}
