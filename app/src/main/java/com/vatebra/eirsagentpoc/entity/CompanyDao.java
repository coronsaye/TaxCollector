package com.vatebra.eirsagentpoc.entity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Strings;
import com.vatebra.eirsagentpoc.domain.entity.Company;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Collins Oronsaye on 23/08/2017.
 */

public class CompanyDao {

    private static CompanyDao INSTANCE;
    private static String TAG = CompanyDao.class.getSimpleName();

    private CompanyDao() {

    }

    public static CompanyDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CompanyDao();
        }
        return INSTANCE;
    }

    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Company> results = realm.where(Company.class).findAll();

            if (results != null)
                companies = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getCompanies: ", ex);
        } finally {
            realm.close();
        }

        if (companies.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return companies;
        }
    }

    public Company getCompany(String rin) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Company> results;
        Company company = new Company();
        try {
            results = realm.where(Company.class).findAll();
            Company company1 = results.where().equalTo("rin", rin).findFirst();
            company = realm.copyFromRealm(company1);

        } catch (Exception ex) {
            Log.e(TAG, "getCompany: ", ex);
        } finally {
            realm.close();
        }

        if (!Strings.isNullOrEmpty(company.getRin())) {
            return company;
        } else {
            return null;
        }
    }

    public void SaveCompanies(@NonNull final List<Company> companies) {
        checkNotNull(companies);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(companies);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveCompanies: ", ex);
        } finally {
            realm.close();
        }
    }

    public void SaveCompany(@NonNull final Company company) {
        checkNotNull(company);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(company);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveCompanies: ", ex);
        } finally {
            realm.close();
        }
    }

}
