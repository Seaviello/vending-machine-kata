package tdd.vendingMachine;

enum Denomination {
    FIVE(500), TWO(200), ONE(100), HALF(50), ONE_FIFTH(20), ONE_TENTH(10);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}
