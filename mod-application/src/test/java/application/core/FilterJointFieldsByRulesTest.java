package application.core;

import application.api.FilterJointFieldsByRules;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
class FilterJointFieldsByRulesTest {

    private FilterJointFieldsByRules filterJointFieldsByRules;

    @BeforeEach
    void before() {
            filterJointFieldsByRules = new FilterJointFieldsByRulesImpl();
    }

    @Test
    void test1() {
        var item1 = new CsvFileItem();
        item1.setId(1L);
        item1.setFarmNo("55");
        item1.setFieldNo(1L);
        List<Long> fieldFarms = new ArrayList<>();
        fieldFarms.add(7L);
        fieldFarms.add(8L);
        fieldFarms.add(9L);
        item1.setFieldFarms(fieldFarms);
        List<Long> farmsNextToCurrentFarm = new ArrayList<>();
        farmsNextToCurrentFarm.add(7L);
        farmsNextToCurrentFarm.add(8L);
        item1.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm);
        item1.setArea(new BigDecimal("0.29"));
        item1.setIsActive(true);

        var item2 = new CsvFileItem();
        item1.setId(1L);
        item1.setFarmNo("55");
        item1.setFieldNo(1L);
        List<Long> fieldFarms2 = new ArrayList<>();
        fieldFarms.add(7L);
        fieldFarms.add(8L);
        fieldFarms.add(9L);
        item1.setFieldFarms(fieldFarms2);
        List<Long> farmsNextToCurrentFarm2 = new ArrayList<>();
        farmsNextToCurrentFarm.add(7L);
        farmsNextToCurrentFarm.add(8L);
        item1.setFarmsNextToCurrentFarm(farmsNextToCurrentFarm2);
        item1.setArea(new BigDecimal("0.29"));
        item1.setIsActive(true);
        Map<Long, List<CsvFileItem>> map = new HashMap<>();
        var output = filterJointFieldsByRules.execute(FilterJointFieldsByRules.Input.of(map));

        System.out.println("LOL");
    }
}
