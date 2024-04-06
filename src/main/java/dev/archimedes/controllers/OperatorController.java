package dev.archimedes.controllers;

import dev.archimedes.service.AppointmentService;
import dev.archimedes.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/operator")
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;

    private final AppointmentService appointmentService;


    @PatchMapping("/close-slot")
    public ResponseEntity<?> closeSlot(@RequestParam("slotId") String slotId){
        return operatorService.closeSlot(slotId);
    }

    @PatchMapping("/close-slot-by-date")
    public ResponseEntity<?> closeSlot(@RequestParam("operatorId") String operatorId, @RequestParam("date") String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return operatorService.closeSlot(operatorId, LocalDate.parse(date, formatter));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getSlot(@RequestParam("operatorId") String operatorId, @RequestParam("date") String date){
        return operatorService.getSlotsByDate(operatorId, LocalDate.parse(date));
    }

    @GetMapping("/get-after-date")
    public ResponseEntity<?> getSlot(@RequestParam("date") String date){
        return operatorService.getSlotsFromDate(LocalDate.parse(date));
    }

    @PatchMapping("/open-slot")
    public ResponseEntity<?> openSlot(@RequestParam("slotId") String slotId){
        return operatorService.openSlot(slotId);
    }

    @GetMapping("/get-appointment")
    public ResponseEntity<?> getAppointments(String operatorId){
        return appointmentService.getAppointments(operatorId);
    }
}
