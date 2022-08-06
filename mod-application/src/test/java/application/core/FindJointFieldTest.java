package application.core;

import application.api.FindJointField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindJointFieldTest {

    private FindJointField findJointField;

    @BeforeEach
    void before() {
        findJointField = new FindJointFieldImpl();
    }

    @DisplayName("GIVEN three active farms under one field and they are all next to each other " +
            "WHEN joint field search is activated " +
            "THEN each farm should have connection to two others")
    @Test
    void findJointFieldTest1() {
        Map<Long, List<CsvFileItem>> activeFarmsByFieldNumber = new HashMap<>();
        List<CsvFileItem> activeFarmsByFieldNumber1 = new ArrayList<>();
        var fieldFarm1 = new CsvFileItem();
        fieldFarm1.setId(1L);
        fieldFarm1.setFieldNo(1L);
        fieldFarm1.setFarmNo("5");
        List<Long> fieldFarms = new ArrayList<>();
        fieldFarms.add(2L);
        fieldFarms.add(3L);
        fieldFarm1.setFieldFarms(fieldFarms);
        List<Long> farmsNextToCurrentFarm = new ArrayList<>();
        farmsNextToCurrentFarm.add(2L);
        farmsNextToCurrentFarm.add(3L);
        fieldFarm1.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm);
        fieldFarm1.setIsActive(true);
        activeFarmsByFieldNumber1.add(fieldFarm1);

        var fieldFarm2 = new CsvFileItem();
        fieldFarm2.setId(2L);
        fieldFarm2.setFieldNo(1L);
        fieldFarm2.setFarmNo("7");
        List<Long> fieldFarms2 = new ArrayList<>();
        fieldFarms2.add(1L);
        fieldFarms2.add(3L);
        fieldFarm2.setFieldFarms(fieldFarms2);
        List<Long> farmsNextToCurrentFarm2 = new ArrayList<>();
        farmsNextToCurrentFarm2.add(1L);
        farmsNextToCurrentFarm2.add(3L);
        fieldFarm2.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm2);
        fieldFarm2.setIsActive(true);
        activeFarmsByFieldNumber1.add(fieldFarm2);

        var fieldFarm3 = new CsvFileItem();
        fieldFarm3.setId(3L);
        fieldFarm3.setFieldNo(1L);
        fieldFarm3.setFarmNo("1");
        List<Long> fieldFarms3 = new ArrayList<>();
        fieldFarms3.add(2L);
        fieldFarms3.add(3L);
        fieldFarm3.setFieldFarms(fieldFarms3);
        List<Long> farmsNextToCurrentFarm3 = new ArrayList<>();
        farmsNextToCurrentFarm3.add(1L);
        farmsNextToCurrentFarm3.add(2L);
        fieldFarm3.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm3);
        fieldFarm3.setIsActive(true);
        activeFarmsByFieldNumber1.add(fieldFarm3);

        activeFarmsByFieldNumber.put(1L, activeFarmsByFieldNumber1);
        var output = findJointField.execute(FindJointField.Input.of(activeFarmsByFieldNumber));
        // We got three fields and each are connected to each other, so 3 elements must be
        assertEquals(3, output.getActiveFarmJointAreas().size());

        assertTrue(output.getActiveFarmJointAreas().containsKey(1L));
        assertTrue(output.getActiveFarmJointAreas().containsKey(2L));
        assertTrue(output.getActiveFarmJointAreas().containsKey(3L));

        var farmsNextToEachOther = output.getActiveFarmJointAreas();
        assertTrue(farmsNextToEachOther.get(1L).stream().allMatch(item -> Arrays.asList(1L, 2L, 3L).contains(item.getId())));
        assertTrue(farmsNextToEachOther.get(2L).stream().allMatch(item -> Arrays.asList(1L, 2L, 3L).contains(item.getId())));
        assertTrue(farmsNextToEachOther.get(3L).stream().allMatch(item -> Arrays.asList(1L, 2L, 3L).contains(item.getId())));
    }

    @DisplayName("GIVEN three active farms under one field and they are all next to each other " +
            "WHEN joint field search is activated " +
            "THEN each farm should have connection to two others")
    @Test
    void findJointFieldTest2() {
        Map<Long, List<CsvFileItem>> activeFarmsByFieldNumber = new HashMap<>();
        List<CsvFileItem> activeFarmsByFieldNumber1 = new ArrayList<>();
        var fieldFarm1 = new CsvFileItem();
        fieldFarm1.setId(1L);
        fieldFarm1.setFieldNo(1L);
        fieldFarm1.setFarmNo("5");
        List<Long> fieldFarms = new ArrayList<>();
        fieldFarms.add(2L);
        fieldFarms.add(3L);
        fieldFarm1.setFieldFarms(fieldFarms);
        List<Long> farmsNextToCurrentFarm = new ArrayList<>();
        farmsNextToCurrentFarm.add(2L);
        farmsNextToCurrentFarm.add(3L);
        fieldFarm1.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm);
        fieldFarm1.setIsActive(true);
        activeFarmsByFieldNumber1.add(fieldFarm1);
    }
}
