package com.eliza.db.poject.DBProject.util;
import com.eliza.db.poject.DBProject.dao.PaintingDAO;
import com.eliza.db.poject.DBProject.dao.StyleDAO;
import com.eliza.db.poject.DBProject.models.Painting;
import com.eliza.db.poject.DBProject.models.Staff;
import com.eliza.db.poject.DBProject.models.Style;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class StyleValidator implements Validator {

    private final StyleDAO styleDAO;
    private final PaintingDAO paintingDAO;

    @Autowired
    public StyleValidator(StyleDAO styleDAO, PaintingDAO paintingDAO) {
        this.styleDAO = styleDAO;
        this.paintingDAO = paintingDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Painting.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Style style = (Style) target;
        int paintingId = style.getPaintingId();

        try {
            paintingDAO.show(paintingId);
        } catch (OrganizerNotFoundException e) {
            errors.rejectValue("paintingId", "", paintingId + " does not exist");
        }

    }
}
