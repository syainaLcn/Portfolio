import java.util.Random;
import java.util.Scanner;

public class BSIT12LucenaShainaModule1CaseStudy1 {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        Random random = new Random();

        int maxRange = 50;
        int targetNumber = random.nextInt(maxRange) + 1;
        int attempts = 1;

        String leftPadding = "                                        "; 

        System.out.println("\n\n"); 
        System.out.println(leftPadding + "==================================================");
        System.out.println(leftPadding + "        WELCOME TO THE GUESSING NUMBER GAME!      ");
        System.out.println(leftPadding + "==================================================");
        System.out.println(leftPadding + "           Guess the number from 1 to " + maxRange + "          ");
        System.out.println(leftPadding + "--------------------------------------------------");
        System.out.println();

        while (true) {
            // Centered input prompt
            System.out.print(leftPadding + "    Enter your guess (Attempt #" + attempts + "): ");

            if (!userInput.hasNextInt()) {
                System.out.println(leftPadding + "    [!] Invalid. Please enter an integer.");
                userInput.next(); 
                continue;
            }

            int userGuess = userInput.nextInt();

            if (userGuess < 1 || userGuess > maxRange) {
                System.out.println(leftPadding + "    [!] Out of range (1-" + maxRange + ") only.");
                continue;
            }

            // Feedback logic
            if (userGuess == targetNumber) {
                System.out.println("\n" + leftPadding + "==================================================");
                System.out.println(leftPadding + "      	 CONGRATULATIONS! YOU GOT IT RIGHT!      ");
                System.out.println(leftPadding + "          Total attempts made: " + attempts);
                System.out.println(leftPadding + "==================================================");
                break;
            } else if (userGuess > targetNumber) {
                System.out.println(leftPadding + "    >> HINT: It's LOWER than " + userGuess);
            } else {
                System.out.println(leftPadding + "    >> HINT: It's HIGHER than " + userGuess);
            }

            attempts++;
            System.out.println(leftPadding + "--------------------------------------------------");
        }

        userInput.close();
        System.out.println("\n" + leftPadding + "             Thank you for playing!               \n\n");
    }
}