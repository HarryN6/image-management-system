import console.Console;

public class ConsoleMenu {
	private String items[];
	private String title;
	private Console conInterface = new Console(true);

	public ConsoleMenu(String title, String data[], Console con) {
		this.title = title;
		this.items = data;
		this.conInterface = con;
	}

	private void display() {
		conInterface.println(title);
		for (int count = 0; count < title.length(); count++) {
			conInterface.print("+");
		}
		conInterface.println();
		for (int option = 1; option <= items.length; option++) {
			conInterface.println(option + ". " + items[option - 1]);
		}
		conInterface.println();
	}

	/**
	 * Displays the menu and prompts the user to enter a selection. Input is
	 * repeatedly requested until a valid integer is entered. If invalid input is
	 * entered, an error message is shown and the menu is redisplayed.
	 *
	 * @return the validated integer menu choice entered by the user
	 */
	public int getUserChoice() {
		display();
		int valueAsInt;
		while (true) {
			try {
				conInterface.print("Enter Selection: ");
				String value = conInterface.readLn().trim();
				valueAsInt = Integer.parseInt(value);
				break;
			} catch (Exception ex) {
				conInterface.println("---->Input is invalid");
				conInterface.println("       Press Enter to Continue...");
				conInterface.readLn();
				conInterface.clear();
				display();

			}
		}
		return valueAsInt;
	}
}
