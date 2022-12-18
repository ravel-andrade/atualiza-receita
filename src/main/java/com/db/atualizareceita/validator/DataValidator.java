package com.db.atualizareceita.validator;

import java.util.List;

public interface DataValidator {

    public boolean csvFileIsValid(String csvPath);
    public List<String> findErrorsInCsv(String csvPath);
    public boolean csvHeadersAreValid(String[] headers);

}
