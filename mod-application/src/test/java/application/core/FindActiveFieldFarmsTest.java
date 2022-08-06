package application.core;

import application.api.FindActiveFieldFarms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindActiveFieldFarmsTest {

    private FindActiveFieldFarms findActiveFieldFarms;

    @BeforeEach
    void before() {
        findActiveFieldFarms = new FindActiveFieldFarmsImpl();
    }

    @DisplayName("GIVEN three farms, two are active one is inactive WHEN active field farm search is activated " +
            "THEN only two actives should be left")
    @Test
    void filterAllActiveFarmsTest() {
        List<CsvFileItem> items = new ArrayList<>();
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
        items.add(fieldFarm1);

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
        items.add(fieldFarm2);

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
        items.add(fieldFarm3);

        var output = findActiveFieldFarms.execute(FindActiveFieldFarms.Input.of(items));
        assertEquals(1, output.getActiveFieldFarms().size()); // Map size
        assertEquals(2, output.getActiveFieldFarms().get(1L).size()); // List size

//        assertEquals(1, output.getActiveFieldFarms().get(1L).get(0).getFieldFarms().size());
        assertTrue(output.getActiveFieldFarms().get(1L).get(0).getFieldFarms().contains(2L));

//        assertEquals(1, output.getActiveFieldFarms().get(1L).get(0).getFarmsNextToCurrentFarm().size());
        assertTrue(output.getActiveFieldFarms().get(1L).get(0).getFarmsNextToCurrentFarm().contains(2L));

   //     assertEquals(1, output.getActiveFieldFarms().get(1L).get(1).getFieldFarms().size());
        assertTrue(output.getActiveFieldFarms().get(1L).get(1).getFieldFarms().contains(1L));

//        assertEquals(1, output.getActiveFieldFarms().get(1L).get(1).getFarmsNextToCurrentFarm().size());
        assertTrue(output.getActiveFieldFarms().get(1L).get(1).getFarmsNextToCurrentFarm().contains(1L));
    }

    @DisplayName("GIVEN three farms, all active but one does not belong to the field WHEN active field farm search is activated " +
            "THEN only two actives should be left")
    @Test
    void filterAllActiveFarmsTest2() {
        List<CsvFileItem> items = new ArrayList<>();
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
        items.add(fieldFarm1);

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
        items.add(fieldFarm2);

        var fieldFarm3 = new CsvFileItem();
        fieldFarm3.setId(3L);
        fieldFarm3.setFieldNo(null);
        fieldFarm3.setIsActive(true);
        items.add(fieldFarm3);

        var output = findActiveFieldFarms.execute(FindActiveFieldFarms.Input.of(items));
        assertEquals(1, output.getActiveFieldFarms().size()); // Map size
        assertEquals(2, output.getActiveFieldFarms().get(1L).size()); // List size

        assertEquals(1, output.getActiveFieldFarms().get(1L).get(0).getFieldFarms().size());
        assertTrue(output.getActiveFieldFarms().get(1L).get(0).getFieldFarms().contains(2L));

     //   assertEquals(1, output.getActiveFieldFarms().get(1L).get(0).getFarmsNextToCurrentFarm().size());
        assertTrue(output.getActiveFieldFarms().get(1L).get(0).getFarmsNextToCurrentFarm().contains(2L));

        assertEquals(1, output.getActiveFieldFarms().get(1L).get(1).getFieldFarms().size());
        assertTrue(output.getActiveFieldFarms().get(1L).get(1).getFieldFarms().contains(1L));

//        assertEquals(1, output.getActiveFieldFarms().get(1L).get(1).getFarmsNextToCurrentFarm().size());
        assertTrue(output.getActiveFieldFarms().get(1L).get(1).getFarmsNextToCurrentFarm().contains(1L));
    }
}
