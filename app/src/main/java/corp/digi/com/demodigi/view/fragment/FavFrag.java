package corp.digi.com.demodigi.view.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import corp.digi.com.demodigi.R;
import corp.digi.com.demodigi.adapter.ImageAdapter;
import corp.digi.com.demodigi.adapter.MySadiAdapter;
import corp.digi.com.demodigi.response.DisplayUserData;
import corp.digi.com.demodigi.response.ServerResponse;
import corp.digi.com.demodigi.view.dao.ProfileRoomDatabase;

/**
 * Created by sharukhb on 10/26/2018.
 */

@SuppressLint("ValidFragment")
public class FavFrag extends Fragment {
    private View view;
    @BindView(R.id.rvImages)
    RecyclerView recyclerView;
    private MySadiAdapter sadiAdapter;
    private ArrayList<DisplayUserData> userListResponse;
    private static ProfileRoomDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.home_fragment_, container, false);
            ButterKnife.bind(this, view);
        }
        Toast.makeText(getActivity(), R.string.loading_from_db, Toast.LENGTH_SHORT).show();
        database = ProfileRoomDatabase.getDatabase(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new FetcProfileData().execute();
    }

    private void setAdapter() {
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sadiAdapter = new MySadiAdapter(getActivity(), userListResponse, "favt");
        sadiAdapter.hideBottomOperation(true);
        recyclerView.setAdapter(sadiAdapter);
        sadiAdapter.notifyDataSetChanged();
    }


    @SuppressLint("StaticFieldLeak")
    class FetcProfileData extends AsyncTask<Void, String, List<DisplayUserData>> {
        @Override
        protected List<DisplayUserData> doInBackground(Void... voids) {
            return database.displayUserDataDao().getAllProfileFavt();
        }

        @Override
        protected void onPostExecute(List<DisplayUserData> responseList) {
            super.onPostExecute(responseList);
            userListResponse = new ArrayList<>();
            userListResponse.addAll(responseList);
            setAdapter();
            if (userListResponse.isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.no_record), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

