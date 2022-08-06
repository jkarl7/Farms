package application.core;

import application.api.FindActiveFieldFarms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindActiveFieldFarmsTest {

    private FindActiveFieldFarms findActiveFieldFarms;

    @BeforeEach
    void before() {
        findActiveFieldFarms = new FindActiveFieldFarmsImpl();
    }

    @DisplayName("GIVEN three farms, two are active and one is inactive " +
            "WHEN active field farm search is activated " +
            "THEN only two actives should be left")
    @Test
    void filterAllActiveFarmsTest() {
        List<CsvFileItem> allFarmsOnMap = new ArrayList<>();
        var fieldFarm1 = new CsvFileItem();
        fieldFarm1.setId(1L);
        fieldFarm1.setFieldNo(1L);
        List<Long> fieldFarms = new ArrayList<>();
        fieldFarms.add(2L);
        fieldFarms.add(3L);
        fieldFarm1.setFieldFarms(fieldFarms);
        List<Long> farmsNextToCurrentFarm = new ArrayList<>();
        farmsNextToCurrentFarm.add(2L);
        farmsNextToCurrentFarm.add(3L);
        fieldFarm1.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm);
        fieldFarm1.setIsActive(true);
        allFarmsOnMap.add(fieldFarm1);

        var fieldFarm2 = new CsvFileItem();
        fieldFarm2.setId(2L);
        fieldFarm2.setFieldNo(1L);
        List<Long> fieldFarms2 = new ArrayList<>();
        fieldFarms2.add(1L);
        fieldFarms2.add(3L);
        fieldFarm2.setFieldFarms(fieldFarms2);
        List<Long> farmsNextToCurrentFarm2 = new ArrayList<>();
        farmsNextToCurrentFarm2.add(1L);
        farmsNextToCurrentFarm2.add(3L);
        fieldFarm2.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm2);
        fieldFarm2.setIsActive(true);
        allFarmsOnMap.add(fieldFarm2);

        var fieldFarm3 = new CsvFileItem();
        fieldFarm3.setId(3L);
        fieldFarm3.setFieldNo(1L);
        List<Long> fieldFarms3 = new ArrayList<>();
        fieldFarms3.add(1L);
        fieldFarms3.add(2L);
        fieldFarm3.setFieldFarms(fieldFarms3);
        List<Long> farmsNextToCurrentFarm3 = new ArrayList<>();
        farmsNextToCurrentFarm3.add(1L);
        farmsNextToCurrentFarm3.add(2L);
        fieldFarm3.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm3);
        fieldFarm3.setIsActive(false);
        allFarmsOnMap.add(fieldFarm3);

        var output = findActiveFieldFarms.execute(FindActiveFieldFarms.Input.of(allFarmsOnMap));
        assertEquals(1, output.getActiveFieldFarms().size()); // Map size
        assertEquals(2, output.getActiveFieldFarms().get(1L).size()); // List size

        assertTrue(output.getActiveFieldFarms().get(1L).get(0).getFieldFarms().containsAll(new ArrayList<>(Arrays.asList(2L, 3L))));
        assertTrue(output.getActiveFieldFarms().get(1L).get(0).getFarmsNextToCurrentFarm().containsAll(new ArrayList<>(Arrays.asList(2L, 3L))));

        assertTrue(output.getActiveFieldFarms().get(1L).get(1).getFieldFarms().containsAll(new ArrayList<>(Arrays.asList(1L, 3L))));
        assertTrue(output.getActiveFieldFarms().get(1L).get(1).getFarmsNextToCurrentFarm().containsAll(new ArrayList<>(Arrays.asList(1L, 3L))));
    }

    @DisplayName("GIVEN three farms, all active but one does not belong to the field " +
            "WHEN active field farm search is activated " +
            "THEN only two actives should be left")
    @Test
    void filterAllActiveFarmsTest2() {
        List<CsvFileItem> allFarmsOnMap = new ArrayList<>();
        var fieldFarm1 = new CsvFileItem();
        fieldFarm1.setId(1L);
        fieldFarm1.setFieldNo(1L);
        List<Long> fieldFarms = new ArrayList<>();
        fieldFarms.add(2L);
        fieldFarm1.setFieldFarms(fieldFarms);
        List<Long> farmsNextToCurrentFarm = new ArrayList<>();
        farmsNextToCurrentFarm.add(2L);
        farmsNextToCurrentFarm.add(3L);
        fieldFarm1.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm);
        fieldFarm1.setIsActive(true);
        allFarmsOnMap.add(fieldFarm1);

        var fieldFarm2 = new CsvFileItem();
        fieldFarm2.setId(2L);
        fieldFarm2.setFieldNo(1L);
        List<Long> fieldFarms2 = new ArrayList<>();
        fieldFarms2.add(1L);
        fieldFarm2.setFieldFarms(fieldFarms2);
        List<Long> farmsNextToCurrentFarm2 = new ArrayList<>();
        farmsNextToCurrentFarm2.add(1L);
        farmsNextToCurrentFarm2.add(3L);
        fieldFarm2.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm2);
        fieldFarm2.setIsActive(true);
        allFarmsOnMap.add(fieldFarm2);

        var fieldFarm3 = new CsvFileItem();
        fieldFarm3.setId(3L);
        fieldFarm3.setFieldNo(null);
        List<Long> farmsNextToCurrentFarm3 = new ArrayList<>();
        farmsNextToCurrentFarm3.add(1L);
        farmsNextToCurrentFarm3.add(2L);
        fieldFarm3.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm3);
        fieldFarm3.setIsActive(true);
        allFarmsOnMap.add(fieldFarm3);

        var output = findActiveFieldFarms.execute(FindActiveFieldFarms.Input.of(allFarmsOnMap));
        assertEquals(1, output.getActiveFieldFarms().size()); // Map size (contains only field number 1 active areas)
        assertEquals(2, output.getActiveFieldFarms().get(1L).size()); // List size

        assertEquals(1, output.getActiveFieldFarms().get(1L).get(0).getFieldFarms().size());
        assertTrue(output.getActiveFieldFarms().get(1L).get(0).getFieldFarms().containsAll(new ArrayList<>(Collections.singletonList(2L))));
        assertTrue(output.getActiveFieldFarms().get(1L).get(0).getFarmsNextToCurrentFarm().containsAll(new ArrayList<>(Arrays.asList(2L, 3L))));

        assertEquals(1, output.getActiveFieldFarms().get(1L).get(1).getFieldFarms().size());
        assertTrue(output.getActiveFieldFarms().get(1L).get(1).getFieldFarms().containsAll(new ArrayList<>(Collections.singletonList(1L))));
        assertTrue(output.getActiveFieldFarms().get(1L).get(1).getFarmsNextToCurrentFarm().containsAll(new ArrayList<>(Arrays.asList(1L, 3L))));
    }

    @DisplayName("GIVEN two fields, one has 2 active lands and 1 inactive land, other 3 active lands and one is without field " +
            "WHEN active field farm search is activated " +
            "THEN all active field land should be returned only")
    @Test
    void filterAllActiveFarmsTest3() {
        // Field number 1 farms
        List<CsvFileItem> allFarmsOnMap = new ArrayList<>();
        var fieldFarm1 = new CsvFileItem();
        fieldFarm1.setId(1L);
        fieldFarm1.setFieldNo(1L);
        List<Long> fieldFarms = new ArrayList<>();
        fieldFarms.add(2L);
        fieldFarms.add(3L);
        fieldFarm1.setFieldFarms(fieldFarms);
        List<Long> farmsNextToCurrentFarm = new ArrayList<>();
        farmsNextToCurrentFarm.add(2L);
        farmsNextToCurrentFarm.add(6L);
        fieldFarm1.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm);
        fieldFarm1.setIsActive(true);
        allFarmsOnMap.add(fieldFarm1);

        var fieldFarm2 = new CsvFileItem();
        fieldFarm2.setId(2L);
        fieldFarm2.setFieldNo(1L);
        List<Long> fieldFarms2 = new ArrayList<>();
        fieldFarms2.add(1L);
        fieldFarms2.add(3L);
        fieldFarm2.setFieldFarms(fieldFarms2);
        List<Long> farmsNextToCurrentFarm2 = new ArrayList<>();
        farmsNextToCurrentFarm2.add(1L);
        farmsNextToCurrentFarm2.add(3L);
        fieldFarm2.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm2);
        fieldFarm2.setIsActive(true);
        allFarmsOnMap.add(fieldFarm2);

        var fieldFarm3 = new CsvFileItem();
        fieldFarm3.setId(3L);
        fieldFarm3.setFieldNo(1L);
        List<Long> fieldFarms3 = new ArrayList<>();
        fieldFarms3.add(2L);
        fieldFarms3.add(3L);
        fieldFarm3.setFieldFarms(fieldFarms3);
        List<Long> farmsNextToCurrentFarm3 = new ArrayList<>();
        farmsNextToCurrentFarm3.add(2L);
        fieldFarm3.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm3);
        fieldFarm3.setIsActive(false);
        allFarmsOnMap.add(fieldFarm3);

        // Field number 2 farms
        var fieldFarm4 = new CsvFileItem();
        fieldFarm4.setId(4L);
        fieldFarm4.setFieldNo(2L);
        List<Long> fieldFarms4 = new ArrayList<>();
        fieldFarms4.add(5L);
        fieldFarm4.setFieldFarms(fieldFarms4);
        List<Long> farmsNextToCurrentFarm4 = new ArrayList<>();
        farmsNextToCurrentFarm4.add(6L);
        farmsNextToCurrentFarm4.add(5L);
        fieldFarm4.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm4);
        fieldFarm4.setIsActive(true);
        allFarmsOnMap.add(fieldFarm4);

        var fieldFarm5 = new CsvFileItem();
        fieldFarm5.setId(5L);
        fieldFarm5.setFieldNo(2L);
        List<Long> fieldFarms5 = new ArrayList<>();
        fieldFarms5.add(4L);
        fieldFarm5.setFieldFarms(fieldFarms5);
        List<Long> farmsNextToCurrentFarm5 = new ArrayList<>();
        farmsNextToCurrentFarm5.add(4L);
        fieldFarm5.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm5);
        fieldFarm5.setIsActive(true);
        allFarmsOnMap.add(fieldFarm5);

        // Non-field farm
        var fieldFarm6 = new CsvFileItem();
        fieldFarm6.setId(6L);
        fieldFarm6.setFieldNo(null);
        List<Long> farmsNextToCurrentFarm6 = new ArrayList<>();
        farmsNextToCurrentFarm6.add(1L);
        farmsNextToCurrentFarm6.add(4L);
        fieldFarm6.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm6);
        fieldFarm6.setIsActive(true);
        allFarmsOnMap.add(fieldFarm6);

        var output = findActiveFieldFarms.execute(FindActiveFieldFarms.Input.of(allFarmsOnMap));
        assertEquals(2, output.getActiveFieldFarms().size()); // Map size (contains field number 1, 2 active areas)
        assertEquals(2, output.getActiveFieldFarms().get(1L).size()); // List size for field nr 1
        assertEquals(2, output.getActiveFieldFarms().get(2L).size()); // List size for field nr 2

        assertTrue(output.getActiveFieldFarms().get(1L).get(0).getFieldFarms().containsAll(new ArrayList<>(Arrays.asList(2L, 3L))));
        assertTrue(output.getActiveFieldFarms().get(1L).get(1).getFieldFarms().containsAll(new ArrayList<>(Arrays.asList(1L, 3L))));

        assertTrue(output.getActiveFieldFarms().get(1L).get(0).getFarmsNextToCurrentFarm().containsAll(new ArrayList<>(Arrays.asList(2L, 6L))));
        assertTrue(output.getActiveFieldFarms().get(1L).get(1).getFarmsNextToCurrentFarm().containsAll(new ArrayList<>(Arrays.asList(1L, 3L))));

        assertTrue(output.getActiveFieldFarms().get(2L).get(0).getFieldFarms().containsAll(new ArrayList<>(Collections.singletonList(5L))));
        assertTrue(output.getActiveFieldFarms().get(2L).get(1).getFieldFarms().containsAll(new ArrayList<>(Collections.singletonList(4L))));

        assertTrue(output.getActiveFieldFarms().get(2L).get(0).getFarmsNextToCurrentFarm().containsAll(new ArrayList<>(Arrays.asList(5L, 6L))));
        assertTrue(output.getActiveFieldFarms().get(2L).get(1).getFarmsNextToCurrentFarm().containsAll(new ArrayList<>(Collections.singletonList(4L))));
    }

}
