package helper;

public class ImageResolutions {
    private int image_height;
    private int image_width;

    public int getImage_width() {
        return this.image_width;
    }

    public void setImage_width(int i) {
        this.image_width = i;
    }

    public int getImage_height() {
        return this.image_height;
    }

    public void setImage_height(int i) {
        this.image_height = i;
    }

    public ImageResolutions(int i, int i2) {
        this.image_width = i;
        this.image_height = i2;
    }
}