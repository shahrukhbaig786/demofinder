package corp.digi.com.demodigi.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
