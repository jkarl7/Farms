package farm.application.core;

import com.opencsv.bean.CsvToBeanBuilder;
import farm.application.api.ReadFromFile;
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
            return Output.builder().items(csvFileItems).build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
