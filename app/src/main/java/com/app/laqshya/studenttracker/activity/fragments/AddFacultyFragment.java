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
import com.app.laqshya.studenttracker.databinding.RegisterFacultyBinding;

import java.util.Objects;

import timber.log.Timber;

public class AddFacultyFragment extends Fragment {
    private RegisterFacultyBinding registerFacultyBinding;
    private StringBuilder coursesBuilder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerFacultyBinding = DataBindingUtil.inflate(inflater, R.layout.register_faculty, container, false);
        return registerFacultyBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        coursesBuilder = new StringBuilder();

        NavDrawerViewModel navDrawerViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NavDrawerViewModel.class);
        navDrawerViewModel.isProgress.observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {

                registerFacultyBinding.progressBar.setVisibility(View.VISIBLE);
            } else {
                registerFacultyBinding.progressBar.setVisibility(View.GONE);
            }
        });

        if (Utils.isNetworkConnected(getActivity())) {
            registerFacultyBinding.facultyCourses.setText(getString(R.string.course_name));
            navDrawerViewModel.getCourseList().observe(this, strings -> {
                ArrayAdapter<String> courses;
                if (strings != null) {
                    courses = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                            strings);
                    registerFacultyBinding.facultyCourses.setAdapter(courses, false, selected -> {
                        coursesBuilder.setLength(0);
                        for (int i = 0; i < selected.length; i++) {
                            if (selected[i]) {
                                coursesBuilder.append(courses.getItem(i)).append("|");
                            }
                        }
                        if (coursesBuilder.length() > 0)
                            coursesBuilder.deleteCharAt(coursesBuilder.toString().length() - 1);
                        Timber.d(coursesBuilder.toString());
                        registerFacultyBinding.facultyCourses.setText(getString(R.string.selectcourse));


                    }, getString(R.string.selectcourse));
                }

            });
        } else {
            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();

        }

        registerFacultyBinding.btnSignup.setOnClickListener(v -> {
            if (Utils.isNetworkConnected(getActivity())) {
                String name = registerFacultyBinding.inputFacultyName.getText().toString();
                String facultyPhone = registerFacultyBinding.inputFacultyNumber.getText().toString();
                String email = registerFacultyBinding.inputEmail.getText().toString();
                if (coursesBuilder.length() == 0) {
                    Toast.makeText(getActivity(), getString(R.string.courseError), Toast.LENGTH_SHORT).show();

                } else if (Utils.isEmpty(name, facultyPhone, email)) {
                    Toast.makeText(getActivity(), getString(R.string.fieldsEmpty), Toast.LENGTH_SHORT).show();
                } else if (!Utils.isValidEmail(email)) {
                    Toast.makeText(getActivity(), getString(R.string.email_error), Toast.LENGTH_SHORT).show();
                } else {

                    navDrawerViewModel.registerFaculty(name, facultyPhone, email, coursesBuilder.toString())
                            .observe(AddFacultyFragment.this, s ->
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