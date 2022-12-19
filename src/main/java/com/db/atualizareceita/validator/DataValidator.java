package com.db.atualizareceita.validator;

import java.util.List;

public interface DataValidator {

    public boolean csvFileIsValid(String[] headers);
    public boolean csvHeadersAreValid(String[] headers);

}
