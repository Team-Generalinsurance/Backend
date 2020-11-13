package com.lti.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Policy;
import com.lti.entity.Vehicle;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository {

	@PersistenceContext
	EntityManager em;

	
	@Override
	@Transactional
	public long save(Vehicle vehicle) {
		// TODO Auto-generated method stub
		Vehicle v= em.merge(vehicle);
//		System.out.println("in Repository");
	
		long id= v.getVehicleId();
//		System.out.println(id);
		return id;
	}

	@Override
	public Vehicle findVehicleById(long vehicleId) {
		// TODO Auto-generated method stub
		return em.find(Vehicle.class, vehicleId);
	}

	
}