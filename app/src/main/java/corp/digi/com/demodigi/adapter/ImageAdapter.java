package corp.digi.com.demodigi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

import corp.digi.com.demodigi.R;
import corp.digi.com.demodigi.response.ServerResponse;
import corp.digi.com.demodigi.view.activity.FullImageViewerActivity;
import corp.digi.com.demodigi.view.activity.HomeActivity;

/**
 * Created by sharukhb on 3/13/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageAdapterHolders> {

    private final ArrayList<ServerResponse> response;
    private final Context mContext;
    private String type;
    private DisplayImageOptions options;
    public ImageAdapter(Context context, ArrayList<ServerResponse> responses,String type) {
        this.response = responses;
        this.mContext = context;
        this.type = type;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

    }


    @Override
    public ImageAdapterHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_images, parent, false);

        return new ImageAdapterHolders(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapterHolders holder, int position) {
        try {
                    if (response.get(position).isFavrite()) {
                        holder.imv_heart.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
                    }else {
                        holder.imv_heart.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_favorite_border_grey_24dp));
                    }
            Glide.with(mContext)
                    .load(response.get(position).getUrl())
                    .placeholder(android.R.drawable.ic_menu_camera)
                    .error(android.R.drawable.ic_menu_camera)
                    .into(holder.imvLogo);
        } catch (Exception r) {
        }

    }

    @Override
    public int getItemCount() {
        return response.size();
    }


    public class ImageAdapterHolders extends RecyclerView.ViewHolder implements View.OnClickListener {


        private ImageView imvLogo, imv_heart;
        private ArrayList<String> image_arr = new ArrayList<>();

        public ImageAdapterHolders(View itemView) {
            super(itemView);
            imvLogo = (ImageView) itemView.findViewById(R.id.imv_product);
            imv_heart = (ImageView) itemView.findViewById(R.id.imv_heart);
            imvLogo.setOnClickListener(this);
            imv_heart.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imv_product: {
                    Intent intent = new Intent(mContext, FullImageViewerActivity.class);
                    image_arr.clear();
                    for (int i = 0; i < response.size(); i++) {
                        image_arr.add(response.get(i).getUrl());
                    }
                    intent.putStringArrayListExtra("imageArray", image_arr);
                    intent.putExtra("pos", getLayoutPosition());
                    mContext.startActivity(intent);
                }
                case R.id.imv_heart: {
                    if (response.get(getLayoutPosition()).isFavrite()) {
                        imv_heart.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_favorite_border_grey_24dp));
                        ((HomeActivity) mContext).updateList(getLayoutPosition(),false);
                        if (type.equals("favorite"))
                        response.remove(getLayoutPosition());
                        notifyDataSetChanged();
                    } else {
                        imv_heart.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
                        ((HomeActivity) mContext).updateList(getLayoutPosition(),true);
                        notifyDataSetChanged();
                    }
                }
            }
        }
    }
}

