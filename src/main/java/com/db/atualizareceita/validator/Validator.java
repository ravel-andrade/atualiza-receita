package com.db.atualizareceita.validator;

import com.db.atualizareceita.fileMenager.Menager;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.db.atualizareceita.Logger.logError;
@Component
public class Validator implements DataValidator{
    public Validator() {
    }
    @Override
    public boolean csvHeadersAreValid(String[] headers) {
        return headers[0].equals("agencia")
                && headers[1].equals("conta")
                && headers[2].equals("saldo")
                && headers[3].equals("status");
    }
}
