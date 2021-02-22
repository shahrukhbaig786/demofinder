package corp.digi.com.demodigi.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import corp.digi.com.demodigi.R;
import corp.digi.com.demodigi.adapter.ImagePagerAdapter;
/*  Created by sharukhb on 3/13/2018. */
public class FullImageViewerActivity extends Activity implements View.OnClickListener {
    Context mContext;
    ArrayList<String> mImageArray;
    ImageView image_viewer_back_img_img, image_viewer_previous_img, image_viewer_next_img;
    private ViewPager viewpager;
    ImagePagerAdapter mPager_adaper;
    TextView tv_title;
    int position;
    boolean pos_scroll_flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__image_viewer);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("pos");
            mImageArray = bundle.getStringArrayList("imageArray");
        }
        image_viewer_previous_img = (ImageView) findViewById(R.id.image_viewer_previous_img);
        image_viewer_next_img = (ImageView) findViewById(R.id.image_viewer_next_img);
        tv_title = (TextView) findViewById(R.id.image_viewer_title);
        viewpager = (ViewPager) findViewById(R.id.image_viewpager_top);
        mPager_adaper = new ImagePagerAdapter(FullImageViewerActivity.this, mImageArray);
        viewpager.setAdapter(mPager_adaper);
        viewpager.setCurrentItem(position);
        image_viewer_back_img_img = (ImageView) findViewById(R.id.image_viewer_back_img);
        image_viewer_back_img_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        image_viewer_previous_img.setOnClickListener(this);
        image_viewer_next_img.setOnClickListener(this);
        if (mImageArray.size() == 1) {
            image_viewer_next_img.setVisibility(View.INVISIBLE);
            image_viewer_previous_img.setVisibility(View.INVISIBLE);
        }
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                pos_scroll_flag = true;
                tv_title.setText((viewpager.getCurrentItem() + 1) + " " + getResources().getString(R.string.of) + " " + mImageArray.size());
                {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tv_title.setText((position + 1) + " " + getResources().getString(R.string.of) + " " + mImageArray.size());
    }

    @Override
    public void onClick(View v) {
        if (v == image_viewer_back_img_img) {
            onBackPressed();
        } else if (v == image_viewer_previous_img)

        {
            if (viewpager.getCurrentItem() == 0) {

            } else {
                viewpager.setCurrentItem(viewpager.getCurrentItem() - 1);
                tv_title.setText((viewpager.getCurrentItem() + 1) + " " + getResources().getString(R.string.of) + " " + mImageArray.size());
            }
        } else if (v == image_viewer_next_img) {
            if (viewpager.getCurrentItem() == (mImageArray.size() - 1)) {

            } else {

                viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                tv_title.setText((viewpager.getCurrentItem() + 1) + " " + getResources().getString(R.string.of) + " " + mImageArray.size());

            }
        }
    }
}