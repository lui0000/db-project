package com.eliza.db.poject.DBProject.dao;

import com.eliza.db.poject.DBProject.models.Staff;
import com.eliza.db.poject.DBProject.util.StaffNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class StaffDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StaffDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Staff> index() {
        return jdbcTemplate.query("SELECT * FROM staff", new BeanPropertyRowMapper<>(Staff.class));
    }


    public Staff show(int id) {
        return jdbcTemplate.query("SELECT * FROM staff WHERE stuff_id=?",
                        new BeanPropertyRowMapper<>(Staff.class), id)
                .stream().findAny().orElseThrow(() ->
                        new StaffNotFoundException("Staff with id: " + id + " not found"));
    }


    public void save(Staff staff) {
        jdbcTemplate.update("INSERT INTO staff(name, phone_number, role, organizer_id) VALUES(?, ?, ?, ?)",
                staff.getName(), staff.getPhoneNumber(), staff.getRole(), staff.getOrganizerId());
    }

    public void update(int id, Staff updatedStaff) {
        jdbcTemplate.update("UPDATE staff SET name=?, phone_number=?, role=?, organizer_id=? WHERE stuff_id=?",
                updatedStaff.getName(), updatedStaff.getPhoneNumber(),
                updatedStaff.getRole(), updatedStaff.getOrganizerId(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM staff WHERE stuff_id=?", id);
    }
}
