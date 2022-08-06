package application.core;

import application.api.FilterJointFarmsByRules;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * This component checks if all connected farms have area bigger than 0.3 hectares and if so, dont use it
 * in our results
 */
@Component
class FilterJointFarmsByRulesImpl implements FilterJointFarmsByRules {

    private static final BigDecimal MIN_SIZE_ACCEPTABLE_FOR_APPLICATION = new BigDecimal("0.3");

    @Override
    public Output execute(Input input) {
        var filteredJointAreaMapByAreaSize = filterValuesByTotalArea(input);
        return Output.of(filteredJointAreaMapByAreaSize);
    }

    private Map<Long, List<CsvFileItem>> filterValuesByTotalArea(Input input) {
        var iterator = input.getActiveFarmJointAreas().entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next().getValue();
            // If all connected farms are bigger than 0.3 hectares (>= 0.3) then we are not gonna display them in our results
            var allFarmsAreBiggerThanMinimumAreaSize = entry.stream()
                    .allMatch(item -> item.getArea().compareTo(MIN_SIZE_ACCEPTABLE_FOR_APPLICATION) >= 0);
            if (allFarmsAreBiggerThanMinimumAreaSize) {
                iterator.remove();
            }
        }
        return input.getActiveFarmJointAreas();
    }
}
