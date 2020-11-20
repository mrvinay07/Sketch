package util;

import android.util.Log;
import helper.ImageResolutions;
import java.util.ArrayList;
import java.util.List;

public class ImageSize {
    private int ImageCrop_Height = 0;
    private int ImageCrop_Width = 0;
    private int Image_height = 0;
    private int Image_width = 0;
    private int MaxMemory;
    public int maximumHeight = 0;
    public int maximumWidth = 0;
    public List<ImageResolutions> resolutions = new ArrayList();
    public int textureSize = 0;

    public enum Memory {
        HIGH,
        HIGHLOW,
        MEDIUM,
        MEDIUMLOW,
        LOW
    }

    public List<ImageResolutions> getResolutions() {
        return this.resolutions;
    }

    public void setImageCrop_Width(int i) {
        this.ImageCrop_Width = i;
    }

    public void setImageCrop_Height(int i) {
        this.ImageCrop_Height = i;
    }

    public int getMaxMemory() {
        return this.MaxMemory;
    }

    public void setMaxMemory(int i) {
        this.MaxMemory = i;
    }

    public int getImageCrop_Width() {
        return this.ImageCrop_Width;
    }

    public int getImageCrop_Height() {
        return this.ImageCrop_Height;
    }

    public void setMaximumTextureSize(int i) {
        this.textureSize = i;
    }

    public void setBitmapMaximumSize(int i, int i2) {
        this.maximumWidth = i;
        this.maximumHeight = i2;
        calculateResolutions();
    }

    public void calculateResolutions() {
        if (this.resolutions.size() > 0) {
            this.resolutions.clear();
        }
        if (this.maximumWidth >= 3840 && this.maximumHeight >= 2160) {
            this.resolutions.add(new ImageResolutions(1280, 720));
            this.resolutions.add(new ImageResolutions(1440, 1080));
            this.resolutions.add(new ImageResolutions(1920, 1080));
            if (this.textureSize == 8192) {
                this.resolutions.add(new ImageResolutions(2048, 1080));
            }
            if (this.textureSize == 8192) {
                this.resolutions.add(new ImageResolutions(3840, 2160));
            }
        } else if (this.maximumWidth <= 1280 || this.maximumHeight <= 720) {
            if (this.maximumWidth < 1280 && this.maximumWidth < 1440 && this.maximumWidth < 1920 && this.maximumWidth < 2048) {
                return;
            }
            if (this.maximumHeight < 720 && this.maximumHeight < 1080 && this.maximumHeight < 2160) {
                return;
            }
            if (this.maximumWidth < 1280 || this.maximumWidth < 1440 || this.maximumHeight < 720 || this.maximumHeight < 1080) {
                this.resolutions.add(new ImageResolutions(0, 0));
            } else if (this.maximumHeight > 720 && this.maximumHeight <= 1080) {
                this.resolutions.add(new ImageResolutions(1280, 720));
                this.resolutions.add(new ImageResolutions(1440, 1080));
            } else if (this.maximumWidth >= 2048 && this.maximumHeight >= 2160) {
                this.resolutions.add(new ImageResolutions(1280, 720));
                this.resolutions.add(new ImageResolutions(1440, 1080));
                this.resolutions.add(new ImageResolutions(1920, 1080));
                if (this.textureSize == 8192) {
                    this.resolutions.add(new ImageResolutions(2048, 1080));
                }
            } else if (this.maximumWidth >= 1920 && this.maximumHeight >= 1080) {
                this.resolutions.add(new ImageResolutions(1280, 720));
                this.resolutions.add(new ImageResolutions(1440, 1080));
                this.resolutions.add(new ImageResolutions(1920, 1080));
            }
        } else if (this.maximumWidth <= 1440 || this.maximumHeight <= 1080) {
            this.resolutions.add(new ImageResolutions(1280, 720));
        } else if (this.maximumWidth <= 2048 && this.maximumHeight <= 2160) {
            this.resolutions.add(new ImageResolutions(1280, 720));
            this.resolutions.add(new ImageResolutions(1440, 1080));
            if (this.maximumWidth == 1920 && this.maximumHeight <= 2160) {
                this.resolutions.add(new ImageResolutions(1920, 1080));
            }
            if (this.maximumWidth == 2048 && this.maximumHeight <= 2160 && this.textureSize == 8192) {
                this.resolutions.add(new ImageResolutions(2048, 1080));
            }
        } else if (this.maximumWidth < 2160 || this.maximumHeight < 2160) {
            this.resolutions.add(new ImageResolutions(1280, 720));
            this.resolutions.add(new ImageResolutions(1440, 1080));
        } else {
            this.resolutions.add(new ImageResolutions(1280, 720));
            this.resolutions.add(new ImageResolutions(1440, 1080));
            this.resolutions.add(new ImageResolutions(1920, 1080));
            if (this.textureSize == 8192) {
                this.resolutions.add(new ImageResolutions(2048, 1080));
            }
            if (this.maximumWidth >= 2160 && this.maximumHeight >= 3840) {
                this.resolutions.add(new ImageResolutions(3840, 2160));
            }
        }
    }

    public void calculateCropSize(Memory memory) {
        switch (memory) {
            case HIGH:
            case HIGHLOW:
                setImageCrop_Width(960);
                setImageCrop_Height(960);
                Log.d("CroP", "High");
                return;
            case MEDIUM:
                setImageCrop_Width(720);
                setImageCrop_Height(720);
                Log.d("CroP", "Medium");
                return;
            case MEDIUMLOW:
            case LOW:
                setImageCrop_Height(640);
                setImageCrop_Width(640);
                Log.d("CroP", "MediumLow");
                return;
            default:
                return;
        }
    }
}