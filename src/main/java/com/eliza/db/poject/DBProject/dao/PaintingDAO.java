package com.eliza.db.poject.DBProject.dao;

import com.eliza.db.poject.DBProject.models.Painting;
import com.eliza.db.poject.DBProject.util.PaintingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PaintingDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PaintingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Painting> index() {
        return jdbcTemplate.query("SELECT * FROM painting", new BeanPropertyRowMapper<>(Painting.class));
    }

    public Painting show(int id) {
        return jdbcTemplate.query("SELECT * FROM painting WHERE painting_id=?", new BeanPropertyRowMapper<>(Painting.class), id)
                .stream().findAny().orElseThrow(() -> new PaintingNotFoundException("Painting with id: " + id + " not found"));
    }

    public void save(Painting painting) {
        jdbcTemplate.update("INSERT INTO painting(creator_id, name, creation_date, price) VALUES(?, ?, ?, ?)",
                painting.getCreatorId(), painting.getName(), painting.getCreationDate(), painting.getPrice());
    }

    public void update(int id, Painting updatedPainting) {
        jdbcTemplate.update("UPDATE painting SET creator_id=?, name=?, creation_date=?, price=? WHERE painting_id=?",
                updatedPainting.getCreatorId(), updatedPainting.getName(), updatedPainting.getCreationDate(), updatedPainting.getPrice(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM painting WHERE painting_id=?", id);
    }
}
