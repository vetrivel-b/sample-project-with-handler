package com.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.postgresql.model.Company;




@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{

//	@Query("SELECT p FROM Company p WHERE CONCAT(p.companyId, p.companyName, p.location) LIKE %?1%")
//		public	List<Company> searchCompanyByName(String keyword);
	
	@Query("SELECT m FROM Company m WHERE CONCAT(m.location, m.companyName, m.companyId) LIKE %:query%")
	List<Company> searchCompanyByName(String query);
}
