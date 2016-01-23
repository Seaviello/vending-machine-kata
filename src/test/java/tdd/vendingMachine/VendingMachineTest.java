package tdd.vendingMachine;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import tdd.vendingMachine.exceptions.NoMoneyInserted;
import tdd.vendingMachine.exceptions.NotEnoughChangeException;
import tdd.vendingMachine.exceptions.NotEnoughMoneyInsertedException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class VendingMachineTest {

    private VendingMachine machine;
    private static final int NUMBER_OF_SHELVES = 7;

    @Before
    public void setup() {
        machine = new VendingMachine();
    }

    @Test
    public void shouldRemainingCashBeZero() {
        Assertions.assertThat(machine.getRemainingCash()).isEqualByComparingTo(new BigDecimal(0.0, new MathContext(2, RoundingMode.HALF_EVEN)));
    }

    @Test
    public void shouldAddMoney() {
        machine.load(Denomination.HALF, 3);
        machine.load(Denomination.FIVE, 1);
        machine.load(Denomination.ONE_TENTH, 2);
        machine.load(Denomination.ONE_TENTH, 2);
        Assertions.assertThat(machine.getRemainingCash()).isEqualByComparingTo(new BigDecimal(6.9, new MathContext(2, RoundingMode.HALF_EVEN)));
    }

    @Test
    public void shouldReturnNumberOfShelves() {
        Assertions.assertThat(machine.getNumberOfShelves()).isEqualTo(NUMBER_OF_SHELVES);
    }

    @Test
    public void shouldReturnPriceForGivenShelf() {
        BigDecimal price = new BigDecimal(ProductDatabase.completeShelf(2).getProduct().getPrice() / 100.0, new MathContext(2, RoundingMode.HALF_EVEN));

        Assertions.assertThat(machine.selectShelf(3)).isEqualByComparingTo(price);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowExceptionForWrongShelfNumber() {
        Assertions.assertThat(machine.selectShelf(-5));
    }

    @Test
    public void shouldReturnHowMuchNeedToBeInserted() {
        BigDecimal remaining = machine.selectShelf(1).subtract(new BigDecimal(Denomination.ONE.getValue() / 100.0, new MathContext(2, RoundingMode.HALF_EVEN)));

        Assertions.assertThat(machine.insertCoin(Denomination.ONE)).isEqualByComparingTo(remaining);
    }

    @Test
    public void shouldThrowExceptionWhenClickedBuyButNotEnoughMoneyInserted() {
        machine.selectShelf(1);
        machine.insertCoin(Denomination.ONE);

        try {
            machine.buy();
            Assertions.fail("Should not be possible, because not enough money inserted!");
        } catch (NotEnoughMoneyInsertedException e) {
            Assertions.assertThat(true);
        } catch (NotEnoughChangeException e) {
            Assertions.fail("Wrong exception type.");
        }
    }

    @Test
    public void shouldThrowExceptionWhenNoMoneyInserted() {
        machine.selectShelf(1);
        machine.insertCoin(Denomination.FIVE);
        try {
            machine.buy();
            Assertions.fail("No money inserted!");
        } catch (NotEnoughChangeException e) {
            Assertions.assertThat(true);
        } catch (NotEnoughMoneyInsertedException e) {
            Assertions.fail("Enough money!");
        }
    }

    @Test
    public void shouldThrowExceptionWhenNoEnoughChange() {
        machine.selectShelf(1);
        machine.load(Denomination.HALF, 2);
        machine.load(Denomination.ONE, 1);
        machine.insertCoin(Denomination.FIVE);
        try {
            machine.buy();
            Assertions.fail("Not enough change!");
        } catch (NotEnoughChangeException e) {
            Assertions.assertThat(true);
        } catch (NotEnoughMoneyInsertedException e) {
            Assertions.fail("Enough money!");
        }
    }

    @Test
    public void shouldCorrectlyGiveMoney() {
        machine.selectShelf(1);
        machine.load(Denomination.ONE_TENTH, 10);
        machine.load(Denomination.ONE_FIFTH, 6);
        machine.load(Denomination.HALF, 2);
        machine.load(Denomination.ONE, 1);
        machine.insertCoin(Denomination.FIVE);
        BigDecimal change = machine.getInsertedCash().subtract(machine.getPriceOfSelectedShelf());

        try {
            Assertions.assertThat(Utils.getCash(machine.buy())).isEqualByComparingTo(change);
        } catch (Exception e) {
            Assertions.fail("Money should be given correctly!");
        }
    }

    @Test
    public void shouldRemainingMoneyBeSummedCorrectly() {
        machine.selectShelf(1);
        machine.load(Denomination.ONE_TENTH, 10);
        machine.load(Denomination.ONE_FIFTH, 6);
        machine.load(Denomination.HALF, 2);
        machine.load(Denomination.ONE, 1);
        BigDecimal cash = machine.getRemainingCash().add(machine.getPriceOfSelectedShelf());
        machine.insertCoin(Denomination.FIVE);
        try {
            machine.buy();
        } catch (Exception e) {
            Assertions.fail("Money should be given correctly!");
        }

        Assertions.assertThat(machine.getRemainingCash()).isEqualByComparingTo(cash);
    }

    @Test
    public void shouldCancelReturnGivenMoney() {
        machine.selectShelf(1);
        machine.insertCoin(Denomination.ONE);

        try {
            Assertions.assertThat(Utils.getCash(machine.cancel())).isEqualByComparingTo(new BigDecimal(Denomination.ONE.getValue() / 100.0, new MathContext(2, RoundingMode.HALF_EVEN)));
        } catch (NoMoneyInserted noMoneyInserted) {
            Assertions.fail("Money should be given correctly!");
        }
    }

    @Test
    public void shouldCancelThrowExceptionWhenNoMoneyInserted() {
        machine.selectShelf(1);
        try {
            machine.cancel();
            Assertions.fail("Money should not be given!");
        } catch (NoMoneyInserted noMoneyInserted) {
            Assertions.assertThat(true);
        }
    }
}
