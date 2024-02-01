public class Transaction {
    private static int idCount;
    private int id;
    private double amount;
    private String currency;

    public Transaction(double amount, String currency) {
        idCount++;
        this.id = idCount;
        this.amount = amount;
        this.currency = currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }


}
