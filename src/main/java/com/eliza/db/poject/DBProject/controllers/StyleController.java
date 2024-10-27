package com.eliza.db.poject.DBProject.controllers;

import com.eliza.db.poject.DBProject.dao.StyleDAO;
import com.eliza.db.poject.DBProject.models.Style;
import com.eliza.db.poject.DBProject.util.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/styles")
public class StyleController {
    private final StyleDAO styleDAO;

    @Autowired
    public StyleController(StyleDAO styleDAO) {
        this.styleDAO = styleDAO;
    }

    @GetMapping
    public List<Style> index() {
        return styleDAO.index();
    }

    @GetMapping("/{id}")
    public Style show(@PathVariable("id") int id) {
        return styleDAO.show(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Style style, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new StyleNotCreatedException(errorMsg.toString());
        }
        styleDAO.save(style);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid Style style, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new StyleNotCreatedException(errorMsg.toString());
        }
        styleDAO.update(id, style);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        try {
            styleDAO.delete(id);
            return ResponseEntity.noContent().build();
        } catch (StyleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler
    private ResponseEntity<StyleErrorResponse> handleException(StyleNotFoundException e) {
        StyleErrorResponse response = new StyleErrorResponse(
                "Style with this id wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<StyleErrorResponse> handleException(StyleNotCreatedException e) {
        StyleErrorResponse response = new StyleErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
