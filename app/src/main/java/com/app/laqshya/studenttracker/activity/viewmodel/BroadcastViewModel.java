package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.repository.BroadcastRepository;

import java.util.List;

public class BroadcastViewModel extends ViewModel {
    private BroadcastRepository broadcastRepository;

    public BroadcastViewModel(BroadcastRepository broadcastRepository) {
        this.broadcastRepository=broadcastRepository;

    }
    public LiveData<StudentInfo.StudentInfoList> getStudents(String centername){
        return broadcastRepository.getBatchForCounsellor(centername);
    }
    public LiveData<BatchInformationResponse> getBatchesForFaculty(String facultyName){
        return broadcastRepository.getBatchForFaculty(facultyName);
    }
    public LiveData<String> sendSingleStudentNotification(String counsellorphone,String phone,String title,String message
    ,String flag){
            return broadcastRepository.sendSingleStudentNotiication(counsellorphone,phone,title,message,flag);
    }
    public LiveData<String> sendSingleBatchNotification(String counsellorphone,String batchid,String title,String message
                                                        ,String facultyid
            ,String flag){
        return broadcastRepository.sendSingleBatchNotification(counsellorphone,batchid,title,message,facultyid,flag);
    }
    public LiveData<String> sendAllBatchNotification(String counsellorphone,String title,String message
            ){
        return broadcastRepository.sendAllBatchNotification(counsellorphone,title,message);
    }
    public LiveData<String> sendNotificationtopendingStudents(String counsellorphone,String title,String message
            ,String student_id){
        return broadcastRepository.sendDueFeesNotification(counsellorphone,title,message,student_id);


    }
    public LiveData<String> sendSingleFacultyBatchNotification(String batchid,String title,String message
            ,String facultyid
            ,String flag){
        return broadcastRepository.sendSingleBatchFacultyNotification(batchid,title,message,facultyid,flag);
    }
}
