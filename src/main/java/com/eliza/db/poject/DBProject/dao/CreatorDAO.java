package com.eliza.db.poject.DBProject.dao;

import com.eliza.db.poject.DBProject.models.Creator;
import com.eliza.db.poject.DBProject.models.ExhibitionHall;
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
        int rowsAffected = jdbcTemplate.update("UPDATE creator SET name=?, email=?, country=?, age=?, number_of_works=? WHERE creator_id=?",
                updatedCreator.getName(), updatedCreator.getEmail(), updatedCreator.getCountry(), updatedCreator.getAge(), updatedCreator.getNumberOfWorks(), id);
        if (rowsAffected == 0) {
            throw new CreatorNotCreatedException("Creator with id: " + id + " not found");
        }
    }

    public void delete(int id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM creator WHERE creator_id=?", id);
        if (rowsAffected == 0) {
            throw new CreatorNotFoundException("Creator with id: " + id + " not found");
        }
    }

    public void connectTheCreatorWithTheExhibitionHall(int creatorId, int exhibitionHallId) {
        int rowsAffected = jdbcTemplate.update("INSERT INTO creator_exhibition_hall(creator_id, exhibition_hall_id) VALUES(?, ?)",
                creatorId, exhibitionHallId);
        if (rowsAffected == 0) {
            throw new CreatorNotCreatedException("Creator or Exhibition Hall with creatorId: " + creatorId + ", or exhibitionHallId: " + exhibitionHallId + " not found");
        }
    }


    // one to many with Paintings
    public List<Painting> getPaintingsByCreatorId(int id) {
        return jdbcTemplate.query("""
                SELECT c.name,
                    p.name, p.price, p.creation_date, p.creator_id, p.painting_id
                FROM creator AS c
                    JOIN painting AS p ON c.creator_id = p.creator_id
                WHERE c.creator_id = ?""", new BeanPropertyRowMapper<>(Painting.class), id);
    }

    // many to many with exhibition hall
    public List<ExhibitionHall> getExhibitionHallByCreatorId(int id) {
        return jdbcTemplate.query("""
                SELECT eh.exhibition_hall_id, eh.organizer_id, eh.serial_number, eh.capacity
                FROM creator AS c
                    JOIN creator_exhibition_hall AS ceh ON c.creator_id = ceh.creator_id
                    JOIN exhibition_hall AS eh ON ceh.exhibition_hall_id = eh.exhibition_hall_id
                WHERE c.creator_id = ?""", new BeanPropertyRowMapper<>(ExhibitionHall.class), id);
    }

}

