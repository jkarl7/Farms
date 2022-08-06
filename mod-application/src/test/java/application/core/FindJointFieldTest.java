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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is testing farm connections. Test data is based on maaalad.png map
 */
class FindJointFieldTest {

    private FindJointField findJointField;

    @BeforeEach
    void before() {
        findJointField = new FindJointFieldImpl();
    }

    @DisplayName("GIVEN active farms: 24-3, 1-1, 1-2, 23-1 and 8-14 " +
            "WHEN joint field search is activated " +
            "THEN each farm should have connection to others")
    @Test
    void findJointFieldTest1() {
        Map<Long, List<CsvFileItem>> activeFarmsByFieldNumber = new HashMap<>();
        List<CsvFileItem> activeFarmsByFieldNumber2 = new ArrayList<>();
        var fieldFarm1 = new CsvFileItem();
        fieldFarm1.setId(20448901L);
        fieldFarm1.setFieldNo(2L);
        fieldFarm1.setFarmNo("24-3");
        List<Long> fieldFarms = new ArrayList<>();
        fieldFarms.add(8600816L);
        fieldFarms.add(8600814L);
        fieldFarms.add(19200601L);
        fieldFarms.add(20384401L);
        fieldFarm1.setFieldFarms(fieldFarms);
        List<Long> farmsNextToCurrentFarm = new ArrayList<>();
        farmsNextToCurrentFarm.add(8600816L);
        farmsNextToCurrentFarm.add(20384401L);
        fieldFarm1.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm);
        fieldFarm1.setIsActive(true);
        activeFarmsByFieldNumber2.add(fieldFarm1);

