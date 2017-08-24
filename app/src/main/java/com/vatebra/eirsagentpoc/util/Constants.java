package com.vatebra.eirsagentpoc.util;

/**
 * Created by David Eti on 16/08/2017.
 */

public class Constants {

    public enum MaritalStatus {
        Single(1), Married(2);

        private int value;

        private MaritalStatus(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }
}
