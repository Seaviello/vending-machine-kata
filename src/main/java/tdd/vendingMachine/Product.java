package tdd.vendingMachine;

enum Product {
    COLA("0.33l", 120), MINERAL_WATER("0.5l", 100), PRINCE_POLO("25g", 70), MILKA("100g", 300), RED_BULL("0.5l", 620);

    private final String quantity;
    private final int price;

    Product(String quantity, int price) {
        this.quantity = quantity;
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