        var fieldFarm2 = new CsvFileItem();
        fieldFarm2.setId(8600814L);
        fieldFarm2.setFieldNo(2L);
        fieldFarm2.setFarmNo("1-1");
        List<Long> fieldFarms2 = new ArrayList<>();
        fieldFarms2.add(20448901L);
        fieldFarms2.add(8600816L);
        fieldFarms2.add(19200601L);
        fieldFarms2.add(20384401L);
        fieldFarm2.setFieldFarms(fieldFarms2);
        List<Long> farmsNextToCurrentFarm2 = new ArrayList<>();
        farmsNextToCurrentFarm2.add(8600816L);
        farmsNextToCurrentFarm2.add(19200601L);
        farmsNextToCurrentFarm2.add(20384401L);
        fieldFarm2.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm2);
        fieldFarm2.setIsActive(true);
        activeFarmsByFieldNumber2.add(fieldFarm2);

        var fieldFarm3 = new CsvFileItem();
        fieldFarm3.setId(8600816L);
        fieldFarm3.setFieldNo(2L);
        fieldFarm3.setFarmNo("1-2");
        List<Long> fieldFarms3 = new ArrayList<>();
        fieldFarms3.add(20448901L);
        fieldFarms3.add(8600814L);
        fieldFarms3.add(19200601L);
        fieldFarms3.add(20384401L);
        fieldFarm3.setFieldFarms(fieldFarms3);
        List<Long> farmsNextToCurrentFarm3 = new ArrayList<>();
        farmsNextToCurrentFarm3.add(8600814L);
        farmsNextToCurrentFarm3.add(20448901L);
        farmsNextToCurrentFarm3.add(20384401L);
        fieldFarm3.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm3);
        fieldFarm3.setIsActive(true);
        activeFarmsByFieldNumber2.add(fieldFarm3);

        var fieldFarm4 = new CsvFileItem();
        fieldFarm4.setId(19200601L);
        fieldFarm4.setFieldNo(2L);
        fieldFarm4.setFarmNo("23-1");
        List<Long> fieldFarms4 = new ArrayList<>();
        fieldFarms4.add(20448901L);
        fieldFarms4.add(8600816L);
        fieldFarms4.add(8600814L);
        fieldFarms4.add(20384401L);
        fieldFarm4.setFieldFarms(fieldFarms4);
        List<Long> farmsNextToCurrentFarm4 = new ArrayList<>();
        farmsNextToCurrentFarm4.add(8600822L);
        farmsNextToCurrentFarm4.add(8600802L);
        farmsNextToCurrentFarm4.add(8600814L);
        farmsNextToCurrentFarm4.add(20384401L);
        fieldFarm4.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm4);
        fieldFarm4.setIsActive(true);
        activeFarmsByFieldNumber2.add(fieldFarm4);

        var fieldFarm5 = new CsvFileItem();
        fieldFarm5.setId(20384401L);
        fieldFarm5.setFieldNo(2L);
        fieldFarm5.setFarmNo("8-14");
        List<Long> fieldFarms5 = new ArrayList<>();
        fieldFarms5.add(20448901L);
        fieldFarms5.add(8600816L);
        fieldFarms5.add(8600814L);
        fieldFarms5.add(19200601L);
        fieldFarm5.setFieldFarms(fieldFarms5);
        List<Long> farmsNextToCurrentFarm5 = new ArrayList<>();
        farmsNextToCurrentFarm5.add(8600822L);
        farmsNextToCurrentFarm5.add(8600802L);
        farmsNextToCurrentFarm5.add(18395901L);
        farmsNextToCurrentFarm5.add(8600816L);
        farmsNextToCurrentFarm5.add(19200601L);
        farmsNextToCurrentFarm5.add(8600814L);
        farmsNextToCurrentFarm5.add(8600824L);
        farmsNextToCurrentFarm5.add(20448901L);
        fieldFarm5.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm5);
        fieldFarm5.setIsActive(true);
        activeFarmsByFieldNumber2.add(fieldFarm5);

        activeFarmsByFieldNumber.put(2L, activeFarmsByFieldNumber2);

        var output = findJointField.execute(FindJointField.Input.of(activeFarmsByFieldNumber));
        assertEquals(5, output.getActiveFarmJointAreas().size());

        assertTrue(output.getActiveFarmJointAreas().containsKey(20448901L));
        assertTrue(output.getActiveFarmJointAreas().containsKey(8600814L));
        assertTrue(output.getActiveFarmJointAreas().containsKey(8600816L));
        assertTrue(output.getActiveFarmJointAreas().containsKey(19200601L));
        assertTrue(output.getActiveFarmJointAreas().containsKey(20384401L));

        assertTrue(output.getActiveFarmJointAreas().get(20448901L).stream().allMatch(item -> Arrays.asList(
                20448901L, 8600814L, 8600816L, 19200601L, 20384401L
        ).contains(item.getId())));
        assertTrue(output.getActiveFarmJointAreas().get(8600814L).stream().allMatch(item -> Arrays.asList(
                20448901L, 8600814L, 8600816L, 19200601L, 20384401L
        ).contains(item.getId())));
        assertTrue(output.getActiveFarmJointAreas().get(8600816L).stream().allMatch(item -> Arrays.asList(
                20448901L, 8600814L, 8600816L, 19200601L, 20384401L
        ).contains(item.getId())));
        assertTrue(output.getActiveFarmJointAreas().get(19200601L).stream().allMatch(item -> Arrays.asList(
                20448901L, 8600814L, 8600816L, 19200601L, 20384401L
        ).contains(item.getId())));
        assertTrue(output.getActiveFarmJointAreas().get(20384401L).stream().allMatch(item -> Arrays.asList(
                20448901L, 8600814L, 8600816L, 19200601L, 20384401L
        ).contains(item.getId())));
    }

    @DisplayName("GIVEN active farm numbers: 23-1, 8-14 and 24-3 " +
            "WHEN joint field search is activated " +
            "THEN they should form a connection with each other")
    @Test
    void findJointFieldTest2() {
        Map<Long, List<CsvFileItem>> activeFarmsByFieldNumber = new HashMap<>();
        List<CsvFileItem> activeFarmsByFieldNumber2 = new ArrayList<>();

        var fieldFarm1 = new CsvFileItem();
        fieldFarm1.setId(20448901L);
        fieldFarm1.setFieldNo(2L);
        fieldFarm1.setFarmNo("24-3");
        List<Long> fieldFarms = new ArrayList<>();
        fieldFarms.add(8600816L);
        fieldFarms.add(8600814L);
        fieldFarms.add(19200601L);
        fieldFarms.add(20384401L);
        fieldFarm1.setFieldFarms(fieldFarms);
        List<Long> farmsNextToCurrentFarm = new ArrayList<>();
        farmsNextToCurrentFarm.add(8600816L);
        farmsNextToCurrentFarm.add(20384401L);
        fieldFarm1.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm);
        fieldFarm1.setIsActive(true);
        activeFarmsByFieldNumber2.add(fieldFarm1);

        var fieldFarm4 = new CsvFileItem();
        fieldFarm4.setId(19200601L);
        fieldFarm4.setFieldNo(2L);
        fieldFarm4.setFarmNo("23-1");
        List<Long> fieldFarms4 = new ArrayList<>();
        fieldFarms4.add(20448901L);
        fieldFarms4.add(8600816L);
        fieldFarms4.add(8600814L);
        fieldFarms4.add(20384401L);
        fieldFarm4.setFieldFarms(fieldFarms4);
        List<Long> farmsNextToCurrentFarm4 = new ArrayList<>();
        farmsNextToCurrentFarm4.add(8600822L);
        farmsNextToCurrentFarm4.add(8600802L);
        farmsNextToCurrentFarm4.add(8600814L);
        farmsNextToCurrentFarm4.add(20384401L);
        fieldFarm4.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm4);
        fieldFarm4.setIsActive(true);
        activeFarmsByFieldNumber2.add(fieldFarm4);

        var fieldFarm5 = new CsvFileItem();
        fieldFarm5.setId(20384401L);
        fieldFarm5.setFieldNo(2L);
        fieldFarm5.setFarmNo("8-14");
        List<Long> fieldFarms5 = new ArrayList<>();
        fieldFarms5.add(20448901L);
        fieldFarms5.add(8600816L);
        fieldFarms5.add(8600814L);
        fieldFarms5.add(19200601L);
        fieldFarm5.setFieldFarms(fieldFarms5);
        List<Long> farmsNextToCurrentFarm5 = new ArrayList<>();
        farmsNextToCurrentFarm5.add(8600822L);
        farmsNextToCurrentFarm5.add(8600802L);
        farmsNextToCurrentFarm5.add(18395901L);
        farmsNextToCurrentFarm5.add(8600816L);
        farmsNextToCurrentFarm5.add(19200601L);
        farmsNextToCurrentFarm5.add(8600814L);
        farmsNextToCurrentFarm5.add(8600824L);
        farmsNextToCurrentFarm5.add(20448901L);
        fieldFarm5.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm5);
        fieldFarm5.setIsActive(true);
        activeFarmsByFieldNumber2.add(fieldFarm5);

        activeFarmsByFieldNumber.put(2L, activeFarmsByFieldNumber2);

        var output = findJointField.execute(FindJointField.Input.of(activeFarmsByFieldNumber));
        assertEquals(3, output.getActiveFarmJointAreas().size());

        assertTrue(output.getActiveFarmJointAreas().containsKey(20448901L));
        assertTrue(output.getActiveFarmJointAreas().containsKey(19200601L));
        assertTrue(output.getActiveFarmJointAreas().containsKey(20384401L));

        assertTrue(output.getActiveFarmJointAreas().get(20448901L).stream().allMatch(item -> Arrays.asList(
                20448901L, 19200601L, 20384401L
        ).contains(item.getId())));
        assertTrue(output.getActiveFarmJointAreas().get(19200601L).stream().allMatch(item -> Arrays.asList(
                19200601L, 20448901L, 20384401L
        ).contains(item.getId())));
        assertTrue(output.getActiveFarmJointAreas().get(20384401L).stream().allMatch(item -> Arrays.asList(
                20384401L, 20448901L, 19200601L
        ).contains(item.getId())));
    }

    @DisplayName("GIVEN active farm numbers: 24-3 and 1-1" +
            "WHEN joint field search is activated " +
            "THEN they themselves are only farm and not connected with anything else")
    @Test
    void findJointFieldTest3() {
        Map<Long, List<CsvFileItem>> activeFarmsByFieldNumber = new HashMap<>();
        List<CsvFileItem> activeFarmsByFieldNumber2 = new ArrayList<>();

        var fieldFarm1 = new CsvFileItem();
        fieldFarm1.setId(20448901L);
        fieldFarm1.setFieldNo(2L);
        fieldFarm1.setFarmNo("24-3");
        List<Long> fieldFarms = new ArrayList<>();
        fieldFarms.add(8600816L);
        fieldFarms.add(8600814L);
        fieldFarms.add(19200601L);
        fieldFarms.add(20384401L);
        fieldFarm1.setFieldFarms(fieldFarms);
        List<Long> farmsNextToCurrentFarm = new ArrayList<>();
        farmsNextToCurrentFarm.add(8600816L);
        farmsNextToCurrentFarm.add(20384401L);
        fieldFarm1.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm);
        fieldFarm1.setIsActive(true);
        activeFarmsByFieldNumber2.add(fieldFarm1);

        var fieldFarm2 = new CsvFileItem();
        fieldFarm2.setId(8600814L);
        fieldFarm2.setFieldNo(2L);
        fieldFarm2.setFarmNo("1-1");
        List<Long> fieldFarms2 = new ArrayList<>();
        fieldFarms2.add(20448901L);
        fieldFarms2.add(8600816L);
        fieldFarms2.add(19200601L);
        fieldFarms2.add(20384401L);
        fieldFarm2.setFieldFarms(fieldFarms2);
        List<Long> farmsNextToCurrentFarm2 = new ArrayList<>();
        farmsNextToCurrentFarm2.add(8600816L);
        farmsNextToCurrentFarm2.add(19200601L);
        farmsNextToCurrentFarm2.add(20384401L);
        fieldFarm2.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm2);
        fieldFarm2.setIsActive(true);
        activeFarmsByFieldNumber2.add(fieldFarm2);

        activeFarmsByFieldNumber.put(2L, activeFarmsByFieldNumber2);
        var output = findJointField.execute(FindJointField.Input.of(activeFarmsByFieldNumber));
        assertEquals(2, output.getActiveFarmJointAreas().size());

        assertTrue(output.getActiveFarmJointAreas().containsKey(20448901L));
        assertTrue(output.getActiveFarmJointAreas().containsKey(8600814L));

        assertTrue(output.getActiveFarmJointAreas().get(20448901L).stream().allMatch(item -> Objects.equals(20448901L, item.getId())));
        assertTrue(output.getActiveFarmJointAreas().get(8600814L).stream().allMatch(item -> Objects.equals(8600814L, item.getId())));
    }
}
