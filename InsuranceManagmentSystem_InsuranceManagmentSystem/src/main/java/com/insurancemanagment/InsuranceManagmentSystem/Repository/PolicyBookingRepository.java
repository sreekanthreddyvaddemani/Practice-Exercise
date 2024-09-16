package com.insurancemanagment.InsuranceManagmentSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurancemanagment.InsuranceManagmentSystem.dto.PolicyBooking;
@Repository
public interface PolicyBookingRepository extends JpaRepository<PolicyBooking, Integer> {

	
	int countByStatus(String string);

}
