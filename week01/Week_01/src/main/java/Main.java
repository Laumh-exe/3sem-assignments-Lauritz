import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
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

        System.out.println("EX2 RESULTS:");
        ex2();

        System.out.println("EX3 RESULTS:");
        ex3();

        System.out.println("EX4 RESULTS:");
        ex4();

        System.out.println("EX5 RESULTS:");
        ex5();

    }

    public static void ex1() {
        System.out.print(operate(1,2, (a,b) -> a+b)+"\n");
        System.out.print(operate(10,5, (a, b) -> a-b)+"\n");
        System.out.print(operate(10,10, (a, b) -> a*b)+"\n");
        System.out.print(operate(10,10, (a, b) -> a/b)+"\n");
        System.out.print(operate(10,4, (a, b) -> a%b)+"\n");
        System.out.print(operate(2,2, (a, b) -> (int) Math.pow(a,b))+"\n");
    }

    public static int operate(int a, int b, ArithmeticOperation op) {
        return op.perform(a,b);
    }

    public static void ex2() {
        System.out.println("Multiply all numbers:");
        int[] numbers = {1, 2, 3, 4};
        int[] numbersTransformed = map(numbers, (a) -> a*2);
        for(int n: numbersTransformed) {
            System.out.println(n);
        }

        System.out.println("Filter out uneven numbers:");
        int[] evenNumbers = filter(numbers, (a) -> (a % 2==0));
        for(int n: evenNumbers) {
            System.out.println(n);
        }
    }

    public static int[] map(int[] a, MyTransformingType op) {
        int[] transformed = new int[a.length];
        for(int i = 0; i < a.length; i++) {
            transformed[i] = op.transform(a[i]);
        }
        return transformed;
    } //Multiply every number by 2

    public static int[] filter(int[] a, MyValidatingType op) {
        ArrayList<Integer> validatedNumbersList = new ArrayList<>();
        for(int i = 0; i < a.length; i++) {
            if(op.validate(a[i])) {
                validatedNumbersList.add(a[i]);
            }
        }
        int[] validatedNumbersArray = new int[validatedNumbersList.size()];
        for(int i = 0; i < validatedNumbersArray.length; i++) {
            validatedNumbersArray[i] = validatedNumbersList.get(i);
        }
        return validatedNumbersArray;
    } //Filters to only even numbers

    public static void ex3() {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 12, 14, 20, 21, 35, 40};
        Predicate<Integer> isDivisible = a -> (a/7.0)%1==0;
        ArrayList<Integer> filteredNumbers = filterIfDivisibleBy7(numbers, isDivisible);
        System.out.println("Filter to only numbers that can be divided with 7:");
        for(int n : filteredNumbers) {
            System.out.println(n);
        }

        Supplier<Employee> createEmployee = () -> {
            String[] names = {"John", "Jane", "Jack", "Joe", "Jill", "Josephine", "Jan", "Job"};
            Random rnd = new Random();
            int n = rnd.nextInt(4,15);
            ArrayList<Employee> employees = new ArrayList<>();
            return new Employee(names[rnd.nextInt(0,8)]);
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
            for(Employee employee : employees) {
                strings.add(employee.getName());
            }
            return strings;
        };

        List<String> employeeStrings = convertToString.apply(employees);

    }

    public static ArrayList<Integer> filterIfDivisibleBy7(int[] a, Predicate<Integer> isDivisible) {
        ArrayList<Integer> b = new ArrayList<>();
        for(int i = 0; i < a.length; i++) {
            if(isDivisible.test(a[i])) {
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
        for(Employee employee : employees) {
            Period age = employee.getAgePeriod();
            System.out.println(employee.getName() + " is " + age.getYears() + " years, " + age.getMonths() + " months and " + age.getDays() + " days old!");
        }

        int sum = 0;
        for(Employee employee : employees) {
            int age = employee.getAge();
            sum += age;
        }

        int average = sum/employees.size();
        System.out.println("The average age of employess is:" + average);
        //Calc with stream
        int averageWithStream = employees.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.summarizingInt(Employee::getAge), summary -> (int) summary.getAverage()));

        System.out.println("The average calculated from Stream is: " + averageWithStream);

        employees.stream().filter(employee -> employee.getBirthDate().getMonth()==Month.FEBRUARY).forEach(employee -> System.out.println(employee.getName()));

        Map<Month, Long> employeesBirthMonthCount = employees.stream()
                .collect(Collectors.
                        groupingBy(employee -> employee.getBithMonth(), Collectors.counting()));

        System.out.println("The number of employees per birthmonth: ");
        employeesBirthMonthCount.forEach((month, count) -> System.out.println("Month: " + month + ", Count: " + count));

        System.out.println("The following employees have birthday this current month: ");
        employees.stream().filter(employee -> employee.getBithMonth()==LocalDate.now().getMonth()).forEach(employee -> System.out.println(employee.getName()));
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
        return a*2;
    }

    public static boolean checkEven(int a) {
        return a%2==0;
    }

    public static void ex6() {
        
    }
}

