package com.app.laqshya.studenttracker.activity.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.NavDrawerViewModel;
import com.app.laqshya.studenttracker.databinding.RegisterCousellorBinding;

import java.util.Objects;

public class AddCounsellorFragment extends Fragment {
    NavDrawerViewModel navDrawerViewModel;
    RegisterCousellorBinding registerCousellorBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerCousellorBinding = DataBindingUtil.inflate(inflater, R.layout.register_cousellor, container, false);
        return registerCousellorBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navDrawerViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NavDrawerViewModel.class);

        navDrawerViewModel.isProgress.observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
//                progressDialog.show();

                registerCousellorBinding.progressBar.setVisibility(View.VISIBLE);
            } else {
                registerCousellorBinding.progressBar.setVisibility(View.GONE);
            }
        });


        if (Utils.isNetworkConnected(getActivity())) {
            navDrawerViewModel.getCenterList().observe(this, centerLists -> {
                if (centerLists != null && centerLists.size() > 0) {
                    ArrayAdapter<String> center = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                            centerLists);
                    registerCousellorBinding.centerList.setAdapter(center);
//

                }
            });
        } else {
            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();

        }

        registerCousellorBinding.btnSignup.setOnClickListener(v -> {
            if (Utils.isNetworkConnected(getActivity())) {
                String name = registerCousellorBinding.inputCounsellorName.getText().toString();
                String counsellorphone = registerCousellorBinding.inputCounsellorNumber.getText().toString();
                String centerphone = registerCousellorBinding.inputCenterNumber.getText().toString();
                String email = registerCousellorBinding.inputEmail.getText().toString();
                if (Utils.isEmpty(name, counsellorphone, centerphone, email)) {
                    Toast.makeText(getActivity(), "Please check all fields", Toast.LENGTH_SHORT).show();
                } else if (!Utils.isValidEmail(email)) {
                    Toast.makeText(getActivity(), "Please check the email address", Toast.LENGTH_SHORT).show();
                } else {
                    navDrawerViewModel.registerCounsellor(email, centerphone, counsellorphone,
                            registerCousellorBinding.centerList.getSelectedItem().toString(), name)
                            .observe(AddCounsellorFragment.this, s ->
                            {
                                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                                navDrawerViewModel.isProgress.setValue(false);
                            });

                }
            } else {
                Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();

            }
        });


    }

}
