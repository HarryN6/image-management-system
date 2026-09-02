import java.time.LocalDate;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;

import console.Console;

public class ConsoleQUBImages {
	static Console conInterface = new Console(true);
	static Console conImages = new Console(false);
	static Scanner input = new Scanner(System.in);
	static ImageManager imageManager = new ImageManager();

	static String interFaceTitle = "QUB Images";
	static String imageOutputTitle = "Image Console\n++++++++++++\n";
	static final String PROMPT = "---->";
	static final String SPACES = "     ";

	static final String[] initialMenuOptions = {"Add Image", "Search Image", "Display All", "Scroll Album", "Exit"};
	static final String searchOptions[] = { "Search by ID", "Search by Title", "Search by Description",
			"Search by Genre", "Search by Date", "Back to Menu" };
	static final String scrollOptions[] = { "Next", "Previous", "Back to Menu" };
	static final String searchByImageType[] = { "Astronomy", "Architecture", "Sport", "Landscape", "Portrait", "Nature",
			"Aerial", "Food", "Other", "Back to Menu"};
	static final String selectImageTypeOptions[] = { "Astronomy", "Architecture", "Sport", "Landscape", "Portrait", "Nature",
			"Aerial", "Food", "Other"};

	static ConsoleMenu initialMenu = new ConsoleMenu(interFaceTitle, initialMenuOptions, conInterface);


	static final int QUIT = initialMenuOptions.length;
	static ConsoleMenu myMenu = new ConsoleMenu(interFaceTitle, initialMenuOptions, conInterface);
	static ConsoleMenu searchMenu = new ConsoleMenu("OK - Select Search option", searchOptions, conInterface);
	static ConsoleMenu scrollMenu = new ConsoleMenu("OK - Scrolling through Images", scrollOptions, conInterface);
	static ConsoleMenu searchImageTypeMenu = new ConsoleMenu("OK - Select Image type", searchByImageType, conInterface);
	static ConsoleMenu selectImageTypeMenu = new ConsoleMenu("Select an Image Type", selectImageTypeOptions, conInterface);

	public static void main(String[] args) {
		try {
			preloadImages();
		} catch (Exception e) {
			conInterface.println(SPACES + "Preloaded images failed!\n"+SPACES+e.getMessage());
			conInterface.println();
		}
		while (true) {
			if (!runMedia(conInterface, conImages)) {
				break;
			}
		}
		System.exit(1);
	}

	/**
	 * Sets the default size, colours, and font for the interface console.
	 * 
	 * @param con the console used for user interaction
	 */
	private static void setConInterface(Console con) {
		con.setSize(400, 700);
		con.setVisible(true);
		con.setBgColour(Color.BLACK);
		con.setFont(new Font("Courier", Font.BOLD, 20));
		con.setColour(Color.WHITE);
	}

	/**
	 * Sets the default size, colours, and font for the image console.
	 * 
	 * @param con the console used for displaying images
	 */
	private static void setConImages(Console con) {
		con.setSize(800, 700);
		con.setVisible(true);
		con.setBgColour(Color.BLACK);
		con.setFont(new Font("Courier", Font.BOLD, 20));
		con.setColour(Color.WHITE);
		con.setLocation(400, 0);
	}

	/**
	 * Sets up both consoles and handles the primary loop. When user selects the
	 * QUIT option then runMedia returns false to main which then exits the program.
	 * 
	 * @param conInterface the console used for user interaction
	 * @param conImages    the console used for image output
	 * @return false when the user chooses to quit
	 */
	private static boolean runMedia(Console conInterface, Console conImages) {
		int choice;
		setConInterface(conInterface);
		setConImages(conImages);
		conImages.println(imageOutputTitle);

		do {
			conInterface.clear();
			choice = myMenu.getUserChoice();
			menuOptions(choice);
		} while (choice != QUIT);
		return false;
	}

