package com.eliza.db.poject.DBProject.controllers;

import com.eliza.db.poject.DBProject.dao.OrganizerDAO;
import com.eliza.db.poject.DBProject.models.Organizer;
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
@RequestMapping("/organizers")
public class OrganizerController {
    private final OrganizerDAO organizerDAO;

    @Autowired
    public OrganizerController(OrganizerDAO organizerDAO) {
        this.organizerDAO = organizerDAO;
    }

    @GetMapping
    public List<Organizer> index() {
        return organizerDAO.index();
    }

    @GetMapping("/{id}")
    public Organizer show(@PathVariable("id") int id) {
        return organizerDAO.show(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Organizer organizer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new OrganizerNotCreatedException(errorMsg.toString());
        }
        organizerDAO.save(organizer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid Organizer organizer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new OrganizerNotCreatedException(errorMsg.toString());
        }
        organizerDAO.update(id, organizer);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        try {
            organizerDAO.delete(id);
            return ResponseEntity.noContent().build();
        } catch (OrganizerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler
    private ResponseEntity<OrganizerErrorResponse> handleException(OrganizerNotFoundException e) {
        OrganizerErrorResponse response = new OrganizerErrorResponse(
                "Organizer with this id wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<OrganizerErrorResponse> handleException(OrganizerNotCreatedException e) {
        OrganizerErrorResponse response = new OrganizerErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

