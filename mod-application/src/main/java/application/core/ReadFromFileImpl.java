package application.core;

import application.api.ReadFromFile;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Component
class ReadFromFileImpl implements ReadFromFile {

    private static final Character CSV_SEPARATOR = ';';

    @Override
    public Output execute(Input input) {
        try {
            List<CsvFileItem> csvFileItems = new CsvToBeanBuilder(new FileReader(input.getFileName()))
                    .withType(CsvFileItem.class)
                    .withSeparator(CSV_SEPARATOR)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSkipLines(0)
                    .build()
                    .parse();
            return Output.of(csvFileItems);
        } catch (FileNotFoundException e) {
            System.out.println("No CSV items found. Missing or wrong file?...Quitting");
        }
        return null;
    }
}
