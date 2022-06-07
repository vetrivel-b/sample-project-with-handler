package com.postgresql.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgresql.dto.APIResponse;
import com.postgresql.dto.CompanyDto;
import com.postgresql.handler.CompanyHandler;
import com.postgresql.model.Company;
import com.postgresql.service.CompanyService;



@RestController
public class CompanyController {

	@Autowired
	private CompanyService service;
	
	@Autowired
	private CompanyHandler handler;
	
	@GetMapping("/viewCompany")
	public APIGatewayProxyResponseEvent viewCompanyList(@Param("flag") String flag) {
		APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
		Map<String, String> params = new HashMap<String, String>();
		params.put("flag", flag);
		event.setQueryStringParameters(params);
		return handler.handleRequest(event,null);
	}
	
	@GetMapping("/viewById")
	public APIGatewayProxyResponseEvent viewById(@Param("flag")String flag, @Param("companyId") long companyId) {
		APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
		Map<String, String> params = new HashMap<String, String>();
		params.put("flag", flag);
		params.put("companyId", String.valueOf(companyId));
		event.setQueryStringParameters(params);
		return handler.handleRequest(event, null);
		
	}
	
	@PostMapping("/createNew")
	public APIGatewayProxyResponseEvent newCompanyWithDto(@Param("flag")String flag, @RequestBody CompanyDto companyDto) {
		APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
		Map<String, String> params = new HashMap<String, String>();
		params.put("flag", flag);
		event.setQueryStringParameters(params);
		String body = null;
		try {
			body = new ObjectMapper().writeValueAsString(companyDto);
			event.setBody(body);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		event.setBody(body);
		
		return handler.handleRequest(event, null);
	}
	
	@PostMapping("/addCompany")
	public APIGatewayProxyResponseEvent addNewCompany(@Param("flag")String flag, @RequestBody Company comp) {
		APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("flag", flag);
		event.setBody(flag);
		String body;
		try {
			body = new ObjectMapper().writeValueAsString(comp);
			event.setBody(body);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return handler.handleRequest(event, null);
	}
	
	@PutMapping("/update/")
	public APIGatewayProxyResponseEvent updateCompany(@Param("flag")String flag, @Param(value = "companyId") long companyId, @RequestBody CompanyDto companyDto) {
		APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("flag", flag);
		headers.put("companyId", String.valueOf(companyId));
		event.setQueryStringParameters(headers);
		
		String body = null;
		try {
			body = new ObjectMapper().writeValueAsString(companyDto);
			event.setBody(body);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return service.UpdateById(companyId, comp);
		return handler.handleRequest(event, null);
		
	}
	
	@PatchMapping("/updateByField")
	public APIGatewayProxyResponseEvent updateCompanyUsingPatchMapping(@Param("flag")String flag ,@RequestParam long companyId, @RequestParam String location) {
		APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("flag", flag);
		headers.put("companyId", String.valueOf(companyId));
		headers.put("location", location);
		event.setQueryStringParameters(headers);
		return handler.handleRequest(event, null);
//		return service.UpdateUsingPatch(companyId, location);
	}
	
	@DeleteMapping("/deleteCompany/")
	public APIGatewayProxyResponseEvent deleteCompany(@Param("flag")String flag , @RequestParam long companyId) {
		APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("flag", flag);
		headers.put("companyId", String.valueOf(companyId));
		event.setQueryStringParameters(headers);
		return handler.handleRequest(event, null);
		
//		return service.deleteById(companyId);
	}
	
	@GetMapping("/sorting")
    private APIGatewayProxyResponseEvent getProductsWithSort(@Param(value="flag")String flag,@RequestParam String field) {
		APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
		Map<String, String> params = new HashMap<String,String>();
		params.put("flag", String.valueOf(flag));
		params.put("field", String.valueOf(field));
		event.setQueryStringParameters(params);
		return handler.handleRequest(event, null);
    }
	
	@GetMapping("/pagination")
    private APIGatewayProxyResponseEvent getProductsWithPagination(@Param(value="flag") String flag,@RequestParam int offset, @RequestParam int pageSize) {
//        Page<Company> productsWithPagination = service.findProductsWithPagination(offset, pageSize);
//        return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
		APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
		Map<String, String> params = new HashMap<String,String>();
		params.put("flag", flag);
		params.put("offset", String.valueOf(offset));
		params.put("pageSize", String.valueOf(pageSize));
		event.setQueryStringParameters(params);
		return handler.handleRequest(event, null);
    }
	
	@GetMapping("/paginationAndSort")
    private APIGatewayProxyResponseEvent getProductsWithPaginationAndSort(@Param(value="flag") String flag,@RequestParam int offset, @RequestParam int pageSize,@RequestParam String field) {
//        Page<Company> productsWithPagination = service.findProductsWithPaginationAndSorting(offset, pageSize, field);
//        return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
		APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
		Map<String, String> params = new HashMap<String,String>();
		params.put("flag", flag);
		params.put("offset", String.valueOf(offset));
		params.put("pageSize", String.valueOf(pageSize));
		params.put("field", field);
		event.setQueryStringParameters(params);
		return handler.handleRequest(event, null);
    }
	
	@GetMapping("/searchComp")
	public ResponseEntity<List<Company>> searchEmployee(@RequestParam("query") String query){
	return ResponseEntity.ok(service.searchCompany(query));
	}
	
//	@RequestMapping("/")
//    public APIResponse<List<Company>> viewHomePage( @Param("keyword") String keyword) {
//        List<Company> company = service.listAll(keyword);
//        return new APIResponse<>(company.size(), company);
//    }
}
