package application.api;

import application.Usecase;
import application.core.CsvFileItem;
import lombok.Value;

import java.util.List;
import java.util.Map;

public interface FilterJointFieldsByRules extends Usecase<FilterJointFieldsByRules.Input, FilterJointFieldsByRules.Output> {

    @Value(staticConstructor = "of")
    class Input {
        Map<Long, List<CsvFileItem>> activeFarmJointAreas;
    }

    @Value(staticConstructor = "of")
    class Output {
        Map<Long, List<CsvFileItem>> filteredActiveFarmJointAreas;
    }
}
