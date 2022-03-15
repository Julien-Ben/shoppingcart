package com.xgen.interview;

import com.xgen.interview.Pricer;
import com.xgen.interview.ShoppingCart;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

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

    private void assertReceiptHasExpectedContent(ShoppingCart sc, String content) {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        sc.printReceipt();
        assertEquals(String.format(content), myOut.toString());
    }

    /**
     * Helper method for unit tests, to build the content of the receipt from three simple data structures.
     * The two arrays must have the same length.
     * @param items list of item names
     * @param prices list of item prices
     * @param quantities list of item quantities
     * @return the String corresponding to the receipt
     */
    private String buildReceiptContent(String[] items, float[] prices, int[] quantities) {
        assertEquals(items.length, prices.length);
        StringBuilder sb = new StringBuilder();
        float totalPrice = 0f;
        for (int i = 0; i < items.length; i++) {
            sb.append(items[i] + " - " + quantities[i] + " - " + String.format(Locale.ENGLISH, "€%.2f", prices[i]));
            sb.append(String.format("%n"));
            totalPrice += prices[i];
        }
        sb.append(String.format("Total" + " - " + String.format(Locale.ENGLISH, "€%.2f",totalPrice) + "%n"));
        return sb.toString();
    }
}


