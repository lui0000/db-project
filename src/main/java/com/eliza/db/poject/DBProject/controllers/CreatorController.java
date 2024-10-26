package com.eliza.db.poject.DBProject.controllers;


import com.eliza.db.poject.DBProject.dao.CreatorDAO;
import com.eliza.db.poject.DBProject.models.Creator;
import com.eliza.db.poject.DBProject.models.ExhibitionHall;
import com.eliza.db.poject.DBProject.models.Painting;
import com.eliza.db.poject.DBProject.util.CreatorErrorResponse;
import com.eliza.db.poject.DBProject.util.CreatorNotCreatedException;
import com.eliza.db.poject.DBProject.util.CreatorNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/creators")
public class CreatorController {

    private final CreatorDAO creatorDAO;

    @Autowired
    public CreatorController(CreatorDAO creatorDAO) {
        this.creatorDAO = creatorDAO;
    }

    @GetMapping
    public List<Creator> index() {
       return new ArrayList<>(creatorDAO.index());
    }

    @GetMapping("/{id}")
    public Creator findOne(@PathVariable("id") int id) {
        return creatorDAO.show(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Creator creator, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new CreatorNotCreatedException(errorMsg.toString());
        }
        creatorDAO.save(creator);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid Creator creator, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";"));
            throw new CreatorNotCreatedException(errorMsg.toString());
        }
        creatorDAO.update(id, creator);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        creatorDAO.delete(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/connect/{creatorId}/{exhibitionHallId}")
    public ResponseEntity<HttpStatus> connectCreatorWithExhibitionHall(
            @PathVariable int creatorId,
            @PathVariable int exhibitionHallId) {
        creatorDAO.connectTheCreatorWithTheExhibitionHall(creatorId, exhibitionHallId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/paintingsbycreatorid/{id}")
    public List<Painting> showPaintings(@PathVariable int id) {
       return creatorDAO.getPaintingsByCreatorId(id);
    }

    @GetMapping("/getexhibitionhallbycreatorid/{id}")
    public List<ExhibitionHall> showExhibitionHalls(@PathVariable int id) {
        return creatorDAO.getExhibitionHallByCreatorId(id);
    }



    @ExceptionHandler
    private ResponseEntity<CreatorErrorResponse> handleException(CreatorNotFoundException e) {
        CreatorErrorResponse response = new CreatorErrorResponse(
                "Creator with this id wasn't found", System.currentTimeMillis()
        );
        //status 400
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CreatorErrorResponse> handleException(CreatorNotCreatedException e) {
        CreatorErrorResponse response = new CreatorErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        //status 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}
