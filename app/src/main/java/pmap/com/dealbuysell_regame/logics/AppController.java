package pmap.com.dealbuysell_regame.logics;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Shashidhar on 17-02-2018.
 */

public class AppController extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("tasky.realm")
                .schemaVersion(P.DATABASE_VERSION)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
