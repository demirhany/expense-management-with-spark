package org.ozo.big_data_hw_spring.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.ozo.big_data_hw_spring.model.DataCol16;

import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader {
    public static DataCol16 readAllDataAtOnce(String fileName) {
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(fileName);

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();

            // e
            List<String> data =  csvReader.readAll().stream().map(CsvReader::_parseLine).collect(Collectors.toList());
            return new DataCol16(data);
        } catch (Exception e) {
            throw new RuntimeException("Error while reading CSV file: " + e);
        }
    }

    private static String _parseLine(String[] row) {
        return row[10];
    }
}
