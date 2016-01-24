package tdd.vendingMachine;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

enum Denomination {
    FIVE(500), TWO(200), ONE(100), HALF(50), ONE_FIFTH(20), ONE_TENTH(10);

    private final BigDecimal value;

    Denomination(int value) {
        this.value = new BigDecimal(value / 100.0, new MathContext(2, RoundingMode.HALF_EVEN));
    }

    BigDecimal getValue() {
        return value;
    }
}
