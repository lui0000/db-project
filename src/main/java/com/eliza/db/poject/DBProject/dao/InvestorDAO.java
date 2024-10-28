package com.eliza.db.poject.DBProject.dao;

import com.eliza.db.poject.DBProject.models.Investor;
import com.eliza.db.poject.DBProject.util.InvestorNotCreatedException;
import com.eliza.db.poject.DBProject.util.InvestorNotFoundException;
import com.eliza.db.poject.DBProject.util.OrganizerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvestorDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InvestorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Investor> index() {
        return jdbcTemplate.query("SELECT * FROM investor", new BeanPropertyRowMapper<>(Investor.class));
    }


    public Investor show(int id) {
        return jdbcTemplate.query("SELECT * FROM investor WHERE investor_id=?",
                        new BeanPropertyRowMapper<>(Investor.class), id)
                .stream().findAny().orElseThrow(() ->
                        new InvestorNotFoundException("Investor with id: " + id + " not found"));
    }


    public void save(Investor investor) {
        jdbcTemplate.update("INSERT INTO investor(name, email, investment_amount) VALUES(?, ?, ?)",
                investor.getName(), investor.getEmail(), investor.getInvestmentAmount());
    }


    public void update(int id, Investor updatedInvestor) {

        int rowsAffected =  jdbcTemplate.update("UPDATE investor SET name=?, email=?, investment_amount=? WHERE investor_id=?",
                updatedInvestor.getName(), updatedInvestor.getEmail(),
                updatedInvestor.getInvestmentAmount(), id);
        if (rowsAffected == 0) {
            throw new InvestorNotCreatedException("Investor with id: " + id + " not found");
        }
    }


    public void delete(int id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM investor WHERE investor_id=?", id);
        if (rowsAffected == 0) {
            throw new InvestorNotFoundException("Investor with id: " + id + " not found");
        }
    }


}
