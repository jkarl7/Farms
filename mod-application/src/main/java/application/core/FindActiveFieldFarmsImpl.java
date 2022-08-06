package application.core;

import application.api.FindActiveFieldFarms;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is responsible finding only active farms within field (field 1, 2 etc).
 */
@Component
class FindActiveFieldFarmsImpl implements FindActiveFieldFarms {

    @Override
    public Output execute(Input input) {
        Map<Long, List<CsvFileItem>> activeFarms = new HashMap<>();

        // Find all input file field numbers (maybe if more numbers than 1, 2)
        var allFieldNumbers = input.getItems().stream()
                .map(CsvFileItem::getFieldNo)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        for (var fieldNo : allFieldNumbers) {
            var allFarmsByFieldNumber = getFieldFarmsByFieldNumber(input, fieldNo);
            activeFarms.put(fieldNo, allFarmsByFieldNumber.stream()
                    .filter(CsvFileItem::getIsActive)
                    .collect(Collectors.toList()));
        }
        return Output.of(activeFarms);
    }

    private List<CsvFileItem> getFieldFarmsByFieldNumber(Input input, Long fieldNo) {
        return input.getItems()
                .stream()
                .filter(csvFileItem -> Objects.equals(csvFileItem.getFieldNo(), fieldNo))
                .collect(Collectors.toList());
    }
}
