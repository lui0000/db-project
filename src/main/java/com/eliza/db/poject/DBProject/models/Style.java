package com.eliza.db.poject.DBProject.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Style {

    private int id;

    @NotEmpty(message = "The style must not be empty")
    @Size(min = 2, max = 100, message = "The style must be between 2 and 100 characters long")
    private String style;
}
