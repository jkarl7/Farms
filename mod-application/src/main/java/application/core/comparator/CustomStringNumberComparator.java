package application.core.comparator;

import java.util.Comparator;

public class CustomStringNumberComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        var firstNumber = Integer.parseInt(o1.contains("-") ? o1.substring(0, o1.indexOf('-')) : o1);
        var secondNumber = Integer.parseInt(o2.contains("-") ? o2.substring(0, o2.indexOf('-')) : o2);
        var firstNumberSubPart = o1.contains("-") ? o1.substring(o1.indexOf('-') + 1) : null;
        var secondNumberSubPart = o2.contains("-") ? o2.substring(o2.indexOf('-') + 1) : null;

        if (firstNumber < secondNumber) {
            return -1;
        } else if (firstNumber > secondNumber) {
            return 1;
        } else {
            if (firstNumberSubPart == null && secondNumberSubPart == null) return 0;
            if ((firstNumberSubPart != null && secondNumberSubPart != null)
                    && Integer.parseInt(firstNumberSubPart) > Integer.parseInt(secondNumberSubPart)) {
                return 1;
            } else if ((firstNumberSubPart != null && secondNumberSubPart != null)
                    && Integer.parseInt(firstNumberSubPart) < Integer.parseInt(secondNumberSubPart)) {
                return -1;
            } else if (firstNumberSubPart == null) {
                return -1;
            } else if (secondNumberSubPart == null) {
                return 1;
            }
            return 0;
        }
    }
}
