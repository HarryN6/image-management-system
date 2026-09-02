import java.time.LocalDate;
import java.util.Scanner;

public class QUBImages {

    static Scanner input = new Scanner(System.in);
    static ImageManager imageManager = new ImageManager();

    static String title = "QUB Images";
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

    static Menu initialMenu = new Menu(title, initialMenuOptions);
    static Menu searchMenu = new Menu("OK - Select Search option", searchOptions);
    static Menu scrollMenu = new Menu("OK - Scrolling through Images", scrollOptions);
    static Menu searchImageTypeMenu = new Menu("OK - Select Image type", searchByImageType);
    static Menu selectImageTypeMenu = new Menu("Select an Image Type", selectImageTypeOptions);

    static final int QUIT = initialMenuOptions.length;

    public static void main(String[] args) {
        try {
            preloadImages();
        } catch (Exception e) {
            System.out.println(SPACES + "Preloaded images failed!\n"+SPACES+e.getMessage());
            System.out.println();
        }

        int choice;
        do {
            choice = initialMenu.getUserChoice();
            initialMenuChoice(choice);
        } while (choice != QUIT);
        System.out.println("Goodbye!");
        input.close();
    }

    /**
     * Handles the users main menu selection by calling the appropriate method.
     * Option 5 (QUIT) returns control to main, which then ends the main loop.
     *
     * @param choice the menu option selected by the user
     */
    private static void initialMenuChoice(int choice) {
        switch (choice) {
            case 1:
                addImage();
                break;
            case 2:
                searchImage();
                break;
            case 3:
                displayAll();
                break;
            case 4:
                scrollAlbum();
                break;
            case 5:
                break;
            default:
                System.out.println(SPACES + "Invalid input.");
                System.out.println();
                break;
        }
    }

    /**
     * Displays the result of searches
     */
    private static void displayAlbumResults(ImageAlbum imageAlbum) {
        ImageRecord current = imageAlbum.getFirst();
        System.out.println();

        if (current == null) {
            System.out.println(SPACES + "No images found.");
        } else {
            System.out.println(SPACES + "Image(s) found:");

            while (current != null) {
                System.out.println(current);
                current = imageAlbum.getNext();
            }
        }
        System.out.println();
    }

