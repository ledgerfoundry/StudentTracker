package com.app.laqshya.studenttracker.activity.fragments;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.FacultyNotificationAdapter;
import com.app.laqshya.studenttracker.activity.factory.NotificationFactory;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.NotificationAndStudentViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentNotificationBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class NotificationsFragment extends Fragment {
    FragmentNotificationBinding fragmentNotificationBinding;
    FacultyNotificationAdapter facultyNotificationAdapter;

    @Inject
    NotificationFactory notificationFactory;
    NotificationAndStudentViewModel notificationAndStudentViewModel;
    @Inject
    SessionManager sessionManager;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater, container, false);
        notificationAndStudentViewModel = ViewModelProviders.of(this, notificationFactory).get(NotificationAndStudentViewModel.class);
        return fragmentNotificationBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentNotificationBinding.recyclerViewNotification.setHasFixedSize(false);
        facultyNotificationAdapter = new FacultyNotificationAdapter();
        fragmentNotificationBinding.recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentNotificationBinding.recyclerViewNotification.setAdapter(facultyNotificationAdapter);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        getData();

    }

    private void getData() {
        progressDialog.show();
        notificationAndStudentViewModel.getFaculty(sessionManager.getLoggedInUserName()).observe(this, facultyNotifications -> {
            if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
                progressDialog.dismiss();
                if (facultyNotifications != null) {
                    if(facultyNotifications.size()==0){
                        showToast("No Notifications to Show");
                    }
                    facultyNotificationAdapter.setFacultyNotificationList(facultyNotifications);
                } else {
                    showToast(getString(R.string.errorwhilefetchingdata));
                }
            } else {
                showToast(getString(R.string.internet_connection));
            }


        });

    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }
}
