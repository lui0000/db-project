package com.eliza.db.poject.DBProject.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Style {

    private int id;

    @NotEmpty(message = "The style must not be empty")
    @Size(min = 2, max = 100, message = "The style must be between 2 and 100 characters long")
    private String style;

    private int paintingId;

    public Style(String style, int paintingId) {
        this.style = style;
        this.paintingId = paintingId;
    }

    public Style() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getPaintingId() {
        return paintingId;
    }

    public void setPaintingId(int paintingId) {
        this.paintingId = paintingId;
    }
}
