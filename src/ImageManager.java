import java.time.LocalDate;
import java.util.ArrayList;

public class ImageManager {

    private ArrayList<ImageRecord> images;

    /**
     * Creates an empty ImageManager with no stored images.
     */
    public ImageManager() {
        this.images = new ArrayList<ImageRecord>();
    }

    /**
     * Adds a new ImageRecord to the collection.
     *
     * @param image the ImageRecord to add
     */
    public void addImage(ImageRecord image) {
        this.images.add(image);
    }

    /**
     * Searches for an image with the given ID.
     *
     * @param id the ID to search for
     * @return the matching ImageRecord, or null if no match is found
     */
    public ImageRecord searchId(int id) {
        for (ImageRecord img : images) {
            if (img.getImageId() == id) {
                return img;
            }
        }
        return null;
    }

    /**
     * Searches for images whose titles contain the given text (case-insensitive).
     *
     * @param str the title text to search for
     * @return an ImageAlbum containing all matching images
     */
    public ImageAlbum searchTitle(String str) throws Exception {
        ArrayList<ImageRecord> list = new ArrayList<>();
        if (str == null || str.trim().isEmpty()) {
            throw new Exception("Search text cannot be empty.");
        }
        String toSearch = str.trim().toLowerCase();

        for (ImageRecord img : images) {
            if (img.getTitle().trim().toLowerCase().contains(toSearch)) {
                list.add(img);
            }
        }
        return new ImageAlbum(list);
    }

    /**
     * Searches for images whose descriptions contain any of the given keywords.
     *
     * @param str the description text or keywords to search for
     * @return an ImageAlbum containing all matching images
     */
    public ImageAlbum searchDescription(String str) throws Exception {
        ArrayList<ImageRecord> list = new ArrayList<>();
        if (str == null || str.trim().isEmpty()) {
            throw new Exception("Search text cannot be empty.");
        }
        String[] wordsToSearch = str.toLowerCase().trim().split(" ");

        for (ImageRecord img : images) {
            String desc = img.getDesc().trim().toLowerCase();
            for (String toSearch : wordsToSearch) {
                if (desc.contains(toSearch.trim().toLowerCase())) {
                    list.add(img);
                    break;
                }
            }
        }
        return new ImageAlbum(list);
    }

    /**
     * Searches for images that match the given ImageType.
     *
     * @param type the ImageType to search for
     * @return an ImageAlbum containing all matching images
     */
    public ImageAlbum searchGenre(ImageType type) {
        ArrayList<ImageRecord> list = new ArrayList<>();

        for (ImageRecord img : images) {
            if (img.getImgType() == type) {
                list.add(img);
            }
        }

        return new ImageAlbum(list);
    }

    /**
     * Searches for images whose dates fall within the given inclusive range.
     *
     * @param start the start date
     * @param end   the end date
     * @return an ImageAlbum containing all matching images
     */
    public ImageAlbum searchDates(LocalDate start, LocalDate end) throws Exception {
        ArrayList<ImageRecord> list = new ArrayList<>();
        if (start == null || end == null) {
            throw new Exception("Dates cannot be null.");
        }

        if (start.isAfter(end)) {
            throw new Exception("Start date must not be after end date.");
        }

        for (ImageRecord img : images) {
            if(!img.getDateTaken().isBefore(start) && !img.getDateTaken().isAfter(end)) {
                list.add(img);
            }
        }

        return new ImageAlbum(list);
    }

    /**
     * Returns all stored images as a new ImageAlbum sorted by date.
     *
     * @return an ImageAlbum containing all images
     */
    public ImageAlbum getAllImages() {
        ArrayList<ImageRecord> list = new ArrayList<>();
        for (ImageRecord img : images) {
            if (images.size() > 0) {
                list.add(img);
            }
        }
        return new ImageAlbum(list);
    }

}
