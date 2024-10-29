package com.eliza.db.poject.DBProject.util.validators;
import com.eliza.db.poject.DBProject.dao.PaintingDAO;
import com.eliza.db.poject.DBProject.dao.StyleDAO;
import com.eliza.db.poject.DBProject.models.Painting;
import com.eliza.db.poject.DBProject.models.Style;
import com.eliza.db.poject.DBProject.util.OrganizerNotFoundException;
import com.eliza.db.poject.DBProject.util.PaintingNotFoundException;
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
        return Style.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Style style = (Style) target;
        int paintingId = style.getPaintingId();

        try {
            paintingDAO.show(paintingId);
        } catch (PaintingNotFoundException e) {
            errors.rejectValue("paintingId", "", paintingId + " does not exist");
        }

    }
}
