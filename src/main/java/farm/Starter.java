package farm;

import application.api.FilterJointFieldsByRules;
import application.api.FindActiveFieldFarms;
import application.api.FindJointField;
import application.api.ReadFromFile;
import application.api.WriteToCsvFile;
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
        var csvItems = readFromFile.execute(ReadFromFile.Input.of("maaalad.csv")); // get data from CSV file

        var activeAreas = findActiveFieldFarms
                .execute(FindActiveFieldFarms.Input.of(csvItems.getItems())); // let's get all active farms in fields

        var input = FindJointField.Input.builder()
                .items(csvItems.getItems())
                .activeFarmsByFieldNumber(activeAreas.getActiveFieldFarms())
                .build();
        var jointFarms = findJointField.execute(input);

        var filteredJointFarms = filterJointFieldsByRules
                .execute(FilterJointFieldsByRules.Input.of(jointFarms.getActiveFarmJointAreas()));

        writeToCsvFile.execute(WriteToCsvFile.Input.builder()
                .activeFarmJointAreas(filteredJointFarms.getFilteredActiveFarmJointAreas())
                .initialCsvFileContent(csvItems.getItems())
                .build());
    }
}
