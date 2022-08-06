package application.core;

import application.api.FindJointField;
import application.core.comparator.CustomStringNumberComparator;
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

            // Loop over every active farm in field and find which other farms they are connected to
            for (var item : activeFarmFields) {
                // Keep a queue where we will keep track of the farm for which we look other farms that are next to it
                Queue<CsvFileItem> itemsToBeSearchedNext = new LinkedList<>();
                // add initial active farm which is a starting point for out search
                itemsToBeSearchedNext.add(item);
                List<CsvFileItem> jointAreas = new ArrayList<>();
                // Found farms next to each other, also sort them by farm number (26-1, 7, 29-5 etc)
                var adjacentFarms = getAdjacentFarms(itemsToBeSearchedNext, activeFarmFields, new ArrayList<>(), jointAreas).stream()
                        .sorted(Comparator.comparing(CsvFileItem::getFarmNo, new CustomStringNumberComparator()))
                        .collect(Collectors.toList());
                // let's also add the farm we searched farms next to it (so it is easier to make certain checks about this connection)
                // later when printing connected farms, we dont display this one
                adjacentFarms.add(item);
                activeFarmJointAreas.put(item.getId(), adjacentFarms);
            }
        }
        return Output.of(activeFarmJointAreas);
    }

    /**
     * We use recursion to find all connected farms
     *
     * @param itemsToBeSearchedNext - Keeps a queue which farm item will be searched for next
     * @param allActiveFieldFarms   - Constant variable that keeps info about all active farms in field (fieldNo 1 for example)
     * @param idToIgnoreFromSearch  - We keep track of farm IDs that have been already processed
     * @param jointAreas            - List which starts containing farms which form a connection between active farms
     * @return                      - A complete list of active farms in a field that form a connection between each other
     */
    private List<CsvFileItem> getAdjacentFarms(
            Queue<CsvFileItem> itemsToBeSearchedNext,
            final List<CsvFileItem> allActiveFieldFarms,
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
}
