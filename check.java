package com.metanit;

import java.math.BigInteger;

public class Main {



    public static void main(String[] args) {

        //System.out.println("res pali = " + isPalindrome("Madam, I'm Adam!"));
        //System.out.println("res fact = " + factorial(17));
        //int[] a1 = new int[] {21, 23, 24, 40, 75, 76, 78, 77, 900, 2100, 2200, 2300, 2400, 2500};
//        int[] a2 = new int[] {10, 11, 41, 50, 65, 86, 98, 101, 190, 1100, 1200, 3000, 5000};
//        int[] a3 = new int[a1.length + a2.length];
//
//        for(int i=0; i<a1.length; i++)
//            System.out.println(a1[i]);



    }


    private String printTextPerRole(String[] roles, String[] textLines) {

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < roles.length; i++) {
            res.append(roles[i] + ":\n");

            for (int j = 0; j < textLines.length; j++) {
                if (textLines[i].startsWith(roles[i] + ":")){
                    
                }
            }

        }


        return "";
    }


//    public static int[] mergeArrays(int[] a1, int[] a2) {
//        return ...; // your implementation here
//    }



    public static BigInteger factorial(int value) {
        BigInteger ret = BigInteger.ONE;

        for (int i=1; i <= value; i++)
            ret = ret.multiply(BigInteger.valueOf(i));
        return ret;

    }

    public static boolean isPalindrome(String text) {

        String t2 = text.replaceAll("[^a-zA-Z0-9]", "");
        StringBuffer t3 = new StringBuffer(t2);
        String t4 = t3.toString();
        boolean x = t2.equalsIgnoreCase(t4);
        return x;

    }

    public static boolean isPowerOfTwo(int value) {
        value = Math.abs(value);
//        if ((value != 0) && ((value & (~value +1 )) == value)){
//            return true;
//        }
//        else
//            return false;

        if (Integer.bitCount(value) == 1){
            return true;
        }
        return false;

    }

    public static char charExpression(int a) {
        char charVal = '\\';


        return  (char) (((int)charVal)+a);
    }

    public static int flipBit(int value, int bitIndex) {
        return value ^ bitIndex;
        // put your implementation here
    }

    public static int leapYearCount(int year) {
        return (year - (year % 4)) / 4 - ((year - (year % 100)) / 100 - (year - (year % 400)) / 400);
    }
}







