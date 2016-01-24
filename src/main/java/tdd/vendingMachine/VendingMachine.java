package tdd.vendingMachine;

import tdd.vendingMachine.exceptions.EmptyShelfException;
import tdd.vendingMachine.exceptions.NoMoneyInserted;
import tdd.vendingMachine.exceptions.NotEnoughChangeException;
import tdd.vendingMachine.exceptions.NotEnoughMoneyInsertedException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VendingMachine {

    private static final int NUMBER_OF_SHELVES = 7;
    private static final int DEFAULT_SHELF = 1;

    private Map<Denomination, Integer> cash = EnumSet.allOf(Denomination.class).stream().collect(Collectors.toMap(Function.identity(), element -> 0));
    private final List<Shelf> shelves = new ArrayList<>(NUMBER_OF_SHELVES);

    private int selectedShelf = DEFAULT_SHELF;
    private BigDecimal priceOfSelectedShelf;
    private Map<Denomination, Integer> insertedCash = new HashMap<>();

    public VendingMachine() {
        IntStream.range(0, NUMBER_OF_SHELVES).forEach(value -> shelves.add(ProductDatabase.completeShelf(value)));
        priceOfSelectedShelf = selectShelf(selectedShelf);
    }

    public void load(Denomination type, int number) {
        cash.put(type, cash.get(type) + number);
    }

    public BigDecimal getRemainingCash() {
        return Utils.getCash(cash);
    }

    public BigDecimal getInsertedCash() {
        return Utils.getCash(insertedCash);
    }

    public int getNumberOfShelves() {
        return shelves.size();
    }

    public BigDecimal getPriceOfSelectedShelf() {
        return priceOfSelectedShelf;
    }

    public BigDecimal selectShelf(int numberOfShelf) {
        if (numberOfShelf > NUMBER_OF_SHELVES || numberOfShelf < 1) {
            throw new IndexOutOfBoundsException("Not valid number");
        }
        selectedShelf = numberOfShelf;
        return priceOfSelectedShelf = new BigDecimal(shelves.get(numberOfShelf - 1).getProductType().getPrice() / 100.0, new MathContext(2, RoundingMode.HALF_EVEN));
    }

    public BigDecimal insertCoin(Denomination coin) throws MissingResourceException {
        insertedCash.put(coin, insertedCash.getOrDefault(coin, 0) + 1);
        cash.put(coin, cash.get(coin) + 1);
        return priceOfSelectedShelf.subtract(getInsertedCash());
    }

    public Map<Denomination, Integer> cancel() throws NoMoneyInserted {
        if (insertedCash.isEmpty()) {
            throw new NoMoneyInserted();
        }
        Map<Denomination, Integer> copy = new HashMap<>(insertedCash);
        insertedCash.entrySet().stream().forEach(entry -> cash.put(entry.getKey(), cash.get(entry.getKey()) - entry.getValue()));
        insertedCash = new HashMap<>();
        return copy;
    }

    public Map<Denomination, Integer> buy() throws NotEnoughChangeException, NotEnoughMoneyInsertedException, EmptyShelfException {
        if (priceOfSelectedShelf.subtract(getInsertedCash()).signum() <= 0) {
            Map<Denomination, Integer> cashCopy = new HashMap<>(cash);
            Map<Denomination, Integer> change = EnumSet.allOf(Denomination.class).stream().collect(Collectors.toMap(Function.identity(), element -> 0));
            Denomination highestCoin = Utils.getNextHighestAvailableDenomination(cashCopy);

            if (highestCoin == null) {
                throw new NotEnoughChangeException();
            }

            BigDecimal remainingToGive = getInsertedCash().subtract(priceOfSelectedShelf);

            while (remainingToGive.signum() != 0) {
                while (new BigDecimal(highestCoin.getValue() / 100.0, new MathContext(2, RoundingMode.HALF_EVEN)).subtract(remainingToGive).signum() > 0) {
                    highestCoin = Utils.getNextHighestAvailableDenomination(cashCopy, highestCoin);
                    if (highestCoin == null) {
                        throw new NotEnoughChangeException();
                    }
                }
                change.put(highestCoin, change.get(highestCoin) + 1);
                cashCopy.put(highestCoin, cashCopy.get(highestCoin) - 1);
                remainingToGive = remainingToGive.subtract(new BigDecimal(highestCoin.getValue() / 100.0, new MathContext(2, RoundingMode.HALF_EVEN)));

                if (cashCopy.get(highestCoin) == 0 && (highestCoin = Utils.getNextHighestAvailableDenomination(cashCopy, highestCoin)) == null) {
                    throw new NotEnoughChangeException();
                }
            }

            try {
                shelves.get(selectedShelf).getProduct();
            } catch (EmptyStackException e) {
                throw new EmptyShelfException();
            }

            change.entrySet().stream().forEach(entry -> cash.put(entry.getKey(), cash.get(entry.getKey()) - entry.getValue()));
            cash = new HashMap<>(cashCopy);
            insertedCash = new HashMap<>();
            return change;
        }
        throw new NotEnoughMoneyInsertedException();

    }

}