	/**
	 * Preloads the example Image Records provided in the Assignment Specification
	 */
	private static void preloadImages() throws Exception {
		imageManager.addImage(new ImageRecord("Andromeda Galaxy", "Image of the Andromeda galaxy.", ImageType.ASTRONOMY,
				LocalDate.of(2023, 1, 1), "Andromeda"));
		imageManager.addImage(new ImageRecord("Lanyon QUB", "An image of the QUB Lanyon building.", ImageType.ARCHITECTURE,
				LocalDate.of(2023, 2, 1), "LanyonQUB"));
		imageManager.addImage(new ImageRecord("Kermit Plays Golf", "An image of Kermit the frog playing golf.", ImageType.SPORT,
				LocalDate.of(2023, 3, 1), "KermitGolf"));
		imageManager.addImage(new ImageRecord("Mourne Mountains", "A panoramic view of the Mourne mountains.",
				ImageType.LANDSCAPE, LocalDate.of(2023, 4, 1), "Mournes"));
		imageManager.addImage(new ImageRecord("Homer Simpson", "Homer Simpson- A portrait of the man.", ImageType.PORTRAIT,
				LocalDate.of(2023, 3, 1), "Homer"));
		imageManager.addImage(new ImageRecord("Red Kite", "A Red Kite bird of prey in flight.", ImageType.NATURE,
				LocalDate.of(2023, 4, 1), "RedKite"));
		imageManager.addImage(new ImageRecord("Central Park", "An overhead view of Central Park New York USA.", ImageType.AERIAL,
				LocalDate.of(2023, 5, 1), "CentralPark"));
		imageManager.addImage(new ImageRecord("Apples", "A bunch of apples.", ImageType.FOOD, LocalDate.of(2023, 6, 1),
				"Apples"));
		imageManager.addImage(new ImageRecord("ChatGPT Meme", "A ChatGPT programming meme.", ImageType.OTHER,
				LocalDate.of(2023, 7, 1), "ChatGPT"));
		imageManager.addImage(new ImageRecord("Programming Meme", "A programming meme.", ImageType.OTHER,
				LocalDate.of(2023, 7, 1), "Meme"));
	}

	/**
	 * Handles the users main menu selection by calling the appropriate method.
	 * Option 5 (QUIT) returns control to runMedia, which then ends the main loop.
	 * 
	 * @param choice the menu option selected by the user
	 */
	private static void menuOptions(int choice) {
		switch (choice) {
		case 1:
			addImage();
			break;
		case 2:
			search();
			break;
		case 3:
			displayAll();
			break;
		case 4:
			scrollImage();
			break;
		case 5:
			break;
		default:
			invalidIntErrorMessage(choice);
		}
	}

	/**
	 * Displays a standard error message for invalid int inputs.
	 * 
	 * @param choice the invalid option entered by the user
	 */
	private static void invalidIntErrorMessage(int choice) {
		conInterface.println(PROMPT + "Option " + choice + " is invalid");
		conInterface.println(SPACES + "Press Enter to Continue...");
		conInterface.readLn();
	}

	/**
	 * Displays the result of searches
	 */
	private static void displayAlbumResults(ImageAlbum imageAlbum) {
		ImageRecord current = imageAlbum.getFirst();
		conInterface.println();

		if (current == null) {
			conImages.println("No images found.");
		} else {
			conImages.println("Image(s) found:");

			while (current != null) {
				conImages.println(current);
				conImages.println();

				conImages.println(selectImage(current.getThumbnail()));
				conImages.println();

				current = imageAlbum.getNext();
			}
		}
		conInterface.println();
	}


	/**
	 * Makes use of getNext() and getPrevious() methods from ImageAlbum to allow
	 * user to cycle through the Images sorted by date.
	 */
	private static void scrollImage() {
		conImages.clear();
		conInterface.clear();
		conImages.println(imageOutputTitle);

		ImageAlbum imageAlbum = imageManager.getAllImages();
		ImageRecord current = imageAlbum.getFirst();

		if (current == null) {
			conImages.clear();
			conImages.println(imageOutputTitle);
			conImages.println("No images found.");
			return;
		}

		conImages.println(current);
		conImages.println();

		conImages.println(selectImage(current.getThumbnail()));
		conImages.println();

		while (true) {
			try {
				conInterface.clear();
				int choice = scrollMenu.getUserChoice();
				if (choice == 3) {
					break;
				}

				if (choice == 1) {
					ImageRecord next = imageAlbum.getNext();
					if (next != null) {
						current = next;
						conImages.clear();
						conImages.println(imageOutputTitle);
						conImages.println("OK - Scrolling Forwards");
						conImages.println();

						conImages.println(current);
						conImages.println();

						conImages.println(selectImage(current.getThumbnail()));
						conImages.println();
					} else {
						conImages.clear();
						conImages.println(imageOutputTitle);
						conImages.println("Reached end of list.");
						conImages.println();
					}

				} else if (choice == 2) {
					ImageRecord prev = imageAlbum.getPrevious();
					if (prev != null) {
						current = prev;
						conImages.clear();
						conImages.println(imageOutputTitle);
						conImages.println("OK - Scrolling Backwards");
						conImages.println();

						conImages.println(current);
						conImages.println();

						conImages.println(selectImage(current.getThumbnail()));
						conImages.println();
					} else {
						conImages.clear();
						conImages.println(imageOutputTitle);
						conImages.println("Reached start of list.");
						conImages.println();
					}

				} else {
					invalidIntErrorMessage(choice);
				}

			} catch (Exception ex) {
				conInterface.println(PROMPT + "Input is invalid");
				conInterface.println(SPACES + "Press Enter to Continue...");
				conInterface.readLn();
			}
		}
	}

