package com.postgresql.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.postgresql.dto.CompanyDto;
import com.postgresql.exception.ResourceNotFoundException;
import com.postgresql.model.Company;
import com.postgresql.repository.CompanyRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@Service
public class CompanyService {

	@Autowired
	private CompanyRepository repo;
	
	public List<Company> viewList() {
		return repo.findAll();
	}
	
//	public Company viewById(long companyId) {
//		//Users selectUser=userRepo.getById(userid);
//		return repo.findById(companyId).orElse(null);
//		
//	}
//	
	public Company newCompany(CompanyDto comp) {
		Company company=new Company();
		company.setCompanyName(comp.getName());
		company.setLocation(comp.getCompanyLocation());
		return repo.save(company);
	}
	
	

	public Company UpdateById(long companyId, CompanyDto	companyDto ) {
		Company existingDetails=repo.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("company not found with this Id "+companyId));
		existingDetails.setCompanyName(companyDto.getName());
		existingDetails.setLocation(companyDto.getCompanyLocation());
		return repo.save(existingDetails);
	}

	public Company UpdateUsingPatch(long companyId, String location) {
		
		Company existing=repo.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("company not found with this Id"+ companyId));
		existing.setLocation(location);
		return repo.save(existing);
	
	}
	
	public ResponseEntity<Company> deleteById(long companyId) {
		Company comp=repo.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("company not found with this Id"+companyId));
		repo.delete(comp);
		
		return ResponseEntity.ok().build();
	}

	public List<Company> findCompanysWithSorting(String field){
        return  repo.findAll(Sort.by(Sort.Direction.ASC,field));
    }

	public Page<Company> findCompanyWithPagination(int offset,int pageSize){
        Page<Company> products = repo.findAll(PageRequest.of(offset, pageSize));
        return  products;
    }
	
	public Page<Company> findCompanysWithPaginationAndSorting(int offset,int pageSize,String field){
        Page<Company> products = repo.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return  products;
    }
	
	public List<Company> searchCompany(String query){
		List<Company> comp = repo.searchCompanyByName(query);
		return comp;
		}
//	public List<Company> listAll(String keyword) {
//        if (keyword != null) {
//            return repo.searchCompanyByName(keyword); 
//        }
//        return repo.findAll();
//    }

	public Company viewById(long parseLong) {
		return repo.findById(parseLong).orElse(null);
	}
	
}
