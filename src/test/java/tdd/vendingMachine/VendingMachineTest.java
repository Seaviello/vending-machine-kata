package tdd.vendingMachine;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class VendingMachineTest {

    private VendingMachine machine;

    @Before
    public void setup() {
        machine = new VendingMachine();
    }

    @Test
    public void shouldRemainingCashBeZero() {
        Assertions.assertThat(machine.remainingCash()).isEqualByComparingTo(new BigDecimal(0.0));
    }

    @Test
    public void shouldAddMoney() {
        machine.addCoin(Denomination.HALF, 3);
        machine.addCoin(Denomination.FIVE, 1);
        machine.addCoin(Denomination.ONE_TENTH, 2);
        machine.addCoin(Denomination.ONE_TENTH, 2);
        Assertions.assertThat(machine.remainingCash()).isEqualByComparingTo(new BigDecimal(6.9));
    }


}