	/**
	 * Displays all images in order by date along with their thumbnails.
	 */
	private static void displayAll() {
		conImages.clear();
		conImages.println(imageOutputTitle);
		conImages.println("OK - Displaying all Images");
		conImages.println();
		ImageAlbum imageAlbum = imageManager.getAllImages();
		displayAlbumResults(imageAlbum);
	}

	/**
	 * Displays the search menu and calls the appropriate search method based on the
	 * user's selection.
	 */
	private static void search() {
		conInterface.clear();
		int choice = searchMenu.getUserChoice();
		switch (choice) {
		case 1:
			searchById();
			break;
		case 2:
			searchByTitle();
			break;
		case 3:
			searchByDescription();
			break;
		case 4:
			searchByImageType();
			break;
		case 5:
			searchByDates();
			break;
		case 6:
			break;
		default:
			invalidIntErrorMessage(choice);
		}

	}

	/**
	 * Builds the file path for the given thumbnail and returns the ImageIcon
	 * 
	 * @param thumbnail the thumbnail filename without path
	 * @return an ImageIcon created from the resolved file path
	 */
	private static ImageIcon selectImage(String thumbnail) {
		String userdir = System.getProperty("user.dir");
		String path = userdir + "/src/Images/" + thumbnail;
		return new ImageIcon(path);
	}

	/**
	 * Prompts the user for an image ID, validates the input and displays the
	 * matching image record and thumbnail if found.
	 */
	private static void searchById() {
		int valueAsInt;
		while (true) {
			try {
				conInterface.print(PROMPT + "Input ID: ");
				String value = conInterface.readLn().trim();
				valueAsInt = Integer.parseInt(value);
				break;
			} catch (NumberFormatException e) {
				conInterface.println(SPACES + "Invalid input.");
				conInterface.println();
			}
		}
		conInterface.println();
		conImages.clear();
		conImages.println(imageOutputTitle);
		ImageRecord result = imageManager.searchId(valueAsInt);
		if (result == null) {
			conImages.println("No Image found with ID: " + valueAsInt);
			conImages.println();
		} else {
			conImages.println("Image found:\n" + result);
			conImages.println();

			conImages.println(selectImage(result.getThumbnail()));
		}
		conImages.println();
	}

	/**
	 * Prompts the user for an image description, validates the input and displays
	 * images with occurrences of keywords and their thumbnail if found.
	 */
	private static void searchByDescription() {
		String inputDesc = validateString(PROMPT + "Input Description: ");
		try {
			ImageAlbum imageAlbum = imageManager.searchDescription(inputDesc);
			conImages.clear();
			conImages.println(imageOutputTitle);
			displayAlbumResults(imageAlbum);
		} catch (Exception e) {
			conInterface.println(SPACES + e.getMessage());
			conInterface.println();
		}
	}

	/**
	 * Prompts the user for a start and end date, validates both inputs and displays
	 * images whose dates fall within the inclusive range and their thumbnail if
	 * found.
	 */
	private static void searchByDates() {
		LocalDate start, end;

		while (true) {
			try {
				conInterface.print(PROMPT + "Input Start date (YYYY-MM-DD): ");
				start = LocalDate.parse(conInterface.readLn().trim());
				break;
			} catch (Exception e) {
				conInterface.println(SPACES + "Invalid input - date must be formatted as YYYY-MM-DD and be a valid date.");
				conInterface.println(SPACES + "Press Enter to Continue...");
				conInterface.readLn();
			}
		}

		while (true) {
			try {
				conInterface.print(PROMPT + "Input End date (YYYY-MM-DD): ");
				end = LocalDate.parse(conInterface.readLn().trim());
				while (end.isBefore(start)) {
					conInterface.println(SPACES + "Invalid input - date must be after " + start + ".");
					conInterface.print(PROMPT + "Input End date (YYYY-MM-DD): ");
					end = LocalDate.parse(conInterface.readLn());
				}
				break;
			} catch (Exception ex) {
				conInterface.println(SPACES + "Invalid input - date must be formatted as YYYY-MM-DD and be a valid date.");
				conInterface.println(SPACES + "Press Enter to Continue...");
				conInterface.readLn();
			}
		}

		try {
			ImageAlbum imageAlbum = imageManager.searchDates(start, end);
			conImages.clear();
			conImages.println(imageOutputTitle);
			displayAlbumResults(imageAlbum);
		} catch (Exception e) {
			conInterface.println(SPACES + e.getMessage());
			conInterface.println();
		}
	}

