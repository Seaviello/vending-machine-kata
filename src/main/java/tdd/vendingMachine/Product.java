package tdd.vendingMachine;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

enum Product {
    COLA("0.33l", 120), MINERAL_WATER("0.5l", 100), PRINCE_POLO("25g", 70), MILKA("100g", 300), RED_BULL("0.5l", 620);

    private final String quantity;
    private final BigDecimal price;

    Product(String quantity, int price) {
        this.quantity = quantity;
        this.price = new BigDecimal(price / 100.0, new MathContext(2, RoundingMode.HALF_EVEN));
    }

    public String getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
