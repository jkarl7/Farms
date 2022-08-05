package farm.application.core;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CsvFileItem {

    @CsvBindByName(column = "ID")
    private Long id;

    @CsvBindByName(column = "POLD_NR")
    private String farmNo;

    @CsvBindByName(column = "MAAALA_NR")
    private Long fieldNo;

    @CsvBindAndSplitByName(column = "MAAALA_POLLUD", elementType = Long.class, splitOn = ",", writeDelimiter = ",", collectionType = List.class)
    private List<Long> fieldFarms;

    @CsvBindAndSplitByName(column = "MAAALA_KYLGNEVAD_POLLUD", elementType = Long.class, splitOn = ",", writeDelimiter = ",")
    private List<Long> farmsNextToCurrentFarm;

    @CsvBindByName(column = "POLD_PINDA", locale = "de-DE")
    private BigDecimal area;

    @CsvBindByName(column = "AKTIIVNE")
    private Boolean isActive;

    /**
     * We fill this list after we have found out which farms are connected to the current farm land
     */
    @CsvBindAndSplitByName(column = "MAAALA_POLLUD_TAOTLUSEL", elementType = String.class, splitOn = ",", writeDelimiter = ",")
    private List<String> jointFarms;
}
