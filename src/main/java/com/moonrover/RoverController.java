package com.moonrover;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoverController {

    private Rover rover;

    @PostMapping("/rover/place")
    public ResponseEntity<ApiResponse> placeRover(@RequestBody RoverPlacementRequest request) {
        int x = request.getX();
        int y = request.getY();
        String facing = request.getFacing();
        // Validate facing has correct direction value
        try {
            Rover.Direction direction = Rover.Direction.valueOf(facing.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Invalid direction
            return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.INVALID_DIRECTION_VALUE, ApiConstants.ERR_INVALID_DIRECTION));
        }
        //check if positions are valid as per tabletop size
        if (Utils.isWithinTabletopBounds(x, y, 5, 5)) {
            Rover.Direction direction = Rover.Direction.valueOf(facing.toUpperCase());
            rover = new Rover(x, y, direction);
            return ResponseEntity.ok().body(new ApiResponse(ApiConstants.ROVER_PLACED_SUCCESSFULLY));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.INVALID_COORDINATES, ApiConstants.ERR_INVALID_COORDINATES));
        }
    }

    @PostMapping("/rover/move")
    public ResponseEntity<ApiResponse> moveRover() {
        if (isRoverPlaced()) {// move rover only if rover is on the table top
            int originalX = rover.getX();
            int originalY = rover.getY();
            rover.move();
            int newX = rover.getX();
            int newY = rover.getY();

            // checks if rover has not moved, assumes that it did nove because it was on the edge of the table top
            if (originalX == newX && originalY == newY) {
                return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.CANNOT_MOVE_FURTHER, ApiConstants.ERR_CANNOT_MOVE_FURTHER));
            }

            return ResponseEntity.ok().body(new ApiResponse(ApiConstants.ROVER_MOVED_SUCCESSFULLY));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.ROVER_NOT_PLACED, ApiConstants.ERR_ROVER_NOT_PLACED));
        }
    }

    @PostMapping("/rover/turn")
    public ResponseEntity<ApiResponse> turnRover(@RequestParam String direction) {
        if (isRoverPlaced()) { // turn only if rover is on table top
            if (direction.equalsIgnoreCase("left")) {
                rover.turnLeft();
                return ResponseEntity.ok().body(new ApiResponse(ApiConstants.ROVER_TURNED_LEFT_SUCCESSFULLY));
            } else if (direction.equalsIgnoreCase("right")) {
                rover.turnRight();
                return ResponseEntity.ok().body(new ApiResponse(ApiConstants.ROVER_TURNED_RIGHT_SUCCESSFULLY));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.INVALID_DIRECTION, ApiConstants.ERR_INVALID_DIRECTION));
            }
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(ApiConstants.ROVER_NOT_PLACED, ApiConstants.ERR_ROVER_NOT_PLACED));
        }
    }

    @GetMapping("/rover/report")
    public ResponseEntity<ApiResponse<ReportResponse>> getRoverReport() {
        if (isRoverPlaced()) {
            int x = rover.getX();
            int y = rover.getY();
            Rover.Direction facing = rover.getFacing();
            ReportResponse report = new ReportResponse(x, y, facing.toString());
            return ResponseEntity.ok().body(new ApiResponse<>(report));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(ApiConstants.ROVER_NOT_PLACED, ApiConstants.ERR_ROVER_NOT_PLACED));
        }
    }

    private boolean isRoverPlaced() {
        return rover != null;
    }
}


