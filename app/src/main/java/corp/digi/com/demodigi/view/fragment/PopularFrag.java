package corp.digi.com.demodigi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import corp.digi.com.demodigi.R;
import corp.digi.com.demodigi.adapter.ImageAdapter;
import corp.digi.com.demodigi.response.ServerResponse;

/**
 * Created by sharukhb on 10/26/2018.
 */

public class PopularFrag extends Fragment {
    private View view;

    @BindView(R.id.rvImages)
    RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private ArrayList<ServerResponse> listImageData;
    private ArrayList<ServerResponse> popularList =new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.home_fragment_, container, false);
            ButterKnife.bind(this, view);
            //  mContext = getActivity();
            //mLandingActivity = (LandingActivity) getActivity();
            //setAdapter();
            if (getArguments() != null && getArguments().containsKey("listData")) {
                listImageData = getArguments().getParcelableArrayList("listData");
            }
            //  mContext = getActivity();
            //mLandingActivity = (LandingActivity) getActivity();
            if (listImageData != null && listImageData.size() > 0) {
                if(popularList.size()>0)
                    popularList.clear();
                for (ServerResponse item:listImageData) {
                    if (item.isPopular())
                        popularList.add(item);
                }
                setAdapter();
            } else {
                Toast.makeText(getActivity(), getString(R.string.no_record), Toast.LENGTH_SHORT).show();
            }
        }
        return view;
    }



    private void setAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        imageAdapter = new ImageAdapter(getActivity(), popularList,"popular");
        recyclerView.setAdapter(imageAdapter);
    }
}
