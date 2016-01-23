package tdd.vendingMachine;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Author: Tomasz Kawik
 * Date: 23.01.2016
 */
class Utils {

    private Utils() {
        throw new AssertionError("Should not be possible to initialize this class");
    }

    public static BigDecimal getCash(Map<Denomination, Integer> cash) {
        return new BigDecimal(cash.entrySet().stream().mapToInt(value -> value.getKey().getValue() * value.getValue()).sum() / 100.0, new MathContext(2, RoundingMode.HALF_EVEN));
    }

    public static Denomination getNextHighestAvailableDenomination(Map<Denomination, Integer> cash) {
        return getNextHighestAvailableDenomination(cash, null);
    }

    public static Denomination getNextHighestAvailableDenomination(Map<Denomination, Integer> cash, Denomination previousValue) {
        List<Denomination> denominations = Arrays.asList(Denomination.values());
        int counter = previousValue != null ? denominations.indexOf(previousValue) + 1 : 0;
        if (counter >= denominations.size()) {
            return null;
        }

        while (cash.get(denominations.get(counter)) == 0) {
            if (++counter >= denominations.size()) {
                return null;
            }
        }
        return denominations.get(counter);
    }
}
