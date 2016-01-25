vending-machine-kata
====================

This is a simple exercise vending-machine-kata - in which you will simulate the...
vending machine ( https://en.wikipedia.org/wiki/Vending_machine )

The project is maven based. On opening you will also find two classes: `VendingMachine` and `VendingMachineTest`.
The second one contains one test, which you can easily remove, since it is not part of the assignment.
You can also rename or even remove provided classes.
Below you will find requirements, key aspects and most importantly assignment itself.

You are more than welcome to tell us (either in email or in the README) what you would do differently, if you had more time.


Assumptions
---------
1. ProductDatabase works as a database for purpose of this task.
2. Product can be bough even when there is no money in machine, if the amount of money corresponds exactly to product price.
3. Machine can be loaded with denominations 5, 2, 1, 0.5, 0.2, 0.1.
4. Buying a product is possible when:
a. inserted amount of money is higher than price,
b. there is product on a shelf
c. machine has change
5. After buying customer receives change. For purpose of this task receiving product not included.

Future development
--------
1. Changing mock database to normal one.
2. Adding logic for obtaining product.
3. Maybe little refactoring (f.e. buy method is quite big right now).
