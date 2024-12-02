package com.p0.calendly.service;

import com.p0.calendly.dto.AvailabilityDto;
import com.p0.calendly.exception.UserNotFoundException;
import com.p0.calendly.model.Availability;
import com.p0.calendly.model.User;
import com.p0.calendly.repository.AvailabilityRepository;
import com.p0.calendly.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private static final Logger logger = LoggerFactory.getLogger(AvailabilityService.class);

    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository, UserRepository userRepository) {
        this.availabilityRepository = availabilityRepository;
        this.userRepository = userRepository;
    }

    public AvailabilityDto setAvailability(AvailabilityDto availabilityDto) {
        User user = userRepository.findById(availabilityDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + availabilityDto.getUserId(), HttpStatus.NOT_FOUND));
        logger.info("Setting availability for user ID: {}", availabilityDto.getUserId());
        Availability availability = new Availability(user, availabilityDto.getStartTime(), availabilityDto.getEndTime());
        Availability savedAvailabilityEntity = availabilityRepository.save(availability);
        return AvailabilityDto.builder().id(savedAvailabilityEntity.getId()).startTime(savedAvailabilityEntity.getStartTime()).endTime(savedAvailabilityEntity.getEndTime()).userId(savedAvailabilityEntity.getUser().getId()).build();
    }

    public List<AvailabilityDto> getAvailability(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userId, HttpStatus.NOT_FOUND));
        logger.info("Fetching availability for user ID: {}", userId);
        List<Availability> availabilityEntityList = availabilityRepository.findByUserId(userId);

        return availabilityEntityList.stream()
                .map(availability -> AvailabilityDto.builder()
                        .id(availability.getId())
                        .userId(availability.getUser().getId())
                        .startTime(availability.getStartTime())
                        .endTime(availability.getEndTime())
                        .build())
                .collect(Collectors.toList());
    }

    public List<AvailabilityDto> findOverlappingSchedule(List<Long> userIds) {

        if (userIds == null || userIds.size() < 2) {
            return new ArrayList<>();
        }
        logger.info("Finding overlapping schedules for user IDs: {}", userIds);
        List<List<Availability>> allUserAvailabilities = userIds.stream()
                .map(availabilityRepository::findByUserId)
                .collect(Collectors.toList());

        List<Availability> overlappingAvailabilities = findOverlappingAvailabilities(allUserAvailabilities);

        return overlappingAvailabilities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<Availability> findOverlappingAvailabilities(List<Availability> availabilities1, List<Availability> availabilities2) {
        return availabilities1.stream()
                .flatMap(a1 -> availabilities2.stream()
                        .filter(a2 -> a1.getEndTime().isAfter(a2.getStartTime()) && a1.getStartTime().isBefore(a2.getEndTime()))
                        .map(a2 -> new Availability(
                                a1.getUser(),
                                a2.getUser(),
                                a1.getStartTime().isAfter(a2.getStartTime()) ? a1.getStartTime() : a2.getStartTime(),
                                a1.getEndTime().isBefore(a2.getEndTime()) ? a1.getEndTime() : a2.getEndTime()
                        )))
                .collect(Collectors.toList());
    }


    private List<Availability> findOverlappingAvailabilities(List<List<Availability>> availabilityLists) {
        if (availabilityLists.isEmpty()) return new ArrayList<>();

        return availabilityLists.stream()
                .reduce(this::findOverlappingAvailabilities)
                .orElse(new ArrayList<>());
    }


    private AvailabilityDto convertToDto(Availability availability) {
        return AvailabilityDto.builder()
                .id(availability.getId())
                .startTime(availability.getStartTime())
                .endTime(availability.getEndTime())
                .build();
    }
}