	/**
	 * Prompts the user for an image title, validates the input and displays images
	 * with titles containing the inputted string and their thumbnail if found.
	 */
	private static void searchByTitle() {
		String inputTitle = validateString(PROMPT + "Input Title: ");
		try {
			ImageAlbum imageAlbum = imageManager.searchTitle(inputTitle);
			conImages.clear();
			conImages.println(imageOutputTitle);
			displayAlbumResults(imageAlbum);
		} catch (Exception e) {
			conInterface.println(SPACES + e.getMessage());
			conInterface.println();
		}
	}

	/**
	 * Displays a menu of image types for the user to select, validates the
	 * selection and displays all images of the chosen type and their thumbnail.
	 */
	private static void searchByImageType() {
		conInterface.clear();
		int choice = searchImageTypeMenu.getUserChoice();

		while (choice < 1 || choice > searchByImageType.length) {
			invalidIntErrorMessage(choice);
			conInterface.clear();
			choice = searchImageTypeMenu.getUserChoice();
		}

		if (choice == searchByImageType.length) {
			return;
		}

		ImageType type = ImageType.values()[choice - 1];
		ImageAlbum imageAlbum = imageManager.searchGenre(type);
		conImages.clear();
		conImages.println(imageOutputTitle);
		displayAlbumResults(imageAlbum);
	}

	/**
	 * Prompts the user for all fields required to create a new image record,
	 * validates each input and adds the completed ImageRecord to the Master
	 * ArrayList in ImageManager.
	 */
	private static void addImage() {
		conInterface.println("OK - Add a new Image.");
		String title = validateString(PROMPT + "Enter Title: ");
		String description = validateString(PROMPT + "Enter a description: ");

		LocalDate date;
		while (true) {
			conInterface.print(PROMPT + "Enter date (YYYY-MM-DD): ");
			try {
				date = LocalDate.parse(conInterface.readLn());
				break;
			} catch (Exception e) {
				conInterface.println(SPACES + "Invalid input - date must be formatted as YYYY-MM-DD and a valid date.");
				conInterface.println(SPACES + "Press Enter to Continue...");
				conInterface.readLn();
			}
		}

		conInterface.println();
		ImageType imgType = ImageType.values()[selectImageTypeMenu.getUserChoice() - 1];
		conInterface.println();

		String thumbnail = validateString(PROMPT + "Enter Thumbnail name: ");
		while (thumbnail.contains(".")) {
			thumbnail = validateString(SPACES + "Invalid input - Enter Thumbnail name: ");
		}
		conInterface.println();

		try {
			ImageRecord image = new ImageRecord(title, description, imgType, date, thumbnail);
			imageManager.addImage(image);
			conInterface.println("Image added successfully.");
			conInterface.println(SPACES + "Press Enter to Continue...");
			conInterface.readLn();
		} catch (Exception e) {
			conInterface.println(SPACES + "Unable to add image: " + e.getMessage());
			conInterface.println(SPACES + "Press Enter to Continue...");
			conInterface.readLn();
		}
	}

	/**
	 * Prompts the user with the given message until a non-empty string is entered.
	 *
	 * @param strPrompt the prompt shown to the user
	 * @return a validated, non-empty string
	 */
	private static String validateString(String strPrompt) {
		while (true) {
			conInterface.print(strPrompt);
			String str = conInterface.readLn().trim();
			if (!str.isBlank()) {
				conInterface.println();
				return str;
			}
			conInterface.println(SPACES + "Invalid input.");
			conInterface.println(SPACES + "Press Enter to Continue...");
			conInterface.readLn();
		}
	}

	/**
	 * Prompts the user to enter an image type and validates the input against the
	 * ImageType enumeration. Repeats until a valid type is entered.
	 *
	 * @return the selected ImageType
	 */
	private static ImageType readImageType() {
		while (true) {
			conInterface.print(PROMPT
					+ "Enter Image Type (Astronomy, Architecture, Sport, Landscape, Portrait, Nature, Aerial, Food, Other): ");
			String imgType = conInterface.readLn().trim().toUpperCase();
			try {
				return ImageType.valueOf(imgType);
			} catch (Exception ex) {
				conInterface.println(SPACES + "Invalid input.");
			}
		}
	}
}
