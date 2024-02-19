package com.example.params;

public class Reverser {

    public static String reverseString(String input) {
        char[] charArray = input.toCharArray();
        for (int start = 0, end = charArray.length - 1; start < end; start++, end--) {
            // Tausche die Zeichen am Anfang und am Ende
            char temp = charArray[start];
            charArray[start] = charArray[end];
            charArray[end] = temp;
        }

        return new String(charArray);
    }

}
