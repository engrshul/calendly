package com.p0.calendly.controller;

import com.p0.calendly.dto.AvailabilityDto;
import com.p0.calendly.service.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/availabilities")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping
    public ResponseEntity<AvailabilityDto> setAvailability(@Valid @RequestBody AvailabilityDto availabilityDto) {
        return ResponseEntity.ok(availabilityService.setAvailability(availabilityDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AvailabilityDto>> getAvailability(@PathVariable Long userId) {
        List<AvailabilityDto> availabilities = availabilityService.getAvailability(userId);
        return ResponseEntity.ok(availabilities);
    }

    @GetMapping("/overlap")
    public ResponseEntity<List<AvailabilityDto>> findOverlappingSchedule(
            @RequestParam List<Long> userIds) {
        List<AvailabilityDto> overlaps = availabilityService.findOverlappingSchedule(userIds);
        return ResponseEntity.ok(overlaps);
    }
}

