package com.HMPackage.controller;

import java.util.List;
import java.util.Optional;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.HMPackage.DTO.DiseaseDTO;
import com.HMPackage.baseResponse.BaseResponse;
import com.HMPackage.entity.Disease;
import com.HMPackage.serviceImplementation.DiseaseServiceImpl;

@RestController
@RequestMapping("/disease")
public class DiseaseController {
	@Autowired
	private DiseaseServiceImpl diseaseServiceImpl;

    @PostMapping("/create")
    @Authorization(value="Bearer")
    public BaseResponse addDisease(@RequestBody DiseaseDTO diseaseDTO) {
    	BaseResponse<Disease> baseResponse = null;
        baseResponse = BaseResponse.<Disease>builder().Data(diseaseServiceImpl.AddDisease(diseaseDTO)).build();
    	return baseResponse;
    }

    @GetMapping("/getAll")
    @Authorization(value="Bearer")
    public BaseResponse<List<Disease>> getAll(){
    	BaseResponse<List<Disease>> baseResponse = null;
    	baseResponse = BaseResponse.<List<Disease>>builder().Data(diseaseServiceImpl.listAlldisease()).build();
        return baseResponse;
    }

    @PutMapping("/update")
    @Authorization(value="Bearer")
    public BaseResponse<Optional<Disease>> updateDiseaseById(@RequestBody DiseaseDTO diseaseDTO){
    	BaseResponse<Optional<Disease>> baseResponse = null;
        baseResponse = BaseResponse.<Optional<Disease>>builder().Data(diseaseServiceImpl.UpdateDiseaseById(diseaseDTO)).build();
    	return baseResponse;
    }

    @PutMapping("/softdelete")
    @Authorization(value="Bearer")
    public BaseResponse<Optional<Disease>>  deleteSoft(@RequestBody DiseaseDTO diseaseDTO){
    	BaseResponse<Optional<Disease>> baseResponse = null;
        baseResponse = BaseResponse.<Optional<Disease>>builder().Data(diseaseServiceImpl.DeleteSoftDisease(diseaseDTO)).build();
    	return baseResponse;
    }
}
