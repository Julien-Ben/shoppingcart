package com.xgen.interview;

import java.util.*;

import static com.xgen.interview.ShoppingCart.PrintModes.*;

/**
 * This is the current implementation of ShoppingCart.
 * Please write a replacement
 */
public class ShoppingCart implements IShoppingCart {
    //Using LinkedHashMap to preserve insertion order
    private final LinkedHashMap<String, Integer> contents = new LinkedHashMap<>();
    private final Pricer pricer;

    /**
     * Represents the different mode of printing the receipt, for example, showing price first.
     * When no mode is specified, "DEFAULT" should be used.
     */
    public enum PrintModes {
        DEFAULT,
        REVERSE_PRICE
    }

    public ShoppingCart(Pricer pricer) {
        this.pricer = pricer;
    }

    public void addItem(String itemType, int number) {
        contents.put(itemType, contents.getOrDefault(itemType, 0) + number);
    }

    public void printReceipt(PrintModes printMode) {
        /*
        Although the iteration is done over a set, ordering is guaranteed according to this post.
        https://stackoverflow.com/questions/2923856/is-the-order-guaranteed-for-the-return-of-keys-and-values-from-a-linkedhashmap-o/2924143#2924143
         */
        float totalPrice = 0f;
        for (Map.Entry<String, Integer> entry : contents.entrySet()) {
            String itemType = entry.getKey();
            int quantity = entry.getValue();
            int price = pricer.getPrice(itemType) * quantity;
            float priceFloat = (float) price / 100;
            totalPrice += priceFloat;
            String priceString = String.format(Locale.ENGLISH, "€%.2f", priceFloat);
            switch (printMode) {
                case REVERSE_PRICE:
                    System.out.println(priceString + " - " + itemType + " - " + quantity + " - ");
                    break;
                case DEFAULT:
                    System.out.println(itemType + " - " + quantity + " - " + priceString);
                    break;
            }
        }
        String totalPriceString = String.format(Locale.ENGLISH, "€%.2f", totalPrice);
        System.out.println("Total" + " - " + totalPriceString);
    }

    public void printReceipt() {
        printReceipt(DEFAULT);
    }
}
