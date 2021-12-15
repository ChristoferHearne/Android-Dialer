package se.miun.chhe1903.dt031g.dialer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "numbers_table")
public class Number {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name="number")
    public String number;
}
