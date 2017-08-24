package com.vatebra.eirsagentpoc.domain.entity;

/**
 * Created by David Eti on 22/08/2017.
 */

public class GlobalLocalDataSource {
    private static GlobalLocalDataSource INSTANCE;
    private static String TAG = GlobalLocalDataSource.class.getSimpleName();

    private GlobalLocalDataSource() {

    }

    public static GlobalLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalLocalDataSource();
        }
        return INSTANCE;
    }


    public void getLgas() {

    }

    public void getBusinessCatgeories() {

    }

    public void getBusinessSectors() {

    }

    public void getBusinessSubSectors() {

    }

    public void getBusinessStructure() {

    }
}
