package com.surelabsid.newmrjempoot.utils;

import java.text.DecimalFormat;

public class CurrencyHelper {
    public static String coolNumberFormat(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        DecimalFormat format = new DecimalFormat("0.#");
        String value = format.format(count / Math.pow(1000, exp));
        return String.format("%s%c", value, "KMBTPE".charAt(exp - 1));
    }

    public static double potongan(double hargaAwal, double hargaDiskon) {
        return hargaDiskon / hargaAwal * 100;
    }
}
