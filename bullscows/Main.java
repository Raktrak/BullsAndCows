package bullscows;


import java.util.*;


public class Main {
    static String answer;
    static Scanner scanner = new Scanner(System.in);
    final static StringBuilder SECRET_CODE = new StringBuilder();
    static int bulls, cows;
    static int turn = 1;


    public static void main(String[] args) {
        randomGenerator();
        grader();
    }


    public static void turns() {
        System.out.printf("Turn %d:\n", turn);
        turn++;
    }

    public static void randomGenerator() {
        List<String> randomList = new ArrayList<>
                (List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                        "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));

        System.out.println("Please, enter the secret code's length:");
        try {
            int length = scanner.nextInt();

            if (!String.valueOf(length).matches("[0-9]+")) {
                System.out.printf("Error: \"%d\" isn't a valid number.\n", length);
                System.exit(0);
            }
            System.out.println("Input the number of possible symbols in the code:");
            int range = scanner.nextInt();
            if (length > range || length == 0) {
                System.out.printf("""
                                Error: it's not possible to generate a
                                code with a length of %d with %d unique symbols."""
                        , length, range);
                System.exit(0);
            }
            if (range > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(0);
            }

            randomList.subList(0, range);

            List<String> selectedList = randomList.subList(0, range);
            String firstSymbol = selectedList.get(0);
            String lastSymbol = selectedList.get(range - 1);

            String rangeDescription = firstSymbol + "-" + lastSymbol;

            Collections.shuffle(selectedList);
            for (var ch : randomList.subList(0, length)) {
                SECRET_CODE.append(ch);
            }
            String star = "*".repeat(length);

            System.out.printf("The secret is prepared: %s (%s).\n", star, rangeDescription);
            System.out.println("Okay, let's start a game!");
        } catch (InputMismatchException e) {
            System.out.printf("Error " + e.getMessage());
            System.exit(0);
        }
    }


    private static void bulls() {
        for (int i = 0; i < answer.length(); i++) {
            if (String.valueOf(answer.charAt(i)).matches(String.valueOf(SECRET_CODE.charAt(i)))) {
                bulls++;
            }
        }
    }

    private static void cows() {
        for (int i = 0; i < answer.length(); i++) {
            for (int j = 0; j < answer.length(); j++) {
                if (String.valueOf(answer.charAt(i)).matches(String.valueOf(SECRET_CODE.charAt(j)))) {
                    cows++;
                }
            }
        }
    }

    private static void grader() {
        StringBuilder sbCows = new StringBuilder("cow");
        StringBuilder sbBulls = new StringBuilder("bull");
        do {
            turns();
            answer = scanner.next();
            bulls();
            cows();
            cows -= bulls;

            if (cows > 1) {
                sbCows.append("s");
            }
            if (bulls > 1) {
                sbBulls.append("s");
            }

            if (bulls == SECRET_CODE.length()) {
                System.out.printf("""
                        Grade: %d %s
                        Congratulations! You guessed the secret code.
                        """, bulls, sbBulls);
                break;
            } else if (cows > 0 && bulls > 0) {
                System.out.printf("Grade: %d %s and %d %s.", bulls, sbBulls, cows, sbCows);
            } else if (cows > 0) {
                System.out.printf("Grade: %d %s.", cows, sbCows);
            } else if (bulls > 0) {
                System.out.printf("Grade: %d %s.", bulls, sbBulls);
            } else {
                System.out.println("Grade: None");
            }
            bulls = 0;
            cows = 0;
        } while (bulls != SECRET_CODE.length());

    }
}


