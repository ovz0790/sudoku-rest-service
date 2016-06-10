package org.rest.service.sudoku.utils;

import javax.xml.bind.ValidationException;
import java.util.List;

/**
 * Created by zlobina on 09.06.16.
 */
public class ValidateUtil {

    public void validateBoardElement(List<Integer> parameters) throws ValidationException {
        for (Integer p : parameters){
            validateParameter(p);
        }
    }

    private void validateParameter(Integer parameter) throws ValidationException {
        if (parameter == null){
            throw new ValidationException("Board element input data not valid!");
        }

        if (parameter < 0 || parameter > 9){
            throw new ValidationException("Board element parameter must be between 0 and 9");
        }
    }

}
