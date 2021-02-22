package corp.digi.com.demodigi.view.dao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import corp.digi.com.demodigi.response.DisplayUserData;

@Dao
public interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DisplayUserData data);

    @Query("DELETE FROM profile_table")
    void deleteAll();



    @Query("SELECT * FROM profile_table")
    List<DisplayUserData> getAllProfile();

    @Query("SELECT * FROM profile_table WHERE isconnected = 1")
    List<DisplayUserData> getAllProfileFavt();


    @Query("SELECT * FROM profile_table WHERE isremoved = 1")
    List<DisplayUserData> getAllDeclineData();
}
