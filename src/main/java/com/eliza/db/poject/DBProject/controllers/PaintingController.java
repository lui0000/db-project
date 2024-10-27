package com.eliza.db.poject.DBProject.controllers;

import com.eliza.db.poject.DBProject.dao.PaintingDAO;
import com.eliza.db.poject.DBProject.models.Painting;
import com.eliza.db.poject.DBProject.util.PaintingErrorResponse;
import com.eliza.db.poject.DBProject.util.PaintingNotCreatedException;
import com.eliza.db.poject.DBProject.util.PaintingNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paintings")
public class PaintingController {
    private final PaintingDAO paintingDAO;

    @Autowired
    public PaintingController(PaintingDAO paintingDAO) {
        this.paintingDAO = paintingDAO;
    }

    @GetMapping
    public List<Painting> index() {
        return paintingDAO.index();
    }

    @GetMapping("/{id}")
    public Painting show(@PathVariable("id") int id) {
        return paintingDAO.show(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Painting painting, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PaintingNotCreatedException(errorMsg.toString());
        }
        paintingDAO.save(painting);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid Painting painting, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PaintingNotCreatedException(errorMsg.toString());
        }
        paintingDAO.update(id, painting);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        try {
            paintingDAO.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PaintingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler
    private ResponseEntity<PaintingErrorResponse> handleException(PaintingNotFoundException e) {
        PaintingErrorResponse response = new PaintingErrorResponse(
                "Painting with this ID wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PaintingErrorResponse> handleException(PaintingNotCreatedException e) {
        PaintingErrorResponse response = new PaintingErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
