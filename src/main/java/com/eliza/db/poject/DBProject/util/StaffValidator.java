package com.eliza.db.poject.DBProject.util;

import com.eliza.db.poject.DBProject.dao.OrganizerDAO;
import com.eliza.db.poject.DBProject.dao.StaffDAO;
import com.eliza.db.poject.DBProject.models.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StaffValidator implements Validator {

    private final StaffDAO staffDAO;
    private final OrganizerDAO organizerDAO;

    @Autowired
    public StaffValidator(StaffDAO staffDAO, OrganizerDAO organizerDAO) {
        this.staffDAO = staffDAO;
        this.organizerDAO = organizerDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Staff.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Staff staff = (Staff) target;
        int organizerId = staff.getOrganizerId();

        try {
            organizerDAO.show(organizerId);
        } catch (OrganizerNotFoundException e) {
            errors.rejectValue("organizerId", "", "Organizer with ID: " + organizerId + " does not exist");
        }
    }
}
