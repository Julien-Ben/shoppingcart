package com.xgen.interview;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import static com.xgen.interview.ShoppingCart.PrintModes.*;
import static org.junit.Assert.assertEquals;


public class ShoppingCartTest {

    @Test
    public void canAddAnItem() {
        ShoppingCart sc = new ShoppingCart(new Pricer());
        sc.addItem("apple", 1);
        String content = buildReceiptContent(new String[]{"apple"}, new float[]{1.0f}, new int[]{1});
        assertReceiptHasExpectedContent(sc, content);
    }

    @Test
    public void canAddMoreThanOneItem() {
        ShoppingCart sc = new ShoppingCart(new Pricer());
        sc.addItem("apple", 2);
        String content = buildReceiptContent(new String[]{"apple"}, new float[]{2.0f}, new int[]{2});
        assertReceiptHasExpectedContent(sc, content);
    }

    @Test
    public void canAddDifferentItems() {
        ShoppingCart sc = new ShoppingCart(new Pricer());
        sc.addItem("apple", 2);
        sc.addItem("banana", 1);
        String content = buildReceiptContent(new String[]{"apple", "banana"}, new float[]{2.0f, 2.0f}, new int[]{2, 1});
        assertReceiptHasExpectedContent(sc, content);
    }

    @Test
    public void doesntExplodeOnMysteryItem() {
        ShoppingCart sc = new ShoppingCart(new Pricer());
        sc.addItem("crisps", 2);
        String content = buildReceiptContent(new String[]{"crisps"}, new float[]{0.0f}, new int[]{2});
        assertReceiptHasExpectedContent(sc, content);
    }

    @Test
    public void orderingIsPreservedWhenAddingAnItemMultipleTimes() {
        ShoppingCart sc = new ShoppingCart(new Pricer());
        sc.addItem("apple", 2);
        sc.addItem("banana", 1);
        sc.addItem("potatoes", 1);
        sc.addItem("banana", 1);
        sc.addItem("crisps", 12);
        sc.addItem("banana", 1);
        String content = buildReceiptContent(
                new String[]{"apple", "banana", "potatoes", "crisps"},
                new float[]{2.0f, 6.0f, 0f, 0f},
                new int[]{2, 3, 1, 12}
        );
        assertReceiptHasExpectedContent(sc, content);
    }

    @Test
    public void reversePricePrintingModeWorks() {
        ShoppingCart sc = new ShoppingCart(new Pricer());
        sc.addItem("apple", 2);
        sc.addItem("banana", 1);
        sc.addItem("potatoes", 1);
        sc.addItem("banana", 1);
        sc.addItem("crisps", 12);
        sc.addItem("banana", 1);
        String content = buildReceiptContent(
                new String[]{"apple", "banana", "potatoes", "crisps"},
                new float[]{2.0f, 6.0f, 0f, 0f},
                new int[]{2, 3, 1, 12},
                REVERSE_PRICE
        );
        assertReceiptHasExpectedContent(sc, content, REVERSE_PRICE);
    }

    private void assertReceiptHasExpectedContent(ShoppingCart sc, String content) {
        assertReceiptHasExpectedContent(sc, content, DEFAULT);
    }

    private void assertReceiptHasExpectedContent(ShoppingCart sc, String content, ShoppingCart.PrintModes printMode) {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        sc.printReceipt(printMode);
        assertEquals(String.format(content), myOut.toString());
    }


    private String buildReceiptContent(String[] items, float[] prices, int[] quantities) {
        return buildReceiptContent(items, prices, quantities, DEFAULT);
    }

    /**
     * Helper method for unit tests, to build the content of the receipt from three simple data structures.
     * The arrays must have the same length.
     *
     * @param items      list of item names
     * @param prices     list of item prices
     * @param quantities list of item quantities
     * @param printMode the mode used for printing the receipt, if none is specified, DEFAULT is used
     * @return the String corresponding to the receipt
     */
    private String buildReceiptContent(String[] items, float[] prices, int[] quantities, ShoppingCart.PrintModes printMode) {
        //The arrays must have the same length
        assertEquals(items.length, prices.length);
        assertEquals(items.length, quantities.length);
        StringBuilder sb = new StringBuilder();
        float totalPrice = 0f;
        for (int i = 0; i < items.length; i++) {
            switch (printMode) {
                case REVERSE_PRICE:
                    sb.append(String.format(Locale.ENGLISH, "€%.2f", prices[i]) + " - " + items[i] + " - " + quantities[i] + " - ");
                    break;
                case DEFAULT:
                    sb.append(items[i] + " - " + quantities[i] + " - " + String.format(Locale.ENGLISH, "€%.2f", prices[i]));
                    break;
            }
            sb.append(String.format("%n"));
            totalPrice += prices[i];
        }
        sb.append(String.format("Total" + " - " + String.format(Locale.ENGLISH, "€%.2f", totalPrice) + "%n"));
        return sb.toString();
    }
}


