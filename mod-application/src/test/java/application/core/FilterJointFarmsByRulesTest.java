package application.core;

import application.api.FilterJointFarmsByRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterJointFarmsByRulesTest {

    private FilterJointFarmsByRules filterJointFarmsByRules;

    @BeforeEach
    void before() {
            filterJointFarmsByRules = new FilterJointFarmsByRulesImpl();
    }

    @DisplayName("GIVEN two connected farms that both have 0.29 hectares area WHEN area filter is applied " +
            "THEN this joint area should be valid")
//    @Test
    void validJointAreaTest() {
        var item1 = new CsvFileItem();
        item1.setId(1L);
        item1.setFarmNo("55");
        item1.setFieldNo(1L);
        item1.setArea(new BigDecimal("0.29"));
        item1.setIsActive(true);

        var item2 = new CsvFileItem();
        item2.setId(2L);
        item2.setFarmNo("55-1");
        item2.setFieldNo(1L);
        item2.setArea(new BigDecimal("0.29"));
        item2.setIsActive(true);

        List<CsvFileItem> allItems = new ArrayList<>();
        allItems.add(item1);
        allItems.add(item2);

        Map<Long, List<CsvFileItem>> map = new HashMap<>();
        map.put(item1.getId(), allItems);

        var output = filterJointFarmsByRules.execute(FilterJointFarmsByRules.Input.of(map));
        assertEquals(1, output.getFilteredActiveFarmJointAreas().size());
        assertEquals("55", output.getFilteredActiveFarmJointAreas().get(item1.getId()).get(0).getFarmNo());
        assertEquals("55-1", output.getFilteredActiveFarmJointAreas().get(item1.getId()).get(1).getFarmNo());
    }

    @DisplayName("GIVEN two connected farms (one has area 0.30 and other 0.1) hectares area WHEN area filter is applied " +
            "THEN this joint area should be valid")
 //   @Test
    void validJointAreaTest2() {
        var item1 = new CsvFileItem();
        item1.setId(1L);
        item1.setFarmNo("55");
        item1.setFieldNo(1L);
        item1.setArea(new BigDecimal("0.30"));
        item1.setIsActive(true);

        var item2 = new CsvFileItem();
        item2.setId(2L);
        item2.setFarmNo("55-1");
        item2.setFieldNo(1L);
        item2.setArea(new BigDecimal("0.01"));
        item2.setIsActive(true);

        List<CsvFileItem> allItems = new ArrayList<>();
        allItems.add(item1);
        allItems.add(item2);

        Map<Long, List<CsvFileItem>> map = new HashMap<>();
        map.put(item1.getId(), allItems);

        var output = filterJointFarmsByRules.execute(FilterJointFarmsByRules.Input.of(map));
        assertEquals(1, output.getFilteredActiveFarmJointAreas().size());
        assertEquals("55", output.getFilteredActiveFarmJointAreas().get(item1.getId()).get(0).getFarmNo());
        assertEquals("55-1", output.getFilteredActiveFarmJointAreas().get(item1.getId()).get(1).getFarmNo());
    }

    @DisplayName("GIVEN two connected farms (one has area 0.29 and other 0.1) hectares area WHEN area filter is applied " +
            "THEN this joint area should be invalid")
 //   @Test
    void invalidJointAreaTest() {
        var item1 = new CsvFileItem();
        item1.setId(1L);
        item1.setFarmNo("55");
        item1.setFieldNo(1L);
        item1.setArea(new BigDecimal("0.29"));
        item1.setIsActive(true);

        var item2 = new CsvFileItem();
        item2.setId(2L);
        item2.setFarmNo("55-1");
        item2.setFieldNo(1L);
        item2.setArea(new BigDecimal("0.01"));
        item2.setIsActive(true);

        List<CsvFileItem> allItems = new ArrayList<>();
        allItems.add(item1);
        allItems.add(item2);

        Map<Long, List<CsvFileItem>> map = new HashMap<>();
        map.put(item1.getId(), allItems);

        var output = filterJointFarmsByRules.execute(FilterJointFarmsByRules.Input.of(map));
        assertEquals(0, output.getFilteredActiveFarmJointAreas().size());
    }
}
