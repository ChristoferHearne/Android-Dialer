package se.miun.chhe1903.dt031g.dialer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(tableName = "numbers_table")
public class Number {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name="number")
    public String number;

    @ColumnInfo(name="timestamp")
    public String timestamp;

    @ColumnInfo(name="latitude")
    public double latitude;

    @ColumnInfo(name="longitude")
    public double longitude;

    public void setNumber(String number){
        this.number = number;
    }
    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public String getNumber(){
        return this.number;
    }
    public String getTimestamp(){
        return this.timestamp;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }
}
