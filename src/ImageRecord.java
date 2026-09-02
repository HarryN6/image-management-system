import java.time.LocalDate;

public class ImageRecord {

    private static int nextId = 1;
    private int imageId;
    private String title;
    private String desc;
    private ImageType imgType;
    private LocalDate dateTaken;
    private String thumbnail;
    private static final LocalDate EARLIEST_PHOTOGRAPH = LocalDate.of(1826, 1, 1);

    /**
     * Creates a new ImageRecord with the given data. A unique ID is assigned
     * automatically.
     *
     * @param title       the title of the image
     * @param desc the description of the image
     * @param imgType       the image type
     * @param dateTaken   the date the image was taken
     * @param thumbnail   the thumbnail filename
     */
    public ImageRecord(String title, String desc, ImageType imgType, LocalDate dateTaken, String thumbnail) throws Exception {
        setTitle(title);
        setDesc(desc);
        this.imgType = imgType;
        setDate(dateTaken);
        setThumbnail(thumbnail);
        imageId = nextId++;
    }

    /**
     * Returns the thumbnail filename for this image.
     *
     * @return the thumbnail filename
     */
    public String getThumbnail() {
        return this.thumbnail;
    }

    /**
     * Returns the date the image was taken.
     *
     * @return the date taken
     */
    public LocalDate getDateTaken() {
        return this.dateTaken;
    }

    /**
     * Returns the genre/category of the image.
     *
     * @return the image genre
     */
    public ImageType getImgType() {
        return this.imgType;
    }

    /**
     * Returns the description of the image.
     *
     * @return the image description
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * Returns the title of the image.
     *
     * @return the image title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the unique ID of this image record.
     *
     * @return the image ID
     */
    public int getImageId() {
        return this.imageId;
    }

    /**
     * Sets the thumbnail filename if the provided value is not null, blank, or contains a thumbnail at the end. .png is automatically assigned.
     *
     * @param thumbnail the new thumbnail filename
     */
    private void setThumbnail(String thumbnail) throws Exception {
        if (thumbnail == null || thumbnail.trim().isEmpty()) {
            throw new Exception("Thumbnail cannot be empty.");
        }
        if (thumbnail.contains(".")) {
            throw new Exception("Images are automatically assigned .png");
        }
        this.thumbnail = thumbnail.trim()+".png";
    }

    /**
     * Sets the date the image was taken if the provided value is neither null or predates photography.
     *
     * @param dateTaken the date to assign
     */
    private void setDate(LocalDate dateTaken) throws Exception{
        if (dateTaken == null) {
            throw new Exception("Date cannot be null.");
        }

        if (dateTaken.isBefore(EARLIEST_PHOTOGRAPH)) {
            throw new Exception("Date predates the invention of photography.");
        }

        if (dateTaken.isAfter(LocalDate.now())) {
            throw new Exception("Date cannot be in the future.");
        }

        this.dateTaken = dateTaken;
    }

    /**
     * Sets the image description if the provided value is not null or blank.
     *
     * @param desc the new description
     */
    private void setDesc(String desc) throws Exception {
        if (desc == null || desc.trim().isEmpty()) {
            throw new Exception("Description cannot be empty.");
        }
        this.desc = desc.trim();
    }

    /**
     * Sets the image title if the provided value is not null or blank.
     *
     * @param title the new title
     */
    private void setTitle(String title) throws Exception {
        if (title == null || title.trim().isEmpty()) {
            throw new Exception("Title cannot be empty.");
        }
        this.title = title.trim();
    }

    /**
     * Returns a formatted string with no line breaks containing all the data for
     * this image record.
     *
     * @return a string representation of the image record
     */
    @Override
    public String toString() {
        return "ID: "+getImageId()+" | Title: "+getTitle()+" | Desc: "+getDesc()+" | Type: "+imgType+" - "+imgType.getImgType()+" | Date taken: "+getDateTaken()+" | Thumbnail: "+getThumbnail();
    }
}
