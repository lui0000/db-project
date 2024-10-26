package com.eliza.db.poject.DBProject.dao;

import com.eliza.db.poject.DBProject.models.Creator;
import com.eliza.db.poject.DBProject.models.Painting;
import com.eliza.db.poject.DBProject.util.CreatorNotCreatedException;
import com.eliza.db.poject.DBProject.util.CreatorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreatorDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CreatorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Creator> index() {
        return jdbcTemplate.query("SELECT * FROM creator", new BeanPropertyRowMapper<>(Creator.class));
    }

    public Creator show(int id) {
        return jdbcTemplate.query("SELECT * FROM creator WHERE creator_id=?", new BeanPropertyRowMapper<>(Creator.class), id)
                .stream().findAny().orElseThrow(() -> new CreatorNotFoundException("Creator with id: " + id + " not found"));
    }

    public void save(Creator creator) {
        jdbcTemplate.update("INSERT INTO creator(name, email, country, age, number_of_works) VALUES(?, ?, ?, ?, ?)",
                creator.getName(), creator.getEmail(), creator.getCountry(), creator.getAge(), creator.getNumberOfWorks());
    }

    public void update(int id, Creator updatedCreator) {
        jdbcTemplate.update("UPDATE creator SET name=?, email=?, country=?, age=?, number_of_works=? WHERE creator_id=?",
                updatedCreator.getName(), updatedCreator.getEmail(), updatedCreator.getCountry(), updatedCreator.getAge(), updatedCreator.getNumberOfWorks(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM creator WHERE creator_id=?", id);
    }

    // one to many with Paintings

    public List<Painting> getPaintingsByCreatorId(int id) {
        return jdbcTemplate.query("SELECT * FROM ", new BeanPropertyRowMapper<>(Painting.class), id);
    }

    // many to many with exhibition hall
    public List<Painting> getExhibitionHallByCreatorId(int id) {
        return jdbcTemplate.query("SELECT * FROM ", new BeanPropertyRowMapper<>(Painting.class), id);
    }

}

