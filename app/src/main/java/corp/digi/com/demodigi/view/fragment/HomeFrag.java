package corp.digi.com.demodigi.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import corp.digi.com.demodigi.R;
import corp.digi.com.demodigi.adapter.ImageAdapter;
import corp.digi.com.demodigi.adapter.MySadiAdapter;
import corp.digi.com.demodigi.application.DigiApplication;
import corp.digi.com.demodigi.response.DisplayUserData;
import corp.digi.com.demodigi.response.ServerResponse;
import corp.digi.com.demodigi.response.UserResponse;
import corp.digi.com.demodigi.util.RxService;
import corp.digi.com.demodigi.util.StringHelper;
import corp.digi.com.demodigi.util.Util;
import corp.digi.com.demodigi.view.activity.HomeActivity;
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
                                userListResponse.add(new DisplayUserData(user, new StringHelper(getActivity())));
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
                Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
