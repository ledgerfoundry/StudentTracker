package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.AddSchedules;
import com.app.laqshya.studenttracker.activity.EditSchedules;
import com.app.laqshya.studenttracker.activity.MainActivity;
import com.app.laqshya.studenttracker.activity.MainScreenNavigationDrawer;
import com.app.laqshya.studenttracker.activity.fragments.AttendanceFragment;
import com.app.laqshya.studenttracker.activity.fragments.CompletionBatchesFragment;
import com.app.laqshya.studenttracker.activity.fragments.DeletedBatchesFragment;
import com.app.laqshya.studenttracker.activity.fragments.FacultyBatchCardsFragment;
import com.app.laqshya.studenttracker.activity.fragments.NotificationsFragment;
import com.app.laqshya.studenttracker.activity.fragments.StudentAttendanceByFacultyFragment;
import com.app.laqshya.studenttracker.activity.fragments.StudentReceivedNotificationFragment;
import com.app.laqshya.studenttracker.activity.fragments.SyllabusFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.AllStudentsAllCentresFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.PendingFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.SameBatchFacultyFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.SameBatchFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.SingleStudentNotificationFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {MainActivityModule.class, MainScreenNavigationModule.class, AddScheduleModule.class, EditScheduleModule.class,
        BroadcastModule.class, NotificationModule.class})
abstract class StudentBindingModule {
    @ContributesAndroidInjector
    abstract MainActivity getMainActivity();

    @ContributesAndroidInjector
    abstract MainScreenNavigationDrawer getMainScreenNavigationDrawer();

    @ContributesAndroidInjector
    abstract AddSchedules getAddSchedules();

    @ContributesAndroidInjector
    abstract AttendanceFragment getAttendanceFragment();

    @ContributesAndroidInjector
    abstract EditSchedules getEditSchedules();

    @ContributesAndroidInjector
    abstract DeletedBatchesFragment deletedBatchesFragment();

    @ContributesAndroidInjector
    abstract CompletionBatchesFragment completionBatchesFragment();

    @ContributesAndroidInjector
    abstract SingleStudentNotificationFragment singleStudentNotificationFragment();

    @ContributesAndroidInjector
    abstract SameBatchFragment sameBatchFragment();

    @ContributesAndroidInjector
    abstract AllStudentsAllCentresFragment allStudentsAllCentresFragment();

    @ContributesAndroidInjector
    abstract PendingFragment pendingFragment();

    @ContributesAndroidInjector
    abstract SameBatchFacultyFragment sameBatchFacultyFragment();

    @ContributesAndroidInjector
    abstract StudentAttendanceByFacultyFragment studentAttendanceByFacultyFragment();
    @ContributesAndroidInjector
    abstract StudentReceivedNotificationFragment studentReceivedNotificationFragment();
    @ContributesAndroidInjector
    abstract NotificationsFragment notificationsFragment();
    @ContributesAndroidInjector
    abstract SyllabusFragment syllabusFragment();
    @ContributesAndroidInjector
    abstract FacultyBatchCardsFragment facultyBatchCardsFragment();

}
