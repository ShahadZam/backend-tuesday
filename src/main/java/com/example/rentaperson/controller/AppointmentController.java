package com.example.rentaperson.controller;


import com.example.rentaperson.dto.ApiResponse;
import com.example.rentaperson.dto.PostAppointment;
import com.example.rentaperson.model.Appointment;
import com.example.rentaperson.model.User;
import com.example.rentaperson.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/viewAll")
    public ResponseEntity<List> getAppointments(){
        List<Appointment> appointments=appointmentService.getAll();
        return ResponseEntity.status(200).body(appointments);
    }

    @GetMapping("/confirmed")
    public ResponseEntity<List> getConfirmedAppointments(@AuthenticationPrincipal User user){
        List<Appointment>  appointments=appointmentService.viewConfirm(user);
        return ResponseEntity.status(200).body(appointments);
    }

    @GetMapping("/new")
    public ResponseEntity<List> getNewAppointments(@AuthenticationPrincipal User user){
        List<Appointment>  appointments=appointmentService.viewNew(user);
        return ResponseEntity.status(200).body(appointments);
    }

    @GetMapping("/completed")
    public ResponseEntity<List> getCompletedAppointments(@AuthenticationPrincipal User user){
        List<Appointment>  appointments=appointmentService.viewCompleted(user);
        return ResponseEntity.status(200).body(appointments);
    }

    @PostMapping("/post")
    public ResponseEntity<ApiResponse> addAppointment(@RequestBody PostAppointment ap, @AuthenticationPrincipal User user){
        appointmentService.post(ap,user.getId());
        return ResponseEntity.status(201).body(new ApiResponse("appointment added !",201));
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<ApiResponse> ConfirmAppointment(@PathVariable Integer id, @AuthenticationPrincipal User user){
        appointmentService.makeItConfirmed(user,id);
        return ResponseEntity.status(201).body(new ApiResponse("appointment confirmed !",201));
    }

    @PostMapping("/complete/{id}")
    public ResponseEntity<ApiResponse> completeAppointment(@PathVariable Integer id, @AuthenticationPrincipal User user){
        appointmentService.makeItCompleted(user,id);
        return ResponseEntity.status(201).body(new ApiResponse("appointment completed !",201));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity updateApp(@PathVariable Integer id,@AuthenticationPrincipal User user,@RequestBody Appointment appointment){
        appointmentService.updateApp(user,appointment,id);
        return ResponseEntity.status(201).body(new ApiResponse("Appointment updated",201));
    }

    @PostMapping("/cancelNew/{id}")
    public ResponseEntity<ApiResponse> cancelNewAppointment(@PathVariable Integer id, @AuthenticationPrincipal User user){
        appointmentService.makeItCanceled(user,id);
        return ResponseEntity.status(201).body(new ApiResponse("appointment canceled !",201));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteApp(@AuthenticationPrincipal User user, @PathVariable Integer id){
        appointmentService.deleteApp(user,id);
        return ResponseEntity.status(201).body(new ApiResponse("Appointment deleted !",201));
    }

    @GetMapping("/myAppointments")
    public ResponseEntity<List> getPersonAppointments(@AuthenticationPrincipal User user){
        List<Appointment> appointments=appointmentService.viewYourAppointments(user);

        return ResponseEntity.status(200).body(appointments);
    }

    @GetMapping("/getApp/{id}")
    public ResponseEntity<Appointment> getAppById(@PathVariable Integer id,@AuthenticationPrincipal User user){
        Appointment appointment=appointmentService.getAppById(id,user);

        return ResponseEntity.status(200).body(appointment);
    }


}
