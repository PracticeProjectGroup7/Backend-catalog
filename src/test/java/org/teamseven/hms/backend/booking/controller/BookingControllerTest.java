package org.teamseven.hms.backend.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.teamseven.hms.backend.booking.dto.BookingOverview;
import org.teamseven.hms.backend.booking.dto.BookingPaginationResponse;
import org.teamseven.hms.backend.booking.dto.BookingType;
import org.teamseven.hms.backend.booking.service.BookingService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {
    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController controller;

    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mockMvc =  MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetBookingHistory_paginationNotSpecified_assertPaginationUseDefault() throws Exception {
        BookingPaginationResponse bookingPaginationResponse = getMockResponse();
        ResponseWrapper.Success<BookingPaginationResponse> expectedResponse = new ResponseWrapper.Success<>(
                bookingPaginationResponse
        );

        when(bookingService.getBookingHistory(any(UUID.class), any(int.class), any(int.class)))
                .thenReturn(bookingPaginationResponse);

        mockMvc.perform(get("/api/v1/services/booking-history/07fec0a8-7145-11ee-8684-0242ac130003"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        verify(bookingService).getBookingHistory(
                UUID.fromString("07fec0a8-7145-11ee-8684-0242ac130003"),
                1,
                10
        );
    }

    @Test
    public void testGetBookingHistory_paginationCustomised_assertPaginationAsRequested() throws Exception {
        BookingPaginationResponse bookingPaginationResponse = getMockResponse();
        ResponseWrapper.Success<BookingPaginationResponse> expectedResponse = new ResponseWrapper.Success<>(
                bookingPaginationResponse
        );

        when(bookingService.getBookingHistory(any(UUID.class), any(int.class), any(int.class)))
                .thenReturn(bookingPaginationResponse);

        mockMvc.perform(get("/api/v1/services/booking-history/07fec0a8-7145-11ee-8684-0242ac130003?page=2&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        verify(bookingService).getBookingHistory(
                UUID.fromString("07fec0a8-7145-11ee-8684-0242ac130003"),
                2,
                5
        );
    }

    private BookingPaginationResponse getMockResponse() throws ParseException {
        return new BookingPaginationResponse(
                List.of(
                        new BookingOverview(
                                UUID.randomUUID(),
                                new String[]{"1", "2"},
                                BookingType.TEST,
                                "Test booking description",
                                new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-12")
                        )
                ),
                1,
                "Jane Doe"
        );
    }
}
