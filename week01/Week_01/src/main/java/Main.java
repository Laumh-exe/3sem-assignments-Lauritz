import java.sql.SQLOutput;
import java.time.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        System.out.println("EX1 RESULTS:");
        ex1(); // Lambda

        System.out.println("\nEX2 RESULTS:");
        ex2();

        System.out.println("\nEX3 RESULTS:");
        ex3();

        System.out.println("\nEX4 RESULTS:");
        ex4();

        System.out.println("\nEX5 RESULTS:");
        ex5();

        System.out.println("\nEX6 RESULTS:");
        ex6();

        System.out.println("\nEX7 RESULTS:");
        ex7();

    }

    public static void ex1() {
        System.out.print(operate(1, 2, (a, b) -> a + b) + "\n");
        System.out.print(operate(10, 5, (a, b) -> a - b) + "\n");
        System.out.print(operate(10, 10, (a, b) -> a * b) + "\n");
        System.out.print(operate(10, 10, (a, b) -> a / b) + "\n");
        System.out.print(operate(10, 4, (a, b) -> a % b) + "\n");
        System.out.print(operate(2, 2, (a, b) -> (int) Math.pow(a, b)) + "\n");
    }

    public static int operate(int a, int b, ArithmeticOperation op) {
        return op.perform(a, b);
    }

    public static void ex2() {
        System.out.println("Multiply all numbers:");
        int[] numbers = {1, 2, 3, 4};
        int[] numbersTransformed = map(numbers, (a) -> a * 2);
        for (int n : numbersTransformed) {
            System.out.println(n);
        }

        System.out.println("Filter out uneven numbers:");
        int[] evenNumbers = filter(numbers, (a) -> (a % 2 == 0));
        for (int n : evenNumbers) {
            System.out.println(n);
        }
    }

    public static int[] map(int[] a, MyTransformingType op) {
        int[] transformed = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            transformed[i] = op.transform(a[i]);
        }
        return transformed;
    } //Multiply every number by 2

    public static int[] filter(int[] a, MyValidatingType op) {
        ArrayList<Integer> validatedNumbersList = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            if (op.validate(a[i])) {
                validatedNumbersList.add(a[i]);
            }
        }
        int[] validatedNumbersArray = new int[validatedNumbersList.size()];
        for (int i = 0; i < validatedNumbersArray.length; i++) {
            validatedNumbersArray[i] = validatedNumbersList.get(i);
        }
        return validatedNumbersArray;
    } //Filters to only even numbers

    public static void ex3() {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 12, 14, 20, 21, 35, 40};
        Predicate<Integer> isDivisible = a -> (a / 7.0) % 1 == 0;
        ArrayList<Integer> filteredNumbers = filterIfDivisibleBy7(numbers, isDivisible);
        System.out.println("Filter to only numbers that can be divided with 7:");
        for (int n : filteredNumbers) {
            System.out.println(n);
        }

        Supplier<Employee> createEmployee = () -> {
            String[] names = {"John", "Jane", "Jack", "Joe", "Jill", "Josephine", "Jan", "Job"};
            Random rnd = new Random();
            int n = rnd.nextInt(4, 15);
            ArrayList<Employee> employees = new ArrayList<>();
            return new Employee(names[rnd.nextInt(0, 8)]);
        };
        List<Employee> employees = new ArrayList<>();
        employees.add(createEmployee.get());
        employees.add(createEmployee.get());
        employees.add(createEmployee.get());
        employees.add(createEmployee.get());

        System.out.println("Print out list of employees");
        Consumer<Employee> printEmployee = (employee) -> {
            System.out.println(employee.getName());
        };
        employees.forEach(printEmployee);

        Function<List<Employee>, List<String>> convertToString = (list) -> {
            ArrayList<String> strings = new ArrayList<>();
            for (Employee employee : employees) {
                strings.add(employee.getName());
            }
            return strings;
        };

        List<String> employeeStrings = convertToString.apply(employees);

    }

    public static ArrayList<Integer> filterIfDivisibleBy7(int[] a, Predicate<Integer> isDivisible) {
        ArrayList<Integer> b = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            if (isDivisible.test(a[i])) {
                b.add(a[i]);
            }
        }
        return b;
    } //Returns filtered list of numbers that can be devided by 7

    public static void ex4() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Lauritz", LocalDate.of(1996, Month.FEBRUARY, 21)));
        employees.add(new Employee("John", LocalDate.of(2002, Month.OCTOBER, 2)));
        employees.add(new Employee("Bambi", LocalDate.of(1982, Month.DECEMBER, 29)));
        employees.add(new Employee("Santa", LocalDate.of(1743, Month.DECEMBER, 24)));
        employees.add(new Employee("Carl", LocalDate.of(1992, Month.JANUARY, 2)));
        employees.add(new Employee("Peter", LocalDate.of(1797, Month.JANUARY, 14)));

        LocalDate currentDate = LocalDate.now();
        for (Employee employee : employees) {
            Period age = employee.getAgePeriod();
            System.out.println(employee.getName() + " is " + age.getYears() + " years, " + age.getMonths() + " months and " + age.getDays() + " days old!");
        }

        int sum = 0;
        for (Employee employee : employees) {
            int age = employee.getAge();
            sum += age;
        }

        int average = sum / employees.size();
        System.out.println("The average age of employess is:" + average);
        //Calc with stream
        int averageWithStream = employees.stream()
            .collect(Collectors.collectingAndThen(
                Collectors.summarizingInt(Employee::getAge), summary -> (int) summary.getAverage()));

        System.out.println("The average calculated from Stream is: " + averageWithStream);

        employees.stream().filter(employee -> employee.getBirthDate().getMonth() == Month.FEBRUARY).forEach(employee -> System.out.println(employee.getName()));

        Map<Month, Long> employeesBirthMonthCount = employees.stream()
            .collect(Collectors.
                groupingBy(employee -> employee.getBithMonth(), Collectors.counting()));

        System.out.println("The number of employees per birthmonth: ");
        employeesBirthMonthCount.forEach((month, count) -> System.out.println("Month: " + month + ", Count: " + count));

        System.out.println("The following employees have birthday this current month: ");
        employees.stream().filter(employee -> employee.getBithMonth() == LocalDate.now().getMonth()).forEach(employee -> System.out.println(employee.getName()));
    }

    public static void ex5() {
        //Rewrite ex2 with method reference:
        System.out.println("Multiply all numbers:");
        int[] numbers = {1, 2, 3, 4};
        ;
        IntStream numbersDoubled = Arrays.stream(numbers).map(Main::doubleValue);
        numbersDoubled.forEach(System.out::println);

        System.out.println("Filter out uneven numbers:");
        IntStream filterNumbers = Arrays.stream(numbers).filter(Main::checkEven);
        filterNumbers.forEach(System.out::println);
    }

    public static Integer doubleValue(int a) {
        return a * 2;
    }

    public static boolean checkEven(int a) {
        return a % 2 == 0;
    }

    public static void ex6() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Harry Potter & The Deathly Hollows", "J.K.Rowling", Year.of(2001), 563, 7));
        books.add(new Book("The Shining", "Stephen King", Year.of(1976), 439, 8));
        books.add(new Book("IT", "Stephen King", Year.of(1976), 439, 6));
        books.add(new Book("Pet Sematary", "Stephen King", Year.of(1976), 439, 3));
        books.add(new Book("The Monster under the sea", "John B Hemmington", Year.of(1832), 820, 4));

        Double average = books.stream()
            .collect(
                Collectors.collectingAndThen(
                    Collectors.summarizingInt(Book::getRating),
                    (sum) -> sum.getAverage()
                )
            );
        System.out.println("The average rating for all books is: " + String.format("%.2f", average));

        books.stream()
            .filter(book -> book.getPublicationYear().equals(Year.of(2001)))
            .forEach((book -> System.out.println("The following book(s) was released in 2001: " + book.getTitle())));

        books.stream()
            .sorted(Comparator.comparing(Book::getRating).reversed())
            .forEach(book -> System.out.println(book.getTitle() + ", Has rating: " + book.getRating()));

        books.stream()
            .max(Comparator.comparing(Book::getRating))
            .ifPresentOrElse(book -> System.out.println("The book with the highest rating is: " + book.getTitle() + " with a rating of " + book.getRating()), () -> System.out.println("No books found in list"));

        books.stream()
            .collect(
                Collectors
                    .groupingBy(
                        Book::getAuthor, Collectors.collectingAndThen(
                            Collectors.summarizingInt(Book::getRating),
                            (sum) -> sum.getAverage())))
            .forEach((author, averageRating) -> System.out.println("Author: " + author + ", Average Rating: " + String.format("%.1f", averageRating)));

        Long totalAmountOfPages = books.stream()
            .collect(
                Collectors.summarizingInt(Book::getPages))
            .getSum();

        System.out.println("The total amount of pages is: " + totalAmountOfPages);
    }

    public static void ex7() {

        //Create a collection of Transaction objects to work with. You can either create a sample dataset or read data from a file or database.
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(10000.0, "USD"));
        transactions.add(new Transaction(372, "USD"));
        transactions.add(new Transaction(200.0, "USD"));
        transactions.add(new Transaction(45.0, "EUR"));
        transactions.add(new Transaction(12.0, "EUR"));
        transactions.add(new Transaction(53.0, "EUR"));
        transactions.add(new Transaction(20.0, "DKK"));
        transactions.add(new Transaction(15.0, "DKK"));
        transactions.add(new Transaction(123.0, "DKK"));
        transactions.add(new Transaction(314.0, "DKK"));

        //Calculate Sum of all transactions
        Double transactionSum = transactions.stream()
            .collect(
                Collectors
                    .summingDouble(Transaction::getAmount));
        //Alternative: Double transactionSum = transactions.stream().mapToDouble(Transaction::getAmount).sum();

        System.out.println("Sum of transactions: " + transactionSum + "\n");

        //Group transactions by currency and calculate the sum of amounts for each currency.
        System.out.println("Total transaction amount for each currency");
        transactions.stream()
            .collect(Collectors.groupingBy(Transaction::getCurrency,
                Collectors.summingDouble(Transaction::getAmount)))
            .forEach((currency, totalAmount) -> System.out.println(currency + " : " + totalAmount));

        //Find the highest transaction amount.

        transactions.stream()
            .collect(
                Collectors
                    .maxBy(
                        Comparator.comparing(Transaction::getAmount)
                    )
            )
            .ifPresentOrElse(transaction -> System.out.println("\nHighest transaction amount: " + transaction.getAmount()), () -> System.out.println("\nNo transactions available"));

        double average = transactions.stream()
            .collect(
                Collectors
                    .averagingDouble(Transaction::getAmount));

        System.out.println("Average of all transactions: " + average);
    }

    public static void ex8() {

    }

}



