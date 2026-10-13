import java.util.Random;
import java.util.Scanner;

public class Bsit12LucenaShainaModule1CaseStudy2 {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        Random randomValue = new Random();

        // Leaderboard storage
        String[] topDealerNames = new String[10];
        double[] topDealerFunds = new double[10];

        String pad = "                                   ";

        while (true) {
            System.out.println("\n\n" + pad + "========================================");
            System.out.println(pad + "         WELCOME TO LUCKY 9!            ");
            System.out.println(pad + "========================================");
            System.out.println(pad + "          [1] Start Game                ");
            System.out.println(pad + "          [2] View Leaderboard          ");
            System.out.println(pad + "          [3] Exit                      ");
            System.out.print(pad + "          Select action: ");
            
            if (!userInput.hasNextInt()) {
                userInput.next();
                continue;
            }
            int userChoice = userInput.nextInt();
            userInput.nextLine(); 

            switch (userChoice) {
                case 1:
                    System.out.print("\n" + pad + "Enter Dealer Name: ");
                    String dealerName = userInput.nextLine();
                    System.out.print(pad + "Enter Cash Fund: $");
                    double cashFund = userInput.nextDouble();
                    System.out.print(pad + "Number of Players (1-20): ");
                    int numPlayers = userInput.nextInt();

                    if (numPlayers < 1 || numPlayers > 20) {
                        System.out.println(pad + "Invalid number of players!");
                        break;
                    }

                    int[] playerBets = new int[numPlayers];
                    for (int i = 0; i < numPlayers; i++) {
                        playerBets[i] = randomValue.nextInt(81) + 20;
                        System.out.println(pad + "Player " + (i + 1) + " bet: $" + playerBets[i]);
                    }

                    boolean sessionActive = true;
                    while (sessionActive) {
                        int dealerTotal = 0;
                        String[] suits = {"Spades", "Diamonds", "Hearts", "Clubs"};

                        System.out.println("\n" + pad + "--- DEALER'S TURN ---");
                        // Initial 2 cards for Dealer
                        for (int i = 1; i <= 2; i++) {
                            int val = randomValue.nextInt(13) + 1;
                            dealerTotal += (val >= 10) ? 0 : val;
                            System.out.println(pad + "Card " + i + ": " + getCardName(val) + " of " + suits[randomValue.nextInt(4)]);
                        }

                        System.out.print(pad + "Draw 3rd card? [Y/N]: ");
                        if (userInput.next().equalsIgnoreCase("Y")) {
                            int val = randomValue.nextInt(13) + 1;
                            dealerTotal += (val >= 10) ? 0 : val;
                            System.out.println(pad + "3rd Card: " + getCardName(val));
                        }
                        int dealerFinal = dealerTotal % 10;
                        System.out.println(pad + "Dealer Value: " + dealerFinal + (dealerFinal == 9 ? " (LUCKY 9!)" : ""));

                        // --- PLAYERS' TURN ---
                        int[] playerValues = new int[numPlayers];
                        System.out.println("\n" + pad + "--- PLAYERS' REVEAL ---");
                        for (int i = 0; i < numPlayers; i++) {
                            int pTotal = 0;
                            for (int c = 0; c < 2; c++) {
                                int val = randomValue.nextInt(13) + 1;
                                pTotal += (val >= 10) ? 0 : val;
                            }
                            if ((pTotal % 10) < 7) {
                                int val = randomValue.nextInt(13) + 1;
                                pTotal += (val >= 10) ? 0 : val;
                            }
                            playerValues[i] = pTotal % 10;
                            System.out.println(pad + "Player " + (i + 1) + " Value: " + playerValues[i]);
                        }

                        double roundWin = 0;
                        double roundLoss = 0;
                        System.out.println("\n" + pad + "--- ROUND RESULTS ---");
                        for (int i = 0; i < numPlayers; i++) {
                            if (playerValues[i] > dealerFinal) {
                                double winAmt = (playerValues[i] == 9) ? playerBets[i] * 3 : playerBets[i] * 2;
                                roundLoss += winAmt;
                                System.out.println(pad + "Player " + (i + 1) + " WON: $" + winAmt);
                            } else if (playerValues[i] < dealerFinal) {
                                roundWin += playerBets[i];
                                System.out.println(pad + "Player " + (i + 1) + " LOST");
                            } else {
                                System.out.println(pad + "Player " + (i + 1) + " TIED");
                            }
                        }

                        cashFund = (cashFund + roundWin) - roundLoss;
                        System.out.println("\n" + pad + "Dealer Result: +$" + roundWin + " | -$" + roundLoss);
                        System.out.println(pad + "Current Fund: $" + cashFund);

                        System.out.print("\n" + pad + "[1] Play Again [2] Save & Quit: ");
                        if (userInput.nextInt() != 1) {
                            // Save to leaderboard logic
                            updateLeaderboard(topDealerNames, topDealerFunds, dealerName, cashFund);
                            sessionActive = false;
                        }
                    }
                    break;

                case 2:
                    System.out.println("\n" + pad + "======= LEADERBOARD =======");
                    for (int i = 0; i < 10; i++) {
                        if (topDealerNames[i] != null) {
                            System.out.println(pad + (i + 1) + ". " + topDealerNames[i] + " - $" + topDealerFunds[i]);
                        } else {
                            System.out.println(pad + (i + 1) + ". ---");
                        }
                    }
                    System.out.println(pad + "===========================");
                    break;

                case 3:
                    System.out.println(pad + "Goodbye!");
                    System.exit(0);
                default:
                    System.out.println(pad + "Invalid Input!");
            }
        }
    }

    // Helper method para sa card names
    public static String getCardName(int val) {
        if (val == 1) return "A";
        if (val == 11) return "J";
        if (val == 12) return "Q";
        if (val == 13) return "K";
        return String.valueOf(val);
    }

    // Leaderboard update logic
    public static void updateLeaderboard(String[] names, double[] funds, String name, double fund) {
        for (int i = 0; i < 10; i++) {
            if (fund > funds[i]) {
                for (int j = 9; j > i; j--) {
                    funds[j] = funds[j - 1];
                    names[j] = names[j - 1];
                }
                funds[i] = fund;
                names[i] = name;
                break;
            }
        }
    }
}