package corp.digi.com.demodigi.view.fragment;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import corp.digi.com.demodigi.R;
import corp.digi.com.demodigi.adapter.ImageAdapter;

/**
 * Created by sharukhb on 10/26/2018.
 */

public class SettingFrag extends Fragment {
    private View view;

    @BindView(R.id.rvImages)
    RecyclerView recyclerView;
    private ImageAdapter imageAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.home_fragment_, container, false);
            //  mContext = getActivity();
            //mLandingActivity = (LandingActivity) getActivity();
           // setAdapter();
        }
        return view;
    }



    private void setAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        //imageAdapter = new ImageAdapter();
        recyclerView.setAdapter(imageAdapter);
    }
}
