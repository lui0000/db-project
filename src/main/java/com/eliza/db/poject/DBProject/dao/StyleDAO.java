package com.eliza.db.poject.DBProject.dao;

import com.eliza.db.poject.DBProject.models.Style;
import com.eliza.db.poject.DBProject.util.StyleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class StyleDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StyleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Style> index() {
        return jdbcTemplate.query("SELECT * FROM style", new BeanPropertyRowMapper<>(Style.class));
    }


    public Style show(int id) {
        return jdbcTemplate.query("SELECT * FROM style WHERE style_id=?",
                        new BeanPropertyRowMapper<>(Style.class), id)
                .stream().findAny().orElseThrow(() ->
                        new StyleNotFoundException("Style with id: " + id + " not found"));
    }


    public void save(Style style) {
        jdbcTemplate.update("INSERT INTO style(style, painting_id) VALUES(?, ?)",
                style.getStyle(), style.getPaintingId());
    }


    public void update(int id, Style updatedStyle) {
        jdbcTemplate.update("UPDATE style SET style=?, painting_id=? WHERE style_id=?",
                updatedStyle.getStyle(), updatedStyle.getPaintingId(), id);
    }


    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM style WHERE style_id=?", id);
    }
}
