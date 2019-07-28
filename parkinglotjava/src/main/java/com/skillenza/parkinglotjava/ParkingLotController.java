package com.skillenza.parkinglotjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api")
public class ParkingLotController {
	
	@Autowired
	private ParkingLotRepository parkingLotRepo;
	
	@GetMapping("/parkings")
    public List<ParkingLot> getAllParkedVehicle(){
    	return parkingLotRepo.findAll();
    }
	
	@PostMapping("/parkings")
	public ResponseEntity<String> parkVehicleCalculateAmount(ParkingLot parkingLot){
		int parkingAmount = 0;
		if(parkingLot.getParkingDuration() == 0) {
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}
		if(parkingLot.getParkingDuration()>60) {
			int addOnDuration = parkingLot.getParkingDuration() - 60;
			int extraAmount = (int) (addOnDuration*0.333);
			parkingAmount = 20 + extraAmount;
		} else {
			parkingAmount = 20;
		}
		parkingLot.setParkingAmount(parkingAmount);
		parkingLotRepo.saveAndFlush(parkingLot);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}

