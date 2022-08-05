package farm.application.api;

import farm.application.Usecase;
import farm.application.core.CsvFileItem;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.Map;

public interface FilterJointFieldsByRules extends Usecase<FilterJointFieldsByRules.Input, FilterJointFieldsByRules.Output> {

    @Data
    @Value(staticConstructor = "of")
    class Input {
        Map<Long, List<CsvFileItem>> activeFarmJointAreas;
    }

    @Data
    @Value(staticConstructor = "of")
    class Output {
        Map<Long, List<CsvFileItem>> filteredActiveFarmJointAreas;
    }
}
