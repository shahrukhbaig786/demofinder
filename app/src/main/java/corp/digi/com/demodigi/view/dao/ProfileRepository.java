package corp.digi.com.demodigi.view.dao;

import android.app.Application;

import java.util.List;

import corp.digi.com.demodigi.response.DisplayUserData;

public class ProfileRepository {
    private ProfileDao userDataDao;
    private List<DisplayUserData> allProfileList;

    public ProfileRepository(Application application) {
        ProfileRoomDatabase db = ProfileRoomDatabase.getDatabase(application);
        userDataDao = db.displayUserDataDao();
        allProfileList = userDataDao.getAllProfile();
    }
}
