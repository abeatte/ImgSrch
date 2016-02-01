package com.beatte.art.imgsrch.rest;

/**
 * @author art.beatte
 * @version 2/1/16.
 */
public class GettyImage {

    String caption;
    String title;
    DisplaySize[] display_sizes;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return display_sizes[0].uri;
    }
}
