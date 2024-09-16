package com.insurancemanagment.InsuranceManagmentSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurancemanagment.InsuranceManagmentSystem.dto.Policies;

@Repository
public interface PoliciesRepository extends JpaRepository<Policies,Integer>{

}
