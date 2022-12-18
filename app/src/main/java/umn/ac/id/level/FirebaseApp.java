package umn.ac.id.level;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable disk persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
