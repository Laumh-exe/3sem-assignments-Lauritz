package JPA_Lifecycle_and_Annotations;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student("Lauritz", "Hauge", 27, "la@email.dk");
        Student student2 = new Student("John", "Johnson", 34, "john@email.dk");
        Student student3 = new Student("Bent", "Benson", 22, "benson@email.dk");

        StudentDAO studentDAO = new StudentDAO();
        studentDAO.saveStudent(student1);
        studentDAO.saveStudent(student2);
        studentDAO.saveStudent(student3);

        studentDAO.close();
    }

}
