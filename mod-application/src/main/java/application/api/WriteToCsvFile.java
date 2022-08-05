package application.api;

import application.Usecase;
import application.core.CsvFileItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

public interface WriteToCsvFile extends Usecase<WriteToCsvFile.Input, WriteToCsvFile.Output> {

    @Data
    @Builder
    class Input {
        Map<Long, List<CsvFileItem>> activeFarmJointAreas;
        List<CsvFileItem> initialCsvFileContent;
    }

    class Output {}
}
