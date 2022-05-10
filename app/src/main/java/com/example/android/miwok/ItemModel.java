package com.example.android.miwok;

public class ItemModel {
    private String miwokTranslation;
    private String defaultTranslation;
    private int imageId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int mediaPlayerResID;

    public ItemModel(String miwokTranslation, String defaultTranslation, int imageId,int mediaPlayerResID) {
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
        this.imageId = imageId;
        this.mediaPlayerResID = mediaPlayerResID;
    }

    public ItemModel(String miwokTranslation, String defaultTranslation,int mediaPlayerResID) {
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
        this.mediaPlayerResID = mediaPlayerResID;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public int getImageId() {
        return imageId;
    }

    public int getMediaPlayerResID() {
        return mediaPlayerResID;
    }

    public boolean hasImage(){
        return imageId != NO_IMAGE_PROVIDED;
    }
}
