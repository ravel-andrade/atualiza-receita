package com.db.atualizareceita.processor;

import com.db.atualizareceita.model.CsvData;

import java.util.List;
import java.util.Optional;

public interface Processor {

    public boolean accept(String csvPath);
    public Optional<List<CsvData>> process(String csvPath);
}
