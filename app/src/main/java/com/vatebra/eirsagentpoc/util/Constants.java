package com.vatebra.eirsagentpoc.util;

import retrofit2.http.PUT;

/**
 * Created by David Eti on 16/08/2017.
 */

public class Constants {

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
}
