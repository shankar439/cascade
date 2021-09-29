package com.HMPackage.DTO;

import lombok.Data;

import java.util.List;
@Data
public class AppointmentDTO {
	private Long appointmentId;
	private String appointmentName;
	private Long appointmentTime;
    private List<DiseaseDTO> diseaseId;
    private List<PatientDTO> patientId;
    private List<DoctorDTO> doctorId;
}
