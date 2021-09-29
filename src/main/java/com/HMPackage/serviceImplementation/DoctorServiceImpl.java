package com.HMPackage.serviceImplementation;

import java.util.Optional;
import com.HMPackage.baseResponse.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.HMPackage.DTO.DoctorDTO;
import com.HMPackage.entity.Doctor;
import com.HMPackage.entity.User;
import com.HMPackage.repository.DoctorRepository;
import com.HMPackage.repository.UserRepository;
import com.HMPackage.service.DoctorServiceInterface;

@Service
public class DoctorServiceImpl implements DoctorServiceInterface{
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Doctor AddDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setDoctorName(doctorDTO.getDoctorName());
        doctorDTO.getId().forEach(userDTO -> {
            Optional<User> user=userRepository.findById(userDTO.getId());
            if (user.isPresent())
            {
                doctor.setUser(user.get());
            }
            else
            {
                throw new RuntimeException("Enter A Valid UserID");
            }
        });
        doctor.setDoctorId(doctorDTO.getDoctorId());
        doctor.setDoctorName(doctorDTO.getDoctorName());
        doctorRepository.save(doctor);
        return doctor;
    }

	@Override
    public Optional<Doctor> findByDoctorId(Long doctorId) {
        Optional<Doctor> doctor=doctorRepository.findByDoctorId(doctorId);
        return doctor;
         
    }

	@Override
    public Optional<Doctor>UpdateDoctorById(DoctorDTO doctorDTO) {
        Optional<Doctor> doctor = doctorRepository.findByDoctorId(doctorDTO.getDoctorId());
        if (doctor.isPresent())
        {
            doctor.get().setDoctorName(doctorDTO.getDoctorName());
        }
        else
        {
            throw new RuntimeException("Enter A Valid DoctorID");
        }
        doctorDTO.getId().forEach(userDTO -> {
            Optional<User> user=userRepository.findById(userDTO.getId());
            if (user.isPresent())
            {
                doctor.get().setUser(user.get());
            }
            else
            {
                throw new RuntimeException("Enter A Valid UserID");
            }

        });
        doctorRepository.save(doctor.get());
        return doctor;
    }

	@Override
    public Optional<Doctor> DeleteSoftDoctor(DoctorDTO doctorDTO) {
        Optional<Doctor> doctor = doctorRepository.findByDoctorId(doctorDTO.getDoctorId());
        if (doctor.isPresent())
        {
            doctor.get().setIsDelete(1);
        }
        else
        {
            throw new RuntimeException("Enter A Valid DoctorID");
        }
        doctorRepository.save(doctor.get());
        return doctor; 
    }

    @Override
    public PageResponse<Doctor> pageByDoctorName(int pageno, int pageSize, String doctorName) {
        Pageable paging= PageRequest.of(pageno,pageSize);
        Page<Doctor> doctor = doctorRepository.searchAllByDoctorNameLike("%" + doctorName + "%", paging);
        PageResponse pageResponse=new PageResponse();
        pageResponse.setResponse(doctor);
        pageResponse.setPages(doctor.getTotalPages());
        return pageResponse;
    }
}
