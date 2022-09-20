package com.example.rentaperson.repository;

import com.example.rentaperson.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    List<Appointment> findAppointmentByPersonId(Integer id);

    List<Appointment> findAppointmentByPersonIdAndStatus(Integer id,String s);

    Appointment findAppointmentByIdAndStatus(Integer id,String s);

    Appointment findAppointmentById(Integer id);


    List<Appointment> findAppointmentByUserIdAndStatus(Integer id,String s);




    List<Appointment> findAppointmentByUserId(Integer id);

    Appointment findAppointmentByPersonIdAndIdAndStatus(Integer pid,Integer id,String s);

    Appointment findAppointmentByIdAndUserId(Integer id,Integer Uid);

    Appointment findAppointmentByPersonIdAndId(Integer id,Integer Uid);



}
