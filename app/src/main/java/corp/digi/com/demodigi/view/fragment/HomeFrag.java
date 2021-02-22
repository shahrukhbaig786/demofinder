package corp.digi.com.demodigi.view.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import corp.digi.com.demodigi.R;
import corp.digi.com.demodigi.adapter.MySadiAdapter;
import corp.digi.com.demodigi.application.DigiApplication;
import corp.digi.com.demodigi.response.DisplayUserData;
import corp.digi.com.demodigi.response.UserResponse;
import corp.digi.com.demodigi.util.RxService;
import corp.digi.com.demodigi.util.StringHelper;
import corp.digi.com.demodigi.util.Util;
import corp.digi.com.demodigi.view.dao.ProfileRoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sharukhb on 10/26/2018.
 */

@SuppressLint("ValidFragment")
public class HomeFrag extends Fragment {
    private static final String TAG = HomeFrag.class.getName();
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
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = ProfileRoomDatabase.getDatabase(getActivity());
        getServerData();
    }

    private void setAdapter() {
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sadiAdapter = new MySadiAdapter(getActivity(), userListResponse, "home");
        recyclerView.setAdapter(sadiAdapter);
        sadiAdapter.notifyDataSetChanged();


        sadiAdapter.setListener(new MySadiAdapter.ItemListener() {
            @Override
            public void onConnect() {
                Toast.makeText(getActivity(), getString(R.string.connect_clicked),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDecline(View viewToAnimate, int position) {
                sadiAdapter.removeUser(viewToAnimate, position);
            }

            @Override
            public void onItemClick(int position) {
                sadiAdapter.onItemClick(position);
            }

            @Override
            public void emptyView() {
                Toast.makeText(getActivity(), getString(R.string.empty_card),
                        Toast.LENGTH_LONG).show();
            }
        });


    }

    private void getServerData() {
        try {
            if (Util.hasInternet(getActivity())) {
                userListResponse = new ArrayList<>();
                new deleteAllProfileData().execute();
                Util.showProDialog(getActivity());
                RxService apiService = DigiApplication.getClient().create(RxService.class);
                /* You can pass param also but now nothing ,u can even use GET method ,but fpr privacy im going post*/
                Call<UserResponse> call = apiService.getData(10);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.body() != null) {
                            Log.e(TAG, response.body().toString());
                            ArrayList<UserResponse.User> list = new ArrayList<UserResponse.User>();
                            for (UserResponse.User user : response.body().getUsers()) {
                                DisplayUserData displayUserData = new DisplayUserData(user, new StringHelper(getActivity()));
                                database.displayUserDataDao().insert(displayUserData);
                                userListResponse.add(displayUserData);
                            }
                            Util.dismissProDialog();
                            setAdapter();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Util.dismissProDialog();
                        Log.e(TAG, t.toString());
                    }
                });


            } else {
                Toast.makeText(getActivity(), R.string.loading_from_db, Toast.LENGTH_SHORT).show();
                new FetcProfileData().execute();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class deleteAllProfileData extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {
            database.displayUserDataDao().deleteAll();
            return TAG;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
    @SuppressLint("StaticFieldLeak")
    private  class FetcProfileData extends AsyncTask<Void, String, ArrayList<DisplayUserData>> {
        @Override
        protected ArrayList<DisplayUserData> doInBackground(Void... voids) {
            userListResponse = new ArrayList<>();
            userListResponse.addAll(database.displayUserDataDao().getAllProfile());
            setAdapter();
            return new ArrayList<DisplayUserData>();
        }


    }
}