    /**
     * Displays the search menu and calls the appropriate search method based on the
     * users selection.
     */
    private static void searchImage() {
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
                System.out.println(SPACES + "Option " + choice + " is invalid");
                System.out.println();
                break;
        }
    }

    /**
     * Prompts the user for an image ID, validates the input and displays the
     * matching image record and thumbnail if found.
     */
    private static void searchById() {
        int valueAsInt;
        while (true) {
            try {
                System.out.print(PROMPT + "Input ID: ");
                String value = input.nextLine().trim();
                valueAsInt = Integer.parseInt(value);
                break;
            } catch (NumberFormatException e) {
                System.out.println(SPACES + "Invalid input - ID must be a number.");
                System.out.println();
            }
        }
        System.out.println();
        ImageRecord result = imageManager.searchId(valueAsInt);
        if (result == null) {
            System.out.println("No image found with ID: " + valueAsInt + ".");
        } else {
            System.out.println("Image found:\n" + result);
        }

        System.out.println();
    }

    /**
     * Prompts the user for an image description, validates the input and displays
     * images with occurrences of keywords.
     */
    private static void searchByDescription() {
        String inputDesc = validateString(PROMPT + "Input Description: ");

        try {
            ImageAlbum imageAlbum = imageManager.searchDescription(inputDesc);
            displayAlbumResults(imageAlbum);
        } catch (Exception e) {
            System.out.println(SPACES + e.getMessage());
            System.out.println();
        }
    }

    /**
     * Prompts the user for a start and end date, validates both inputs and displays
     * images whose dates fall within the inclusive range.
     */
    private static void searchByDates() {
        LocalDate start, end;

        while (true) {
            try {
                System.out.print(PROMPT + "Input Start date (YYYY-MM-DD): ");
                start = LocalDate.parse(input.nextLine().trim());
                System.out.println();
                break;
            } catch (Exception e) {
                System.out.println(SPACES + "Invalid input - date must be formatted as YYYY-MM-DD and be a valid date.");
                System.out.println();
            }
        }

        while (true) {
            try {
                System.out.print(PROMPT + "Input end date (YYYY-MM-DD): ");
                end = LocalDate.parse(input.nextLine().trim());
                while (end.isBefore(start)) {
                    System.out.println(SPACES + "Invalid input - end date cannot be before " + start + ".");
                    System.out.println();
                    System.out.print(PROMPT + "Input end date (YYYY-MM-DD): ");
                    end = LocalDate.parse(input.nextLine());
                }
                break;
            } catch (Exception e) {
                System.out.println(SPACES + "Invalid input - date must be formatted as YYYY-MM-DD and be a valid date.");
                System.out.println();
            }
        }

        try {
            ImageAlbum imageAlbum = imageManager.searchDates(start, end);
            displayAlbumResults(imageAlbum);
        } catch (Exception e) {
            System.out.println(SPACES + e.getMessage());
        }
    }

    /**
     * Prompts the user for an image title, validates the input and displays images
     * with titles containing the inputted string.
     */
    private static void searchByTitle() {
        String inputTitle = validateString(PROMPT + "Input Title: ");

        try {
            ImageAlbum imageAlbum = imageManager.searchTitle(inputTitle);
            displayAlbumResults(imageAlbum);
        } catch (Exception e) {
            System.out.println(SPACES + e.getMessage());
            System.out.println();
        }
    }

    /**
     * Displays a menu of image types for the user to select, validates the
     * selection and displays all images of the chosen type.
     */
    private static void searchByImageType() {
        int choice = searchImageTypeMenu.getUserChoice();

        if (choice == searchByImageType.length) {
            return;
        }

        ImageType type = ImageType.values()[choice - 1];
        ImageAlbum imageAlbum = imageManager.searchGenre(type);
        displayAlbumResults(imageAlbum);
    }

    /**
     * Makes use of getNext() and getPrevious() methods from ImageAlbum to allow
     * user to cycle through the Images sorted by date.
     */
    private static void scrollAlbum() {
        ImageAlbum imageAlbum = imageManager.getAllImages();
        ImageRecord current = imageAlbum.getFirst();

        if (current == null) {
            System.out.println(SPACES + "No images found.");
            System.out.println();
            return;
        }
        System.out.println(current);
        System.out.println();

        while (true) {
            try {
                int choice = scrollMenu.getUserChoice();
                if (choice == 3) {
                    break;
                }

                if (choice == 1) {
                    ImageRecord next = imageAlbum.getNext();
                    if (next != null) {
                        current = next;
                        System.out.println("OK - Scrolling forwards");
                        System.out.println();
                        System.out.println(current);
                        System.out.println();
                    } else {
                        System.out.println(SPACES + "Reached end of list.");
                        System.out.println();
                    }

                } else if (choice == 2) {
                    ImageRecord prev = imageAlbum.getPrevious();
                    if (prev != null) {
                        current = prev;
                        System.out.println("OK - Scrolling backwards");
                        System.out.println();
                        System.out.println(current);
                        System.out.println();
                    } else {
                        System.out.println(SPACES + "Reached start of list.");
                        System.out.println();
                    }
                }
            } catch (Exception e) {
                System.out.println(SPACES + "Input is invalid.");
            }
        }
    }

    /**
     * Displays all images in order by date.
     */
    private static void displayAll() {
        System.out.println("OK - Displaying all images");
        ImageAlbum imageAlbum = imageManager.getAllImages();
        displayAlbumResults(imageAlbum);
    }

    /**
     * Prompts the user for all fields required to create a new image record,
     * validates each input and adds the completed ImageRecord to the Master
     * ArrayList in ImageManager.
     */
    private static void addImage() {
        System.out.println("OK - Add a new Image.");
        String title = validateString(PROMPT + "Enter Title: ");
        System.out.println();
        String description = validateString(PROMPT + "Enter a description: ");
        System.out.println();

        LocalDate date;
        while (true) {
            System.out.print(PROMPT + "Enter date (YYYY-MM-DD): ");
            try {
                date = LocalDate.parse(input.nextLine());
                System.out.println();
                break;
            } catch (Exception e) {
                System.out.println(SPACES + "Invalid input - date must be formatted as YYYY-MM-DD and a valid date.");
                System.out.println();
            }
        }

        ImageType imgType = ImageType.values()[selectImageTypeMenu.getUserChoice() - 1];

        String thumbnail = validateString(PROMPT + "Enter Thumbnail name: ");
        while (thumbnail.contains(".")) {
            System.out.println(SPACES + "Invalid input - thumbnail name must not contain an extension.");
            System.out.println();
            thumbnail = validateString(PROMPT + "Enter Thumbnail name: ");
        }
        System.out.println();

        try {
            ImageRecord image = new ImageRecord(title, description, imgType, date, thumbnail);
            imageManager.addImage(image);
            System.out.println("Image added successfully.");
            System.out.println();
        } catch (Exception e) {
            System.out.println(SPACES + "Unable to add image: " + e.getMessage());
            System.out.println();
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
            System.out.print(strPrompt);
            String str = input.nextLine().trim();
            if (!str.isBlank()) {
                return str;
            }
            System.out.println(SPACES + "Invalid input.");
            System.out.println();
        }
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
}
