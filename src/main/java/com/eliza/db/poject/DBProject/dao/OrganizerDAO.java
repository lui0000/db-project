package com.eliza.db.poject.DBProject.dao;

import com.eliza.db.poject.DBProject.models.Organizer;
import com.eliza.db.poject.DBProject.models.Painting;
import com.eliza.db.poject.DBProject.util.OrganizerNotFoundException;
import com.eliza.db.poject.DBProject.util.PaintingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class OrganizerDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrganizerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Organizer> index() {
        return jdbcTemplate.query("SELECT * FROM organizer", new BeanPropertyRowMapper<>(Organizer.class));
    }

    public Organizer show(int id) {
        return jdbcTemplate.query("SELECT * FROM organizer WHERE organizer_id=?", new BeanPropertyRowMapper<>(Organizer.class), id)
                .stream().findAny().orElseThrow(() -> new OrganizerNotFoundException("Organizer with id: " + id + " not found"));
    }

    public void save(Organizer organizer) {
        jdbcTemplate.update("INSERT INTO organizer(organizer_id, name, address, email) VALUES(?, ?, ?, ?)",
                organizer.getId(), organizer.getName(), organizer.getAddress(), organizer.getEmail());
    }

    public void update(int id, Organizer updatedOrganizer) {
        jdbcTemplate.update("UPDATE organizer SET organizer_id=?, name=?, address=?, email=? WHERE organizer_id=?",
                updatedOrganizer.getId(), updatedOrganizer.getName(), updatedOrganizer.getAddress(), updatedOrganizer.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM organizer WHERE organizer_id=?", id);
    }
}
