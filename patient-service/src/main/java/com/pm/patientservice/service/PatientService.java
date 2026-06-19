package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository; //dependency injection
    }

//    public List<patientresponsedto> getPatients() {
//        List<patient> patients = patientRepository.findAll();
//        List<patientresponsedto> patientResponseDTOs = patients.stream()
//                .map(patient -> PatientMapper.toDTO(patient)).toList();
//        return patientResponseDTOs;
//    }

    //below one is optimized version of above code

    public List<PatientResponseDTO> getPatients() {
        return patientRepository.findAll().stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email " + patientRequestDTO.getEmail() + " already exists");
        }
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(newPatient);
    }
}
