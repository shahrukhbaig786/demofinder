package corp.digi.com.demodigi.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import corp.digi.com.demodigi.R;
import corp.digi.com.demodigi.response.ServerResponse;
import corp.digi.com.demodigi.response.UserResponse;
import corp.digi.com.demodigi.view.fragment.FavFrag;
import corp.digi.com.demodigi.view.fragment.HomeFrag;
import corp.digi.com.demodigi.view.fragment.PopularFrag;
import corp.digi.com.demodigi.view.fragment.SettingFrag;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getName();
    private FragmentManager mFragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;
    private Fragment mFragment;

    @BindView(R.id.navigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.txt_toolbar)
    TextView toolbarTitle;
    public ArrayList<ServerResponse> listImageData;
    public UserResponse baseResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        try {
            ButterKnife.bind(this);
            listImageData = new ArrayList<>();
            disableShiftMode(bottomNavigationView);
            mFragmentManager = getSupportFragmentManager();
            bottomNavigationView.setOnNavigationItemSelectedListener(navigationSelectedListener);



            mFragment = new HomeFrag();
            populateFragment("", mFragment);
            setToolbarTitle("" + getText(R.string.home));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView bottomNavigationView) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
//                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e(TAG, e.toString());
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.toString());
        }
    }

    public void populateFragment(String fragmentName, Fragment mFragment) {
        fragmentTransaction = mFragmentManager.beginTransaction();
        if (mFragment != null) {
            try {
                if (listImageData.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("EXTRA_DATA", baseResponse);
                    mFragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.container, mFragment);
                fragmentTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateList(int position, boolean flag) {
        if (flag)
            listImageData.get(position).setFavrite(true);
        else listImageData.get(position).setFavrite(false);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mFragment = new HomeFrag();
                    populateFragment("", mFragment);
                    setToolbarTitle("" + getText(R.string.home));
                    break;
                case R.id.navigation_favourites:
                    mFragment = new FavFrag();
                    populateFragment("", mFragment);
                    setToolbarTitle("" + getText(R.string.favourites));
                    break;
                case R.id.navigation_popular:
                    mFragment = new PopularFrag();
                    populateFragment("", mFragment);
                    setToolbarTitle("" + getText(R.string.popular));
                    break;
                case R.id.navigation_setting:
                    mFragment = new SettingFrag();
                    populateFragment("", mFragment);
                    //Toast.makeText(HomeActivity.this, "More ", Toast.LENGTH_SHORT).show();
                    setToolbarTitle("" + getText(R.string.setting));
                    break;
            }
            return true;
        }
    };

    private void setToolbarTitle(String title_name) {
        toolbarTitle.setText(title_name);
    }

}




