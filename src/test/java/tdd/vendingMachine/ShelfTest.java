package tdd.vendingMachine;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

/**
 * Author: Tomasz Kawik
 * Date: 23.01.2016
 */
public class ShelfTest {

    private Shelf colaShelf;
    private Shelf waterShelf;

    @Before
    public void setup() {
        this.colaShelf = new Shelf(Product.COLA, 5);
        this.waterShelf = new Shelf(Product.MINERAL_WATER, 0);
    }

    @Test
    public void shouldReturnQuantity() {
        colaShelf.getProduct();
        colaShelf.getProduct();

        Assertions.assertThat(colaShelf.getQuantity()).isEqualTo(3);
    }

    @Test(expected = EmptyStackException.class)
    public void shouldThrowExceptionWhenNoMoreProducts() {
        Assertions.assertThat(waterShelf.getProduct());
    }
}
