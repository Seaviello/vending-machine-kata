package tdd.vendingMachine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Author: Tomasz Kawik
 * Date: 23.01.2016
 */
class ProductDatabase {

    private static final List<Product> PRODUCT_LIST = Collections.unmodifiableList(Arrays.asList(Product.COLA, Product.COLA, Product.MILKA, Product.MINERAL_WATER, Product.RED_BULL, Product.RED_BULL, Product.PRINCE_POLO, Product.PRINCE_POLO));
    private static final List<Integer> QUANTITY = Collections.unmodifiableList(Arrays.asList(5, 8, 5, 1, 1, 4, 5, 2));

    public static Shelf completeShelf(int number) {
        assert number < PRODUCT_LIST.size() && number >= 0;
        return new Shelf(PRODUCT_LIST.get(number), QUANTITY.get(number));
    }
}

