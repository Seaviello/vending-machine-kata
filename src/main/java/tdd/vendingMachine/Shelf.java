package tdd.vendingMachine;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * Author: Tomasz Kawik
 * Date: 23.01.2016
 */
public class Shelf {
    private final Stack<Product> products = new Stack<>();
    private int quantity;

    public Shelf(Product product, int quantity) {
        this.quantity = quantity;
        IntStream.range(0, quantity).forEach(value -> products.push(product));
    }

    public Product getProduct() {
        if (quantity-- > 0) {
            return products.pop();
        }
        throw new EmptyStackException();
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProductType() {
        if (quantity-- > 0) {
            return products.peek();
        }
        throw new EmptyStackException();
    }
}
