package application.core;

import application.api.FindActiveFieldFarms;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is responsible finding only active farms within field (field 1, 2 etc). All other farms
 * that are inactive or do not belong to any field are removed. Simply because it is better to work with data that is necessary
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

        // Find all farms that do not belong to any field (farms that do not belong to field 1, 2)
        var farmsWithoutField = input.getItems().stream()
                .filter(item -> item.getFieldNo() == null)
                .map(CsvFileItem::getId)
                .collect(Collectors.toList());

        for (var fieldNo : allFieldNumbers) {
            var allFarmsByFieldNumber = getFieldFarmsByFieldNumber(input, fieldNo);
            activeFarms.put(fieldNo, getActiveFieldFarms(allFarmsByFieldNumber, farmsWithoutField));
        }
        return Output.of(activeFarms);
    }

    private List<CsvFileItem> getFieldFarmsByFieldNumber(Input input, Long fieldNo) {
        return input.getItems()
                .stream()
                .filter(csvFileItem -> Objects.equals(csvFileItem.getFieldNo(), fieldNo))
                .collect(Collectors.toList());
    }

    // We trim off everything that is useless. If active farm is surrounded by a farm that does not belong to field (1, 2) then we
    // remove it from it's farmNextToCurrentFarm list. Also we remove farms that do belong to field, but are inactive.
    private List<CsvFileItem> getActiveFieldFarms(List<CsvFileItem> allFarmsByFieldNumber, List<Long> farmsWithoutField) {
        var activeList = allFarmsByFieldNumber.stream().filter(CsvFileItem::getIsActive).collect(Collectors.toList());
        allFarmsByFieldNumber.removeAll(activeList);
        var inActiveList = allFarmsByFieldNumber.stream().map(CsvFileItem::getId).collect(Collectors.toList());

        for (var activeItem : activeList) {
            activeItem.getFieldFarms().removeIf(inActiveList::contains);
            activeItem.getFarmsNextToCurrentFarm().removeIf(item -> inActiveList.contains(item) || farmsWithoutField.contains(item));
        }
        return activeList;
    }
}
