package farm.application;

import farm.application.api.FilterJointFieldsByRules;
import farm.application.api.FindActiveFieldFarms;
import farm.application.api.FindJointField;
import farm.application.api.ReadFromFile;
import farm.application.api.WriteToCsvFile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Starter {

    private final ReadFromFile readFromFile;
    private final FindJointField findJointField;
    private final FindActiveFieldFarms findActiveFieldFarms;
    private final FilterJointFieldsByRules filterJointFieldsByRules;
    private final WriteToCsvFile writeToCsvFile;

    public void run(String[] args) {
      //  System.out.println("Input file: " + args[0]);

        var csvItems = readFromFile.execute(ReadFromFile.Input.of("maaalad.csv")); // get data from CSV file
        var activeAreas = findActiveFieldFarms
                .execute(FindActiveFieldFarms.Input.of(csvItems.getItems())); // let's get all active farms in fields
        var jointFarms = findJointField.execute(FindJointField.Input.of(csvItems.getItems(), activeAreas.getActiveFieldFarms()));
        var filteredJointFarms = filterJointFieldsByRules.execute(FilterJointFieldsByRules.Input.of(jointFarms.getActiveFarmJointAreas()));
        writeToCsvFile.execute(WriteToCsvFile.Input.builder()
                .activeFarmJointAreas(filteredJointFarms.getFilteredActiveFarmJointAreas())
                .initialCsvFileContent(csvItems.getItems())
                .build());
    }
}
