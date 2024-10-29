package com.eliza.db.poject.DBProject.controllers;

import com.eliza.db.poject.DBProject.dao.StaffDAO;
import com.eliza.db.poject.DBProject.models.Staff;
import com.eliza.db.poject.DBProject.util.*;
import com.eliza.db.poject.DBProject.util.validators.StaffValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {
    private final StaffDAO staffDAO;
    private final StaffValidator staffValidator;

    @Autowired
    public StaffController(StaffDAO staffDAO, StaffValidator staffValidator) {
        this.staffDAO = staffDAO;
        this.staffValidator = staffValidator;
    }

    @GetMapping
    public List<Staff> index() {
        return staffDAO.index();
    }

    @GetMapping("/{id}")
    public Staff show(@PathVariable("id") int id) {
        return staffDAO.show(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Staff staff, BindingResult bindingResult) {
        staffValidator.validate(staff, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new StaffNotCreatedException(errorMsg.toString());
        }
        staffDAO.save(staff);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid Staff staff, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new StaffNotCreatedException(errorMsg.toString());
        }
        staffDAO.update(id, staff);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        try {
            staffDAO.delete(id);
            return ResponseEntity.noContent().build();
        } catch (StaffNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler
    private ResponseEntity<StaffErrorResponse> handleException(StaffNotFoundException e) {
        StaffErrorResponse response = new StaffErrorResponse(
                "Staff with this id wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<StaffErrorResponse> handleException(StaffNotCreatedException e) {
        StaffErrorResponse response = new StaffErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

