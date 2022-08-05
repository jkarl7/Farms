package farm.application.core;

import farm.application.api.FindJointField;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

@Component
class FindJointFieldImpl implements FindJointField {

    @Override
    public Output execute(Input input) {

        Map<Long, List<CsvFileItem>> activeFarmJointAreas = new HashMap<>();
        for (var entry : input.getActiveFarmsByFieldNumber().entrySet()) {
            var activeFarmFields = entry.getValue();
            for (var item : activeFarmFields) {
                Queue<CsvFileItem> itemsToBeSearchedNext = new LinkedList<>();
                itemsToBeSearchedNext.add(item);
                List<CsvFileItem> jointAreas = new ArrayList<>();
                // Let's find current's active farm adjacent farms and also sort them by farm number (ascending order)
                var adjacentFarms = getAdjacentFarms(itemsToBeSearchedNext, activeFarmFields, new ArrayList<>(), jointAreas).stream()
                        .sorted(Comparator.comparing(CsvFileItem::getFarmNo, new FarmNumberComparator())).collect(Collectors.toList());
                System.out.println("FARMS: " + adjacentFarms);
                activeFarmJointAreas.put(item.getId(), adjacentFarms);
                System.out.println("<------------------------------------------------->");
            }
        }
        return Output.of(activeFarmJointAreas);
    }

    /**
     * We use recursion to find all connected farms
     * @param itemsToBeSearchedNext -
     * @param allActiveFieldFarms - Constant variable that keeps info about all active farms in field (fieldNo 1 for example)
     * @param idToIgnoreFromSearch - We keep track of farm IDs that have been already processed
     * @param jointAreas
     * @return
     */
    private List<CsvFileItem> getAdjacentFarms
    (
            Queue<CsvFileItem> itemsToBeSearchedNext,
            List<CsvFileItem> allActiveFieldFarms,
            List<Long> idToIgnoreFromSearch,
            List<CsvFileItem> jointAreas
    ) {
        // Let's keep track of farm ID-s that have been taken into account already
        idToIgnoreFromSearch.add(itemsToBeSearchedNext.element().getId());
        for (var id : itemsToBeSearchedNext.element().getFarmsNextToCurrentFarm()) {
            var farm = allActiveFieldFarms.stream().filter(land -> land.getId().equals(id)
                    && !idToIgnoreFromSearch.contains(id)).findAny();
            if (farm.isPresent()) {
                itemsToBeSearchedNext.add(farm.get());
                jointAreas.add(farm.get());
            }

        }
        // we processed previously found item, let's remove it from search
        itemsToBeSearchedNext.poll();
        if (itemsToBeSearchedNext.isEmpty()) return jointAreas;
        return getAdjacentFarms(itemsToBeSearchedNext, allActiveFieldFarms, idToIgnoreFromSearch, jointAreas);
    }

    class FarmNumberComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            if (o2.contains("-")) {
                return 1;
            } else if (o1.length() < o2.length()) {
                return -1;
            }
            return 0;
        }
    }
}
