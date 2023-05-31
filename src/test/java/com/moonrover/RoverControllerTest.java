package com.moonrover;

import com.moonrover.service.RoverService;
import com.moonrover.service.RoverServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoverControllerTest {
    private RoverController roverController;
    private RoverService roverService;

    @BeforeEach
    public void setUp() {
        roverService = new RoverServiceImpl();
        roverController = new RoverController(roverService);
    }

    @Test
    public void testPlaceRover() {
        RoverPlacementRequest request = new RoverPlacementRequest(1, 1, "NORTH");
        ResponseEntity<ApiResponse> response = roverController.placeRover(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Rover placed successfully.", response.getBody().getMessage());
    }

    @Test
    public void testPlaceRover_InvalidCoordinates() {
        RoverPlacementRequest request = new RoverPlacementRequest(6, 6, "NORTH");
        ResponseEntity<ApiResponse> response = roverController.placeRover(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals(ApiConstants.INVALID_COORDINATES, response.getBody().getMessage());
    }

    @Test
    public void testPlaceRover_InvalidFacing() {
        RoverPlacementRequest request = new RoverPlacementRequest(1, 1, "INVALID");
        ResponseEntity<ApiResponse> response = roverController.placeRover(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals(ApiConstants.INVALID_DIRECTION_VALUE, response.getBody().getMessage());
    }

    @Test
    public void testMoveRover() {
        roverController.placeRover(new RoverPlacementRequest(1, 1, "NORTH"));
        ResponseEntity<ApiResponse> response = roverController.moveRover();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Rover moved successfully.", response.getBody().getMessage());
    }

    @Test
    public void testMoveRover_CannotMoveFurther() {
        roverController.placeRover(new RoverPlacementRequest(4, 4, "NORTH"));
        ResponseEntity<ApiResponse> response = roverController.moveRover();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals(ApiConstants.CANNOT_MOVE_FURTHER, response.getBody().getMessage());
    }

    @Test
    public void testTurnRover() {
        roverController.placeRover(new RoverPlacementRequest(1, 1, "NORTH"));
        ResponseEntity<ApiResponse> response = roverController.turnRover("left");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(ApiConstants.ROVER_TURNED_LEFT_SUCCESSFULLY, response.getBody().getMessage());
    }

    @Test
    public void testTurnRover_NotPlaced() {
        ResponseEntity<ApiResponse> response = roverController.turnRover("left");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals(ApiConstants.ROVER_NOT_PLACED, response.getBody().getMessage());
    }

    @Test
    public void testTurnRover_InvalidDirection() {
        roverController.placeRover(new RoverPlacementRequest(1, 1, "NORTH"));
        ResponseEntity<ApiResponse> response = roverController.turnRover("invalid");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals(ApiConstants.INVALID_DIRECTION, response.getBody().getMessage());
    }

    @Test
    public void testGetRoverReport() {
        roverController.placeRover(new RoverPlacementRequest(1, 1, "NORTH"));
        ResponseEntity<ApiResponse<ReportResponse>> response = roverController.getRoverReport();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(1, response.getBody().getData().getX());
        assertEquals(1, response.getBody().getData().getY());
        assertEquals("NORTH", response.getBody().getData().getFacing());
    }

    @Test
    public void testGetRoverReport_NotPlaced() {
        ResponseEntity<ApiResponse<ReportResponse>> response = roverController.getRoverReport();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals(ApiConstants.ROVER_NOT_PLACED, response.getBody().getMessage());
    }
}