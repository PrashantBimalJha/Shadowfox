import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {
    private static ArrayList<String> history = new ArrayList<>();
    private static double memory = 0;
    private static int decimalPlaces = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1; // Initialize choice to -1 to avoid uninitialized error

        do {
            System.out.println("\n--- Enhanced Calculator ---");
            System.out.println("1. Basic Arithmetic Operations");
            System.out.println("2. Scientific Calculations");
            System.out.println("3. Unit Conversions");
            System.out.println("4. Change Decimal Precision");
            System.out.println("5. Memory Functions");
            System.out.println("6. Show Calculation History");
            System.out.println("7. Simulated Real-time Currency Conversion");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt(); // Get user input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); // Clear invalid input
                continue; // Continue loop if input is invalid
            }

            switch (choice) {
                case 1:
                    basicOperations(scanner);
                    break;
                case 2:
                    scientificCalculations(scanner);
                    break;
                case 3:
                    unitConversions(scanner);
                    break;
                case 4:
                    changeDecimalPrecision(scanner);
                    break;
                case 5:
                    memoryFunctions(scanner);
                    break;
                case 6:
                    showHistory();
                    break;
                case 7:
                    simulatedCurrencyConversion(scanner);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 8);

        scanner.close();
    }

    // Basic Arithmetic Operations
    public static void basicOperations(Scanner scanner) {
        try {
            System.out.print("Enter first number: ");
            double num1 = scanner.nextDouble();
            System.out.print("Enter second number: ");
            double num2 = scanner.nextDouble();
            System.out.println("Choose operation: +, -, *, /");
            char op = scanner.next().charAt(0);
            double result = 0;

            switch (op) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        System.out.println("Error: Division by zero.");
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid operation.");
                    return;
            }

            result = roundToDecimalPlaces(result);
            System.out.println("Result: " + result);
            addToHistory(num1 + " " + op + " " + num2 + " = " + result);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter valid numbers.");
            scanner.next(); // Clear invalid input
        }
    }

    // Scientific Calculations
    public static void scientificCalculations(Scanner scanner) {
        System.out.println("Choose: 1 for Square Root, 2 for Exponentiation, 3 for Trigonometric Functions, 4 for Logarithms, 5 for Factorial");
        int sciChoice = scanner.nextInt();
        double result = 0;

        switch (sciChoice) {
            case 1:
                System.out.print("Enter number for square root: ");
                double num = scanner.nextDouble();
                if (num >= 0) {
                    result = Math.sqrt(num);
                    System.out.println("Square Root: " + roundToDecimalPlaces(result));
                } else {
                    System.out.println("Error: Negative number.");
                }
                break;
            case 2:
                System.out.print("Enter base: ");
                double base = scanner.nextDouble();
                System.out.print("Enter exponent: ");
                double exponent = scanner.nextDouble();
                result = Math.pow(base, exponent);
                System.out.println("Exponentiation Result: " + roundToDecimalPlaces(result));
                break;
            case 3:
                System.out.println("Choose: 1 for Sine, 2 for Cosine, 3 for Tangent");
                int trigChoice = scanner.nextInt();
                System.out.print("Enter angle in degrees: ");
                double angle = Math.toRadians(scanner.nextDouble());

                if (trigChoice == 1) result = Math.sin(angle);
                else if (trigChoice == 2) result = Math.cos(angle);
                else if (trigChoice == 3) result = Math.tan(angle);
                else {
                    System.out.println("Invalid choice.");
                    return;
                }
                System.out.println("Result: " + roundToDecimalPlaces(result));
                break;
            case 4:
                System.out.print("Enter number for logarithm: ");
                double logNum = scanner.nextDouble();
                result = Math.log10(logNum);
                System.out.println("Logarithm (base 10): " + roundToDecimalPlaces(result));
                break;
            case 5:
                System.out.print("Enter an integer for factorial: ");
                int factorialNum = scanner.nextInt();
                System.out.println("Factorial: " + factorial(factorialNum));
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Unit Conversions
    public static void unitConversions(Scanner scanner) {
        System.out.println("Choose: 1 for Temperature Conversion, 2 for Distance Conversion, 3 for Weight Conversion");
        int unitChoice = scanner.nextInt();
        
        if (unitChoice == 1) {
            System.out.println("Choose: 1 for Celsius to Fahrenheit, 2 for Fahrenheit to Celsius");
            int tempChoice = scanner.nextInt();
            if (tempChoice == 1) {
                System.out.print("Enter temperature in Celsius: ");
                double celsius = scanner.nextDouble();
                double fahrenheit = (celsius * 9/5) + 32;
                System.out.println("Temperature in Fahrenheit: " + roundToDecimalPlaces(fahrenheit));
            } else if (tempChoice == 2) {
                System.out.print("Enter temperature in Fahrenheit: ");
                double fahrenheit = scanner.nextDouble();
                double celsius = (fahrenheit - 32) * 5/9;
                System.out.println("Temperature in Celsius: " + roundToDecimalPlaces(celsius));
            } else {
                System.out.println("Invalid choice.");
            }
        } else if (unitChoice == 2) {
            System.out.println("Choose: 1 for Kilometers to Miles, 2 for Miles to Kilometers");
            int distanceChoice = scanner.nextInt();
            if (distanceChoice == 1) {
                System.out.print("Enter distance in Kilometers: ");
                double km = scanner.nextDouble();
                double miles = km * 0.621371;
                System.out.println("Distance in Miles: " + roundToDecimalPlaces(miles));
            } else if (distanceChoice == 2) {
                System.out.print("Enter distance in Miles: ");
                double miles = scanner.nextDouble();
                double km = miles / 0.621371;
                System.out.println("Distance in Kilometers: " + roundToDecimalPlaces(km));
            } else {
                System.out.println("Invalid choice.");
            }
        } else if (unitChoice == 3) {
            System.out.println("Choose: 1 for Kilograms to Pounds, 2 for Pounds to Kilograms");
            int weightChoice = scanner.nextInt();
            if (weightChoice == 1) {
                System.out.print("Enter weight in Kilograms: ");
                double kg = scanner.nextDouble();
                double pounds = kg * 2.20462;
                System.out.println("Weight in Pounds: " + roundToDecimalPlaces(pounds));
            } else if (weightChoice == 2) {
                System.out.print("Enter weight in Pounds: ");
                double pounds = scanner.nextDouble();
                double kg = pounds / 2.20462;
                System.out.println("Weight in Kilograms: " + roundToDecimalPlaces(kg));
            } else {
                System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Memory Functions
    public static void memoryFunctions(Scanner scanner) {
        System.out.println("Memory: " + memory);
        System.out.println("Choose: 1 for Add to Memory, 2 for Subtract from Memory, 3 for Clear Memory");
        int memChoice = scanner.nextInt();

        if (memChoice == 1) {
            System.out.print("Enter number to add to memory: ");
            double num = scanner.nextDouble();
            memory += num;
            System.out.println("Memory updated: " + memory);
        } else if (memChoice == 2) {
            System.out.print("Enter number to subtract from memory: ");
            double num = scanner.nextDouble();
            memory -= num;
            System.out.println("Memory updated: " + memory);
        } else if (memChoice == 3) {
            memory = 0;
            System.out.println("Memory cleared.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Show Calculation History
    public static void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No calculations in history.");
        } else {
            System.out.println("Calculation History:");
            for (String entry : history) {
                System.out.println(entry);
            }
        }
    }

    // Simulated Real-time Currency Conversion
    public static void simulatedCurrencyConversion(Scanner scanner) {
        System.out.println("Choose: 1 for USD to INR, 2 for INR to USD");
        int currencyChoice = scanner.nextInt();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        if (currencyChoice == 1) {
            double inrAmount = amount * 75; // Assume exchange rate of 1 USD = 75 INR
            System.out.println("INR Amount: " + roundToDecimalPlaces(inrAmount));
        } else if (currencyChoice == 2) {
            double usdAmount = amount / 75; // Assume exchange rate of 1 USD = 75 INR
            System.out.println("USD Amount: " + roundToDecimalPlaces(usdAmount));
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Helper Methods
    public static void addToHistory(String entry) {
        history.add(entry);
    }

    public static void changeDecimalPrecision(Scanner scanner) {
        System.out.print("Enter desired decimal places: ");
        int places = scanner.nextInt();
        if (places >= 0) {
            decimalPlaces = places;
            System.out.println("Decimal precision updated to " + decimalPlaces + " places.");
        } else {
            System.out.println("Invalid decimal places. Must be non-negative.");
        }
    }

    public static double roundToDecimalPlaces(double value) {
        double factor = Math.pow(10, decimalPlaces);
        return Math.round(value * factor) / factor;
    }

    public static int factorial(int n) {
        if (n < 0) return -1;
        if (n == 0 || n == 1) return 1;
        return n * factorial(n - 1);
    }
}
