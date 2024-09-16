package com.insurancemanagment.InsuranceManagmentSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurancemanagment.InsuranceManagmentSystem.dto.Questions;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Integer> {


}
