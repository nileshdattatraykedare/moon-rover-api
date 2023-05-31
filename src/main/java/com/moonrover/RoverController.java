package com.moonrover;

import com.moonrover.service.RoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoverController {
    private RoverService roverService;

    @Autowired
    public RoverController(RoverService roverService) {
        this.roverService = roverService;
    }

    @PostMapping("/rover/place")
    public ResponseEntity<ApiResponse> placeRover(@RequestBody RoverPlacementRequest request) {
        int x = request.getX();
        int y = request.getY();
        String facing = request.getFacing();

        // Validate if positions are within tabletop bounds
        if (!Utils.isWithinTabletopBounds(x, y, 5, 5)) {
            return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.INVALID_COORDINATES, ApiConstants.ERR_INVALID_COORDINATES));
        }

        // Validate if facing direction is correct
        try {
            Rover.Direction direction = Rover.Direction.valueOf(facing.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Invalid direction
            return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.INVALID_DIRECTION_VALUE, ApiConstants.ERR_INVALID_DIRECTION));
        }

        // If validations pass, delegate the placement to the service
        Rover.Direction direction = Rover.Direction.valueOf(facing.toUpperCase());
        roverService.placeRover(x, y, direction);

        return ResponseEntity.ok().body(new ApiResponse(ApiConstants.ROVER_PLACED_SUCCESSFULLY));
    }

    @PostMapping("/rover/move")
    public ResponseEntity<ApiResponse> moveRover() {
        if (!isRoverPlaced()) {
            return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.ROVER_NOT_PLACED, ApiConstants.ERR_ROVER_NOT_PLACED));
        }

        try {
            roverService.moveRover(); // Delegate to roverService to move the rover

            return ResponseEntity.ok().body(new ApiResponse(ApiConstants.ROVER_MOVED_SUCCESSFULLY));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.CANNOT_MOVE_FURTHER, ApiConstants.ERR_CANNOT_MOVE_FURTHER));
        }
    }

    @PostMapping("/rover/turn")
    public ResponseEntity<ApiResponse> turnRover(@RequestParam String direction) {
        if (!isRoverPlaced()) {
            return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.ROVER_NOT_PLACED, ApiConstants.ERR_ROVER_NOT_PLACED));
        }
            if (direction.equalsIgnoreCase("left")) {
                roverService.turnRoverLeft();
                return ResponseEntity.ok().body(new ApiResponse(ApiConstants.ROVER_TURNED_LEFT_SUCCESSFULLY));
            } else if (direction.equalsIgnoreCase("right")) {
                roverService.turnRoverRight();
                return ResponseEntity.ok().body(new ApiResponse(ApiConstants.ROVER_TURNED_RIGHT_SUCCESSFULLY));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.INVALID_DIRECTION, ApiConstants.ERR_INVALID_DIRECTION));
            }

    }

    @GetMapping("/rover/report")
    public ResponseEntity<ApiResponse<ReportResponse>> getRoverReport() {
        try {
            ReportResponse report = roverService.getRoverReport();
            return ResponseEntity.ok().body(new ApiResponse<>(report));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(ApiConstants.ROVER_NOT_PLACED, ApiConstants.ERR_ROVER_NOT_PLACED));
        }
    }

    private boolean isRoverPlaced() {
        return roverService.isRoverPlaced();
    }
}


