package com.db.atualizareceita.validator;

import com.db.atualizareceita.fileMenager.Menager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.db.atualizareceita.Logger.logError;

public class Validator implements DataValidator{
    public Validator() {
    }

    @Override
    public boolean csvFileIsValid(String[] headers) {
        if(!csvHeadersAreValid(headers)){
            logError("incorrect headers");
            return false;
        }
        return true;
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
