package com.eliza.db.poject.DBProject.controllers;


import com.eliza.db.poject.DBProject.dao.CreatorDAO;
import com.eliza.db.poject.DBProject.models.Creator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


}
