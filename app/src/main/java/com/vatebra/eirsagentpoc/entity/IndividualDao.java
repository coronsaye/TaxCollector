package com.vatebra.eirsagentpoc.entity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Strings;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.EconomicActivity;
import com.vatebra.eirsagentpoc.domain.entity.Individual;
import com.vatebra.eirsagentpoc.domain.entity.TaxOffice;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Collins Oronsaye on 23/08/2017.
 */

public class IndividualDao {

    private static IndividualDao INSTANCE;
    private static String TAG = IndividualDao.class.getSimpleName();

    private IndividualDao() {

    }

    public static IndividualDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IndividualDao();
        }
        return INSTANCE;
    }


    public List<Individual> getIndividuals() {
        List<Individual> individuals = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Individual> results = realm.where(Individual.class).findAll();

            if (results != null)
                individuals = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getIndividuals: ", ex);
        } finally {
            realm.close();
        }

        if (individuals.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return individuals;
        }
    }

    public List<TaxOffice> getTaxOffices() {
        List<TaxOffice> taxOffices = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<TaxOffice> results = realm.where(TaxOffice.class).findAll();

            if (results != null)
                taxOffices = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getIndividuals: ", ex);
        } finally {
            realm.close();
        }

        if (taxOffices.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return taxOffices;
        }
    }

    public List<EconomicActivity> getEconomicActivity() {
        List<EconomicActivity> economicActivities = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<EconomicActivity> results = realm.where(EconomicActivity.class).findAll();

            if (results != null)
                economicActivities = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getIndividuals: ", ex);
        } finally {
            realm.close();
        }

        if (economicActivities.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return economicActivities;
        }
    }


    public void SaveEconomicActivities(final List<EconomicActivity> economicActivities) {
        checkNotNull(economicActivities);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(economicActivities);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveTaxOffices: ", ex);
        } finally {
            realm.close();
        }
    }

    public void SaveTaxOffices(@NonNull final List<TaxOffice> taxOffices) {
        checkNotNull(taxOffices);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(taxOffices);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveTaxOffices: ", ex);
        } finally {
            realm.close();
        }
    }


    public Individual getIndividual(String rin) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Individual> results;
        Individual individual = new Individual();
        try {
            results = realm.where(Individual.class).findAll();
            Individual individual1 = results.where().equalTo("userRin", rin).findFirst();
            individual = realm.copyFromRealm(individual1);

        } catch (Exception ex) {
            Log.e(TAG, "getIndividual: ", ex);
        } finally {
            realm.close();
        }

        if (!Strings.isNullOrEmpty(individual.getUserRin())) {
            return individual;
        } else {
            return null;
        }
    }

    public void SaveIndividuals(@NonNull final List<Individual> individuals) {
        checkNotNull(individuals);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(individuals);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveIndividual: ", ex);
        } finally {
            realm.close();
        }
    }

    public void SaveIndividual(@NonNull final Individual individual) {
        checkNotNull(individual);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(individual);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveIndividual: ", ex);
        } finally {
            realm.close();
        }
    }

}
