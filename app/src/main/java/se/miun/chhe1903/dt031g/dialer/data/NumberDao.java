package se.miun.chhe1903.dt031g.dialer.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NumberDao {
    @Query("SELECT * FROM numbers_table ORDER BY ID ASC")
    List<Number> getAll();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void InsertOne(Number number);

    @Delete
    void DeleteAll(Number... numbers);
}
