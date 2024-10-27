package com.eliza.db.poject.DBProject.controllers;

import com.eliza.db.poject.DBProject.dao.InvestorDAO;
import com.eliza.db.poject.DBProject.models.Investor;
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
@RequestMapping("/investors")
public class InvestorController {
    private final InvestorDAO investorDAO;

    @Autowired
    public InvestorController(InvestorDAO investorDAO) {
        this.investorDAO = investorDAO;
    }

    @GetMapping
    public List<Investor> index() {
        return investorDAO.index();
    }

    @GetMapping("/{id}")
    public Investor show(@PathVariable("id") int id) {
        return investorDAO.show(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Investor investor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new InvestorNotCreatedException(errorMsg.toString());
        }
        investorDAO.save(investor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid Investor investor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new InvestorNotCreatedException(errorMsg.toString());
        }
        investorDAO.update(id, investor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        try {
            investorDAO.delete(id);
            return ResponseEntity.noContent().build();
        } catch (InvestorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler
    private ResponseEntity<InvestorErrorResponse> handleException(InvestorNotFoundException e) {
        InvestorErrorResponse response = new InvestorErrorResponse(
                "Investor with this id wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InvestorErrorResponse> handleException(InvestorNotCreatedException e) {
        InvestorErrorResponse response = new InvestorErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

