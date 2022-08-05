package application.core;

import application.api.FilterJointFarmsByRules;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * This component removes all joint areas that has total area size <= 0.3 hectares
 */
@Component
class FilterJointFarmsByRulesImpl implements FilterJointFarmsByRules {

    private static final BigDecimal MINIMUM_TOTAL_AREA_SIZE_LIMIT = new BigDecimal("0.3");

    @Override
    public Output execute(Input input) {
        var filteredJointAreaMapByAreaSize = filterValuesByTotalArea(input);
        return Output.of(filteredJointAreaMapByAreaSize);
    }

    private Map<Long, List<CsvFileItem>> filterValuesByTotalArea(Input input) {
        var iterator = input.getActiveFarmJointAreas().entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next().getValue();
            var areaSum = entry.stream()
                    .map(CsvFileItem::getArea)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (areaSum.compareTo(MINIMUM_TOTAL_AREA_SIZE_LIMIT) <= 0) {
                iterator.remove();
            }
        }
        return input.getActiveFarmJointAreas();
    }
}
