package farm;

import application.api.FilterJointFarmsByRules;
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
    private final FilterJointFarmsByRules filterJointFarmsByRules;
    private final WriteToCsvFile writeToCsvFile;
    private static final String DEFAULT_INPUT_FILE_NAME = "maaalad.csv";

    public void run(String[] args) {

        var csvItems = readFromFile.execute(ReadFromFile.Input.of(getInputFileName(args))); // get data from CSV file

        var activeAreas = findActiveFieldFarms
                .execute(FindActiveFieldFarms.Input.of(csvItems.getItems())); // let's get all active farms in fields

        // Let's find all farms and their connections to other farms
        var jointFarms = findJointField
                .execute(FindJointField.Input.of(activeAreas.getActiveFieldFarms()));

        // let's remove those connected farms which all have area greater than 0.3
        var filteredJointFarms = filterJointFarmsByRules
                .execute(FilterJointFarmsByRules.Input.of(jointFarms.getActiveFarmJointAreas()));

        // write result and generate CSV
        writeToCsvFile.execute(WriteToCsvFile.Input.builder()
                .activeFarmJointAreas(filteredJointFarms.getFilteredActiveFarmJointAreas())
                .initialCsvFileContent(csvItems.getItems())
                .build());
    }

    private String getInputFileName(String[] args) {
        if (args.length == 0) return DEFAULT_INPUT_FILE_NAME;
        return args[0];
    }
}
