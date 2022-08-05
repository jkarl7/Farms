package farm.application.core;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import farm.application.api.WriteToCsvFile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Component
class WriteToCsvFileImpl implements WriteToCsvFile {

    private static final Character CSV_SEPARATOR = ';';
    private static final String RESULT_FILE_NAME = "tulemus.csv";

    @Override
    public Output execute(Input input) {
        addJointAreasToCsvItems(input);

        // Create strategy to use it for preserving header ordering
        var strategy = new HeaderColumnNameMappingStrategy<CsvFileItem>();
        strategy.setType(CsvFileItem.class);

        // Initialize strategy by reading CSV file headers only
        try (var reader = new StringReader(getHeaders())) {
            var csv = new CsvToBeanBuilder<CsvFileItem>(reader)
                    .withType(CsvFileItem.class)
                    .withMappingStrategy(strategy)
                    .build();
            for (var csvRow : csv) {}
        }

        // Initialize a writer by using created strategy for preserving header order and data order accordingly
        try (var writer = Files.newBufferedWriter(Path.of(RESULT_FILE_NAME))) {
            var csv = new StatefulBeanToCsvBuilder<CsvFileItem>(writer)
                    .withMappingStrategy(strategy)
                    .withSeparator(CSV_SEPARATOR)
                    .withLineEnd("\n")
                    .withQuotechar('\u0000')
                    .build();
            csv.write(input.getInitialCsvFileContent());
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void addJointAreasToCsvItems(Input input) {
        var jointAreas = input.getActiveFarmJointAreas();
        for (var farmId : jointAreas.keySet()) {
            input.getInitialCsvFileContent().forEach(item -> {
                if (item.getId().equals(farmId)) {
                    item.setJointFarms(jointAreas.get(farmId).stream().map(CsvFileItem::getFarmNo).collect(Collectors.toList()));
                }
            });
        }
    }

    private String getHeaders() {
        final var HEADER = new String[]{"ID","POLD_NR","MAAALA_NR","MAAALA_POLLUD","MAAALA_KYLGNEVAD_POLLUD","POLD_PINDA","AKTIIVNE","MAAALA_POLLUD_TAOTLUSEL"};
        return String.join(",", HEADER);
    }
}
