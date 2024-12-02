package com.p0.calendly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class AvailabilityDto {

    Long id;

    @NotNull(message = "User ID is required.")
    private Long userId;

    @NotNull(message = "Start time is required.")
    @PastOrPresent(message = "Start time cannot be in the future.")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required.")
    @Future(message = "End time must be in the future.")
    private LocalDateTime endTime;
}
