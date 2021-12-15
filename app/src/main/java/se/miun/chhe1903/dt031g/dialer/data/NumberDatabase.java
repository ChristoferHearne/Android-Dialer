package se.miun.chhe1903.dt031g.dialer.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Number.class}, version = 1)
public abstract class NumberDatabase extends RoomDatabase {
    public abstract NumberDao numberDao();
}
