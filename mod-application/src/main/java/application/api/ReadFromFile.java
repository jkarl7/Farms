package application.api;

import application.Usecase;
import application.core.CsvFileItem;
import lombok.Value;

import java.util.List;

public interface ReadFromFile extends Usecase<ReadFromFile.Input, ReadFromFile.Output> {

    @Value(staticConstructor = "of")
    class Input {
        String fileName;
    }

    @Value(staticConstructor = "of")
    class Output {
        List<CsvFileItem> items;
    }
}
