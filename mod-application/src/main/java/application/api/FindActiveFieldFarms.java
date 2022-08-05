package application.api;

import application.Usecase;
import application.core.CsvFileItem;
import lombok.Value;

import java.util.List;
import java.util.Map;

public interface FindActiveFieldFarms extends Usecase<FindActiveFieldFarms.Input, FindActiveFieldFarms.Output> {

    @Value(staticConstructor = "of")
    class Input {
        List<CsvFileItem> items;
    }

    @Value(staticConstructor = "of")
    class Output {
        Map<Long, List<CsvFileItem>> activeFieldFarms;
    }

}
