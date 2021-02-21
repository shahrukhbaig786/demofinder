package corp.digi.com.demodigi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.ArrayList;

import corp.digi.com.demodigi.R;
import corp.digi.com.demodigi.util.TouchImageView;
import corp.digi.com.demodigi.util.Util;


/**
 * Created by sharukhb on 3/13/2018.
 */
public class ImagePagerAdapter extends PagerAdapter {

    private Context mGetContext;
    ArrayList<String> mAl_profileimg = new ArrayList<String>();
    private DisplayImageOptions options;

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF startPoint = new PointF();
    PointF midPoint = new PointF();
    float oldDist = 1f;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    public ImagePagerAdapter(Context mContext, ArrayList<String> al_profileimg) {
        mGetContext = mContext;
        mAl_profileimg = al_profileimg;
        Bitmap default_bitmap = drawableToBitmap(mContext.getResources().getDrawable(android.R.drawable.ic_menu_camera));
        //Bitmap default_bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thumnail_img_hover);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(new BitmapDrawable(mContext.getResources(), default_bitmap))
                .showImageForEmptyUri(new BitmapDrawable(mContext.getResources(), default_bitmap))
                .showImageOnFail(new BitmapDrawable(mContext.getResources(), default_bitmap))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return this.mAl_profileimg.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final Context context = mGetContext;


        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        View vi = mLayoutInflater.inflate(R.layout.layout_full_image, null, false);
        TouchImageView imageView = (TouchImageView) vi.findViewById(R.id.fullImageView);
        try {
            Glide.with(context)
                    .load(mAl_profileimg.get(position))
                    .placeholder(android.R.drawable.ic_menu_camera)
                    .error(android.R.drawable.ic_menu_camera)
                    .into(imageView);
        } catch (Exception r) {
        }

        ((ViewPager) container).addView(vi, 0);

        return vi;
    }


    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view == ((LinearLayout) object);
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}