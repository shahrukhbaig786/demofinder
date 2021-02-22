package corp.digi.com.demodigi.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.os.Trace;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
import corp.digi.com.demodigi.view.activity.HomeActivity;
import corp.digi.com.demodigi.view.activity.SplashActivity;
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
            public void onConnect(View viewToAnimate, int position) {
                if (position < userListResponse.size()) {
                    Toast.makeText(getActivity(), getString(R.string.connect_clicked),
                            Toast.LENGTH_LONG).show();
                    new updateItemAsynTask(userListResponse.get(position), false).execute();
                 }
            }

            @Override
            public void onDecline(View viewToAnimate, int position) {
//              sadiAdapter.removeUser(viewToAnimate, position);
                if (position < userListResponse.size()) {
                    Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext()
                            , android.R.anim.slide_out_right);
                    viewToAnimate.startAnimation(animation);
                    new updateItemAsynTask(userListResponse.get(position), true).execute();
                    userListResponse.remove(position);
                    sadiAdapter.notifyDataSetChanged();
                    if (userListResponse.isEmpty()) {
                        emptyView();
                    }
                }
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
                                userListResponse.add(displayUserData);
                            }
                            Util.dismissProDialog();
                            setAdapter();
                            new StoreProfileData().execute();
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
                new Handler().postDelayed(() -> new FetcProfileData().execute(), 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class deleteAllProfileData extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            database.displayUserDataDao().deleteAll();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class FetcProfileData extends AsyncTask<Void, String, List<DisplayUserData>> {
        @Override
        protected List<DisplayUserData> doInBackground(Void... voids) {
            return database.displayUserDataDao().getAllProfile();
        }

        @Override
        protected void onPostExecute(List<DisplayUserData> responseList) {
            super.onPostExecute(responseList);
            userListResponse = new ArrayList<>();
            userListResponse.addAll(responseList);
            setAdapter();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class StoreProfileData extends AsyncTask<Void, String, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 0; i < userListResponse.size(); i++) {
                database.displayUserDataDao().insert(userListResponse.get(i));
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean responseList) {
            super.onPostExecute(responseList);

        }
    }

    private class updateItemAsynTask extends AsyncTask<Boolean, String, Boolean> {
        DisplayUserData data;
        boolean decline;

        public updateItemAsynTask(DisplayUserData data, boolean decline) {
            this.data = data;
            this.decline = decline;
        }

        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            if (decline) {
                data.isRemoved = true;
                database.displayUserDataDao().insert(data);
            } else {
                data.isConected = true;
                database.displayUserDataDao().insert(data);
            }
            return true;
        }


        @Override
        protected void onPostExecute(Boolean status) {
            if (status) {

            }
        }
    }
}