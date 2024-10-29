package com.eliza.db.poject.DBProject.util;

import com.eliza.db.poject.DBProject.dao.CreatorDAO;
import com.eliza.db.poject.DBProject.dao.PaintingDAO;
import com.eliza.db.poject.DBProject.models.Painting;
import com.eliza.db.poject.DBProject.models.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PaintingValidator implements Validator {

    private final PaintingDAO paintingDAO;
    private final CreatorDAO creatorDAO;

    @Autowired
    public PaintingValidator(PaintingDAO paintingDAO, CreatorDAO creatorDAO) {
        this.paintingDAO = paintingDAO;
        this.creatorDAO = creatorDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Painting.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Painting painting = (Painting) target;
        int creatorId = painting.getCreatorId();

        try {
            creatorDAO.show(creatorId);
        } catch (CreatorNotFoundException e) {
            errors.rejectValue("creatorId", "",  creatorId + " does not exist");
        }
    }
}
