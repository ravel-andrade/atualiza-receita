package com.db.atualizareceita.validator;

import com.db.atualizareceita.fileMenager.Menager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.db.atualizareceita.Logger.logError;

public class Validator implements DataValidator{

    Menager fileManager;

    public Validator(Menager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public boolean csvFileIsValid(String csvPath) {
        List<String> errors = findErrorsInCsv(csvPath);
        if(!errors.isEmpty()){
            logError(errors);
            return false;
        }
        return true;
    }

    @Override
    public List<String> findErrorsInCsv(String csvPath) {
        List<String> errors = new ArrayList<>();
        try {
            String[] headers = fileManager.getHeaders(csvPath);
            if(!csvHeadersAreValid(headers)){
                errors.add("incorrect headers");
            }
        } catch (IOException error) {
            errors.add("file or directory dont exists");
        }
        return errors;
    }

    @Override
    public boolean csvHeadersAreValid(String[] headers) {
        if(headers[0].equals("agencia")
                && headers[1].equals("conta")
                && headers[2].equals("saldo")
                && headers[3].equals("status")
        ){return true;}
        return false;
    }
}
