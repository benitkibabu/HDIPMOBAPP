package com.example.scr3.customlistview;

/**
 * Created by scr3 on 01/03/2017.
 */

public class CustomItem {
    int imageId;
    String title;

    public CustomItem(int imgId, String title){
        this.imageId = imgId;
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
