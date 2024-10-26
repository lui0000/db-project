package com.eliza.db.poject.DBProject.controllers;

import com.eliza.db.poject.DBProject.dao.ExhibitionHallDAO;
import com.eliza.db.poject.DBProject.models.Creator;
import com.eliza.db.poject.DBProject.models.ExhibitionHall;
import com.eliza.db.poject.DBProject.util.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO fix id names in all classes to normal))

@RestController
@RequestMapping("/exhibitionhalls")
public class ExhibitionHallController {
    private final ExhibitionHallDAO exhibitionHallDAO;

    @Autowired
    public ExhibitionHallController(ExhibitionHallDAO exhibitionHallDAO) {
        this.exhibitionHallDAO = exhibitionHallDAO;
    }

    @GetMapping
    public List<ExhibitionHall> index() {
        return exhibitionHallDAO.index();
    }

    @GetMapping("/{id}")
    public ExhibitionHall show(@PathVariable("id") int id) {
        return exhibitionHallDAO.show(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ExhibitionHall exhibitionHall, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ExhibitionHallNotCreatedException(errorMsg.toString());
        }
        exhibitionHallDAO.save(exhibitionHall);
        return ResponseEntity.ok(HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid ExhibitionHall exhibitionHall, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ExhibitionHallNotFoundException(errorMsg.toString());
        }
        exhibitionHallDAO.update(id, exhibitionHall);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        exhibitionHallDAO.delete(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    private ResponseEntity<ExhibitionHallErrorResponse> handleException(ExhibitionHallNotFoundException e) {
        ExhibitionHallErrorResponse response = new ExhibitionHallErrorResponse(
                "Exhibition hall with this id wasn't found", System.currentTimeMillis()
        );
        //status 400
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ExhibitionHallErrorResponse> handleException(ExhibitionHallNotCreatedException e) {
        ExhibitionHallErrorResponse response = new ExhibitionHallErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        //status 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
