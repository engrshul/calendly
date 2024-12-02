package com.p0.calendly;

import com.p0.calendly.controller.AvailabilityController;
import com.p0.calendly.dto.AvailabilityDto;
import com.p0.calendly.service.AvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AvailabilityControllerTest {

	@Mock
	private AvailabilityService availabilityService;

	@InjectMocks
	private AvailabilityController availabilityController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSetAvailability() {
		AvailabilityDto mockAvailability = AvailabilityDto.builder()
				.userId(1L)
				.startTime(LocalDateTime.parse("2024-12-01T09:00:00"))
				.endTime(LocalDateTime.parse("2024-12-01T17:00:00"))
				.build();

		when(availabilityService.setAvailability(mockAvailability)).thenReturn(mockAvailability);

		ResponseEntity<AvailabilityDto> response = availabilityController.setAvailability(mockAvailability);

		assertEquals(200, response.getStatusCodeValue(), "Expected HTTP status 200");
		assertEquals(mockAvailability, response.getBody(), "Expected response body to match mockAvailability");
	}

	@Test
	void testGetAvailability() {
		Long userId = 1L;

		AvailabilityDto availability1 = AvailabilityDto.builder()
				.userId(1L)
				.startTime(LocalDateTime.parse("2024-12-01T09:00:00"))
				.endTime(LocalDateTime.parse("2024-12-01T11:00:00"))
				.build();

		AvailabilityDto availability2 = AvailabilityDto.builder()
				.userId(1L)
				.startTime(LocalDateTime.parse("2024-12-02T13:00:00"))
				.endTime(LocalDateTime.parse("2024-12-02T15:00:00"))
				.build();

		List<AvailabilityDto> mockAvailabilities = Arrays.asList(availability1, availability2);

		when(availabilityService.getAvailability(userId)).thenReturn(mockAvailabilities);

		ResponseEntity<List<AvailabilityDto>> response = availabilityController.getAvailability(userId);

		assertEquals(200, response.getStatusCodeValue(), "Expected HTTP status 200");
		assertEquals(mockAvailabilities, response.getBody(), "Expected response body to match mockAvailabilities");
	}

	@Test
	void testFindMultipleOverlap() {
		List<Long> userIds = Arrays.asList(1L, 2L);

		AvailabilityDto overlap1 = AvailabilityDto.builder()
				.userId(1L)
				.startTime(LocalDateTime.parse("2024-12-01T09:00:00"))
				.endTime(LocalDateTime.parse("2024-12-01T11:00:00"))
				.build();

		AvailabilityDto overlap2 = AvailabilityDto.builder()
				.userId(2L)
				.startTime(LocalDateTime.parse("2024-12-02T13:00:00"))
				.endTime(LocalDateTime.parse("2024-12-02T15:00:00"))
				.build();

		List<AvailabilityDto> mockOverlaps = Arrays.asList(overlap1, overlap2);

		when(availabilityService.findOverlappingSchedule(userIds)).thenReturn(mockOverlaps);

		ResponseEntity<List<AvailabilityDto>> response = availabilityController.findOverlappingSchedule(userIds);

		assertEquals(200, response.getStatusCodeValue(), "Expected HTTP status 200");
		assertEquals(mockOverlaps, response.getBody(), "Expected response body to match mockOverlaps");
	}
}
