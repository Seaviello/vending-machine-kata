package tdd.vendingMachine;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VendingMachine {

    private Map<Denomination, Integer> cash = EnumSet.allOf(Denomination.class).stream().collect(Collectors.toMap(Function.identity(), element -> 0));

    public void addCoin(Denomination type, int number) {
        cash.put(type, cash.get(type) + number);
    }

    public BigDecimal remainingCash() {
        return new BigDecimal(cash.entrySet().stream().mapToInt(value -> value.getKey().getValue() * value.getValue()).sum() / 100.0);
    }
}
