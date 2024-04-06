package dev.archimedes.controllers;

import dev.archimedes.dtos.AppointmentDTO;
import dev.archimedes.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody @Validated AppointmentDTO appointmentDTO){
        log.info(appointmentDTO.toString());
        return appointmentService.book(appointmentDTO);
    }

    @PatchMapping("/cancel")
    public ResponseEntity<?> cancelAppointment(String appointmentId){
        return appointmentService.cancel(appointmentId);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAppointments(){
        return appointmentService.getAppointments();
    }

    @GetMapping("/get-by-date")
    public ResponseEntity<?> getAppointments(@RequestParam("date") String date){
        return appointmentService.getAppointments(LocalDate.parse(date));
    }

}
