package unicorns;

public class Main {
    public static void main(String[] args) {
        UnicornDAO unicornDAO = new UnicornDAO();

        Unicorn unicorn = new Unicorn();
        unicorn.setAge(4);
        unicorn.setName("Sparkle");
        unicorn.setPowerStrength(23);
        unicornDAO.save(unicorn);
    }
}
