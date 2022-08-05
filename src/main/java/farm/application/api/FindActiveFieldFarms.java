package farm.application.api;

import farm.application.Usecase;
import farm.application.core.CsvFileItem;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.Map;

public interface FindActiveFieldFarms extends Usecase<FindActiveFieldFarms.Input, FindActiveFieldFarms.Output> {

    @Data
    @Value(staticConstructor = "of")
    class Input {
        List<CsvFileItem> items;
    }

    @Data
    @Value(staticConstructor = "of")
    class Output {
        Map<Long, List<CsvFileItem>> activeFieldFarms;
    }

}
