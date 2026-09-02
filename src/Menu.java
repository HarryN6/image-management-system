import java.util.Scanner;

public class Menu {
    private String items[];
    private String title;
    private Scanner input;
    static final String PROMPT = "---->";
    static final String SPACES = "     ";

    public Menu(String title, String[] data) {
        this.title = title;
        this.items = data;
        this.input = new Scanner(System.in);
    }

    private void display() {
        System.out.println(title);
        for (int count = 0; count < title.length(); count++) {
            System.out.print("+");
        }
        System.out.println();
        for (int option = 1; option <= items.length; option++) {
            System.out.println(option + ". " + items[option - 1]);
        }
        System.out.println();
    }

    /**
     * Displays the menu and prompts the user to enter a selection. Input is
     * repeatedly requested until a valid integer is entered. If invalid input is
     * entered, an error message is shown.
     *
     * @return the validated integer menu choice entered by the user
     */
    public int getUserChoice() {
        display();
        while (true) {
            try {
                System.out.print(PROMPT + "Enter Selection: ");
                int choice = Integer.parseInt(input.nextLine().trim());
                if (choice >= 1 && choice <= items.length) {
                    System.out.println();
                    return choice;
                }
                System.out.println(SPACES + "Input a value between 1 and "+items.length+".");
                System.out.println();
            } catch (Exception e) {
                System.out.println(SPACES + "Invalid input.");
                System.out.println();
            }
        }
    }
}
