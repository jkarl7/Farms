package application.api;

import application.Usecase;
import application.core.CsvFileItem;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;

public interface ReadFromFile extends Usecase<ReadFromFile.Input, ReadFromFile.Output> {

    @Data
    @Value(staticConstructor = "of")
    class Input {
        String fileName;
    }

    @Builder
    @Data
    @Value(staticConstructor = "of")
    class Output {
        List<CsvFileItem> items;
    }
}
