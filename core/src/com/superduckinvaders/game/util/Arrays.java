package com.superduckinvaders.game.util;

/**
 * Created by james on 17/02/16.
 */
public class Arrays {
    public static boolean contains(final int[] arr, final int key) {
        return java.util.Arrays.stream(arr).anyMatch(i -> i == key);
    }
}