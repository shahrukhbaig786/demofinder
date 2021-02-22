package corp.digi.com.demodigi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import corp.digi.com.demodigi.R;

/**
 * Created by sharukhb on 10/26/2018.
 */

public class HFragment extends Fragment {
    private TabViewAdapter adapter;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.h_fragment, container, false);
        ButterKnife.bind(this, view);
      //  setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        try {
            mFragmentTitleList.clear();
            mFragmentList.clear();
//            adapter = new TabViewAdapter(this.getChildFragmentManager());
//            int count = adapter.getCount();
//            adapter.addFragment(new HomeFrag(), getString(R.string.home));
//            adapter.addFragment(new FavFrag(), getString(R.string.favourites));
//            adapter.addFragment(new PopularFrag(), getString(R.string.popular));
//            adapter.addFragment(new SettingFrag(), getString(R.string.setting));
//            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            break;
                        case 1:

                            break;
                        case 2:
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
//            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class TabViewAdapter extends FragmentPagerAdapter {

        public TabViewAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {

                    return new HomeFrag();
                }
                case 1: {

//                    return new FavFrag();
                }
                case 2: {

                    return new PopularFrag();
                }
                case 3: {

                    return new SettingFrag();
                }
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
