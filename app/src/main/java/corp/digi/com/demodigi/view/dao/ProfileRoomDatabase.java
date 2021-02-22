package corp.digi.com.demodigi.view.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import corp.digi.com.demodigi.response.DisplayUserData;

@Database(entities = {DisplayUserData.class}, version = 2, exportSchema = false)
public abstract class ProfileRoomDatabase extends RoomDatabase {

    public abstract ProfileDao displayUserDataDao();

    private static ProfileRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ProfileRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProfileRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProfileRoomDatabase.class, "profile_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
