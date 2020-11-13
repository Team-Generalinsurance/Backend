package com.lti.repository;

import com.lti.entity.Policy;
import com.lti.entity.Vehicle;


public interface VehicleRepository {
	long save(Vehicle vehicle);
	Vehicle findVehicleById(long vehicleId);
}