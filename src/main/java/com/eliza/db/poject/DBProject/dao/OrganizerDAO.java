package com.eliza.db.poject.DBProject.dao;

import com.eliza.db.poject.DBProject.models.*;
import com.eliza.db.poject.DBProject.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
                organizer.getOrganizerId(), organizer.getName(), organizer.getAddress(), organizer.getEmail());
    }

    public void update(int id, Organizer updatedOrganizer) {
        int rowsAffected = jdbcTemplate.update("UPDATE organizer SET organizer_id=?, name=?, address=?, email=? WHERE organizer_id=?",
                updatedOrganizer.getOrganizerId(), updatedOrganizer.getName(), updatedOrganizer.getAddress(), updatedOrganizer.getEmail(), id);
        if (rowsAffected == 0) {
            throw new OrganizerNotCreatedException("Organizer with id: " + id + " not found");
        }
    }

    public void delete(int id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM organizer WHERE organizer_id=?", id);
        if (rowsAffected == 0) {
            throw new OrganizerNotFoundException("Organizer with id: " + id + " not found");
        }
    }

    public void connectTheOrganizerWithInvestor(int organizerId, int investorId) {
        int rowsAffected = jdbcTemplate.update("INSERT INTO organizer_investor(organizer_id, investor_id) VALUES(?, ?)",
                organizerId, investorId);
        if (rowsAffected == 0) {
            throw new OrganizerNotCreatedException("Organizer or Investor with organizerId: " + organizerId + ", or investorId: " + investorId + " not found");
        }
    }


    public List<Staff> getStaffByOrganizerId(int id) {
        return jdbcTemplate.query("SELECT s.name, s.phone_number, s.role, s.organizer_id FROM staff AS s JOIN organizer AS o ON s.organizer_id = o.organizer_id WHERE o.organizer_id=?", new BeanPropertyRowMapper<>(Staff.class), id);
    }

    public List<Investor> getInvestorByOrganizerId(int id) {
        return jdbcTemplate.query("""
                SELECT i.name,
                    i.email,
                    i.investment_amount
                FROM organizer AS o
                    JOIN organizer_investor AS oi ON o.organizer_id = oi.organizer_id
                    JOIN investor as i on oi.investor_id = i.investor_id
                WHERE o.organizer_id = 1;""", new BeanPropertyRowMapper<>(Investor.class), id);
    }
}
