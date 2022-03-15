package com.xgen.interview;

import java.lang.reflect.Array;
import java.util.*;


/**
 * This is the current implementation of ShoppingCart.
 * Please write a replacement
 */
public class ShoppingCart implements IShoppingCart {
    //Using LinkedHashMap to preserve insertion order
    private final LinkedHashMap<String, Integer> contents = new LinkedHashMap<>();
    private final Pricer pricer;

    public ShoppingCart(Pricer pricer) {
        this.pricer = pricer;
    }

    public void addItem(String itemType, int number) {
        contents.put(itemType, contents.getOrDefault(itemType, 0) + number);
    }

    public void printReceipt() {
        Object[] keys = contents.keySet().toArray();

        for (int i = 0; i < Array.getLength(keys) ; i++) {
            Integer price = pricer.getPrice((String)keys[i]) * contents.get(keys[i]);
            Float priceFloat = new Float(new Float(price) / 100);
            String priceString = String.format(Locale.ENGLISH, "€%.2f", priceFloat);

            System.out.println(keys[i] + " - " + contents.get(keys[i]) + " - " + priceString);
        }
    }
}
