package com.eliza.db.poject.DBProject.dao;

import com.eliza.db.poject.DBProject.models.ExhibitionHall;
import com.eliza.db.poject.DBProject.util.ExhibitionHallNotCreatedException;
import com.eliza.db.poject.DBProject.util.ExhibitionHallNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExhibitionHallDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExhibitionHallDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ExhibitionHall> index() {
        return jdbcTemplate.query("SELECT * FROM exhibition_hall", new BeanPropertyRowMapper<>(ExhibitionHall.class));
    }

    public ExhibitionHall show(int id) {
        return jdbcTemplate.query("SELECT * FROM exhibition_hall WHERE exhibition_hall_id=?", new BeanPropertyRowMapper<>(ExhibitionHall.class), id)
                .stream().findAny().orElseThrow(() -> new ExhibitionHallNotFoundException("Exhibition Hall with id: " + id + " not found"));
    }

    public void save(ExhibitionHall exhibitionHall) {
        jdbcTemplate.update("INSERT INTO exhibition_hall(organizer_id, serial_number, capacity) VALUES(?, ?, ?)",
                exhibitionHall.getOrganizerId(), exhibitionHall.getSerialNumber(), exhibitionHall.getCapacity());


    }

    public void update(int id, ExhibitionHall updatedExhibitionHall) {
        int rowsAffected = jdbcTemplate.update("UPDATE exhibition_hall SET organizer_id=?, serial_number=?, capacity=? WHERE exhibition_hall_id=?",
                updatedExhibitionHall.getOrganizerId(), updatedExhibitionHall.getSerialNumber(), updatedExhibitionHall.getCapacity(), id);

        if (rowsAffected == 0) {
            throw new ExhibitionHallNotCreatedException("Exhibition Hall with id: " + id + " not found");
        }
    }

    public void delete(int id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM exhibition_hall WHERE exhibition_hall_id=?", id);
        if (rowsAffected == 0) {
            throw new ExhibitionHallNotFoundException("Exhibition Hall with id: " + id + " not found");
        }
    }

}