package com.beatte.art.imgsrch.rest;

/**
 * @author art.beatte
 * @version 2/1/16.
 */
public class GettyImage {

    public String caption;
    public DisplaySize[] display_sizes;

    public String getTitle() {
        return display_sizes[0].name;
    }

    public String getUrl() {
        return display_sizes[0].uri;
    }
}
