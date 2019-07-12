package com.vatebra.eirsagentpoc.util;

import android.text.Html;
import android.text.Spanned;

import java.text.NumberFormat;
import java.util.Currency;

import retrofit2.http.PUT;

/**
 * Created by Collins Oronsaye on 16/08/2017.
 */

public class Constants {

    public static Spanned nairaSymbol = Html.fromHtml("&#8358");
    public static String userId = "30044";

    public enum MaritalStatus {
        Single(1), married(2);

        private int value;

        private MaritalStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }


    public enum EnumGender {
        Male(1), Female(2);
        private int value;

        private EnumGender(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }

    public enum NotificationMethod {
        SMS(1), EMAIL(2);
        private int value;
        private NotificationMethod(int value){
            this.value = value;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }
    public static String formatStringToNaira(double number) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("NGN"));
        String formatedNumber = format.format(number);
        formatedNumber = formatedNumber.replace("NGN", nairaSymbol);
        return formatedNumber;

    }

}
