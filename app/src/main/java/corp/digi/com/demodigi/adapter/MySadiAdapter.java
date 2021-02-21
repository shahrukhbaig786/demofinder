package corp.digi.com.demodigi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

import corp.digi.com.demodigi.R;
import corp.digi.com.demodigi.response.DisplayUserData;
import corp.digi.com.demodigi.response.ServerResponse;
import corp.digi.com.demodigi.view.activity.FullImageViewerActivity;
import corp.digi.com.demodigi.view.activity.HomeActivity;

/**
 * Created by sharukhb on 3/13/2018.
 */

public class MySadiAdapter extends RecyclerView.Adapter<MySadiAdapter.MySadiAdapterHolders> {

    private ArrayList<DisplayUserData> responseList;
    private final Context mContext;
    private String type;
    private DisplayImageOptions options;
    private ItemListener listener;

    public MySadiAdapter(Context context, ArrayList<DisplayUserData> responses, String type) {
        this.responseList = responses;
        this.mContext = context;
        this.type = type;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }


    @Override
    public MySadiAdapterHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.raw_item, parent, false);
        return new MySadiAdapterHolders(view);
    }

    @Override
    public void onBindViewHolder(MySadiAdapterHolders holder, int position) {
        try {
            holder.tvCreatedDate.setText(responseList.get(position).getRegistrationPeriod());
            holder.tvNameAge.setText(responseList.get(position).getFullNameAge());
            holder.tvLocation.setText(responseList.get(position).getLocation());
            holder.tvEmail.setText(responseList.get(position).getEmail());
            Glide.with(mContext)
                    .load(responseList.get(position).getPicture())
                    .placeholder(android.R.drawable.ic_menu_camera)
                    .error(android.R.drawable.ic_menu_camera)
                    .into(holder.imvLogo);
            holder.fabConnect.setOnClickListener(v -> listener.onConnect());
            holder.fabDecline.setOnClickListener(view -> listener.onDecline(holder.fabDecline, position));
        } catch (Exception r) {
            Log.e("ERROR", r.toString());
        }
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }


    public class MySadiAdapterHolders extends RecyclerView.ViewHolder {

        private ImageView imvLogo, imv_heart;
        private TextView tvCreatedDate, tvNameAge, tvLocation, tvEmail;
        private FloatingActionButton fabConnect, fabDecline;

        public MySadiAdapterHolders(View itemView) {
            super(itemView);
            imvLogo = (ImageView) itemView.findViewById(R.id.iv_profile_image_);
            fabConnect = (FloatingActionButton) itemView.findViewById(R.id.fab_connect);
            fabDecline = (FloatingActionButton) itemView.findViewById(R.id.fab_decline);
            tvCreatedDate = (TextView) itemView.findViewById(R.id.tv_created_date);
            tvNameAge = (TextView) itemView.findViewById(R.id.tv_name_age);
            tvLocation = (TextView) itemView.findViewById(R.id.tv_location);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
        }
    }

    public interface ItemListener {
        void onConnect();
        void emptyView();

        void onDecline(View viewToAnimate, int position);
    }

    public void setListener(ItemListener listener) {
        this.listener = listener;
    }

    public void removeUser(View viewToAnimate, int position) {
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext()
                , android.R.anim.slide_out_right);
        viewToAnimate.startAnimation(animation);
        responseList.remove(position);
        notifyDataSetChanged();
        if(responseList.isEmpty()){
            if(listener!=null){
                listener.emptyView();
            }
        }
    }
}
