package tdd.vendingMachine;

enum Product {
    COLA("0.33l", 120), MINERAL_WATER("0.5l", 100), PRINCE_POLO("25g", 70), MILKA("100g", 300), RED_BULL("0.5l", 620);

    private final String quantity;
    private final int value;

    Product(String quantity, int value) {
        this.quantity = quantity;
        this.value = value;
    }

    public String getQuantity() {
        return quantity;
    }

    public int getValue() {
        return value;
    }
}
