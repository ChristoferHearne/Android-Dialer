package se.miun.chhe1903.dt031g.dialer.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Number.class}, version = 1)
public abstract class NumberDatabase extends RoomDatabase {
    private static final String DB_NAME = "numbers_db";
    private static NumberDatabase instance;
    public abstract NumberDao numberDao();

    public static synchronized NumberDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NumberDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
