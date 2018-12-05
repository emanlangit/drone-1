package com.boeing.drone;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drone")
public class DroneController {

	private final DroneRepository droneRepository;

	public DroneController(DroneRepository droneRepository) {
		this.droneRepository = droneRepository;
	}

	@GetMapping("")
	public Iterable<Drone> all() {
		return this.droneRepository.findAll();
	}

	@PostMapping("")
	public Drone create(@RequestBody Drone drone) {
		return this.droneRepository.save(drone);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		if (this.droneRepository.existsById(id)) {
			this.droneRepository.deleteById(id);		
		}
	}

	@PutMapping("{id}")
	public Drone update(@PathVariable Long id, @RequestBody Drone drone) {
		Drone droneToUpdate = this.droneRepository.findById(id).orElse(new Drone());
		droneToUpdate.setFirstFlight(drone.getFirstFlight());
		droneToUpdate.setTitle(drone.getTitle());
		return this.droneRepository.save(droneToUpdate);
	}
}
