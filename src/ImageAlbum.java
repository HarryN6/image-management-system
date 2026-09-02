import java.util.ArrayList;

public class ImageAlbum {

    private ArrayList<ImageRecord> images = new ArrayList<>();
    private int currentIndex;

    /**
     * Creates a new ImageAlbum containing the given list of images. The images are
     * copied into an internal list and sorted by date. currentIndex is set to its
     * default value.
     *
     * @param list the list of ImageRecord objects to include in the album
     */
    public ImageAlbum(ArrayList<ImageRecord> list) {
        for (ImageRecord img : list) {
            images.add(img);
        }
        insertionSort();
        currentIndex = -1;
    }

    /**
     * Returns the first image in the album and sets currentIndex to the beginning
     * of the list.
     *
     * @return the first ImageRecord or null if the album is empty
     */
    public ImageRecord getFirst() {
        if (images.isEmpty()) {
            return null;
        }
        currentIndex = 0;
        return images.get(currentIndex);
    }

    /**
     * Moves currentIndex forward and returns the next image in the album.
     *
     * @return the next ImageRecord or null if there is no next image
     */
    public ImageRecord getNext() {
        if (currentIndex == -1 || currentIndex + 1 >= images.size()) {
            return null;
        }
        currentIndex++;
        return images.get(currentIndex);
    }

    /**
     * Moves currentIndex backward and returns the previous image in the album.
     *
     * @return the previous ImageRecord or null if there is no previous image
     */
    public ImageRecord getPrevious() {
        if (currentIndex == -1 || currentIndex - 1 < 0) {
            return null;
        }
        currentIndex--;
        return images.get(currentIndex);
    }

    /**
     * Sorts the images in ascending order by date using an Insertion sort
     * algorithm.
     */
    public void insertionSort() {
        for (int i = 1; i < images.size(); i++) {
            ImageRecord temp = images.get(i);
            int j = i - 1;

            while (j >= 0 && temp.getDateTaken().isBefore(images.get(j).getDateTaken())) {
                images.set(j+1, images.get(j));
                j--;
            }
            images.set(j+1, temp);
        }
    }
}
