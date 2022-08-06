package application.api;

import application.Usecase;
import application.core.CsvFileItem;
import lombok.Value;

import java.util.List;
import java.util.Map;

public interface FindJointField extends Usecase<FindJointField.Input, FindJointField.Output> {

    @Value(staticConstructor = "of")
    class Input {
        Map<Long, List<CsvFileItem>> activeFarmsByFieldNumber;
    }

    @Value(staticConstructor = "of")
    class Output {
        Map<Long, List<CsvFileItem>> activeFarmJointAreas;
    }
}
