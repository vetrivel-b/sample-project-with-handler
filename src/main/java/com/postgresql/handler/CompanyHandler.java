package com.postgresql.handler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.postgresql.dto.CompanyDto;
import com.postgresql.model.Company;
import com.postgresql.service.CompanyService;
import com.google.gson.Gson;

@Component
public class CompanyHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Autowired
	public CompanyService companyService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyHandler.class);



	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		Map<String, String> param = input.getQueryStringParameters();
		param.get("flag");
		System.out.println("check");
		if (param.get("flag").equals("viewList")) {
			logger.info("METHOD: handleRequest(companyService.viewList()) Initiated");
			return apiGatewayResponse(companyService.viewList(), 200, new APIGatewayProxyResponseEvent());
		}
		else if(param.get("flag").equals("viewById")) {
			Company response=companyService.viewById(Long.parseLong(param.get("companyId")));
			return apiGatewayResponse(response,200,new APIGatewayProxyResponseEvent());
			}
		else if(param.get("flag").equals("newCompany")) {
			String body = input.getBody();
			CompanyDto companyDto = new Gson().fromJson(body, CompanyDto.class);
			Company response = companyService.newCompany(companyDto);
			return apiGatewayResponse(response, 200, new APIGatewayProxyResponseEvent());
		}
		else if(param.get("flag").equals("findCompanysWithSorting")) {
			List<Company> response = companyService.findCompanysWithSorting(String.valueOf(param.get("field")));
			return apiGatewayResponse(response, 200, new APIGatewayProxyResponseEvent());
		}
		else if(param.get("flag").equals("findCompanyWithPagination")) {
			Page<Company> response = companyService.findCompanyWithPagination(Integer.parseInt(param.get("offset")), Integer.parseInt(param.get("pageSize")));
			return apiGatewayResponse(response, 200, new APIGatewayProxyResponseEvent());
		}
		else if(param.get("flag").equals("findCompanysWithPaginationAndSorting")) {
			Page<Company> response = companyService.findCompanysWithPaginationAndSorting(Integer.parseInt(param.get("offset")),Integer.parseInt(param.get("pageSize")),String.valueOf(param.get("field")));
			return apiGatewayResponse(response, 200, new APIGatewayProxyResponseEvent());
		}
		else if(param.get("flag").equals("UpdateById")) {
			String body=input.getBody();
			CompanyDto companyDto=new Gson().fromJson(body, CompanyDto.class);
			Company response=companyService.UpdateById(Long.parseLong(param.get("companyId")),companyDto);
			return apiGatewayResponse(response, 200, new APIGatewayProxyResponseEvent());
		}
		else if(param.get("flag").equals("UpdateUsingPatch")) {
			String body=input.getBody();
			Company response=companyService.UpdateUsingPatch(Long.parseLong(param.get("companyId")), param.get("location"));
			return apiGatewayResponse(response, 200, new APIGatewayProxyResponseEvent());
		}
		else if(param.get("flag").equals("deleteById")) {
			
			ResponseEntity<Company> response=companyService.deleteById(Long.parseLong(param.get("companyId")));
			return apiGatewayResponse(response, 200, new APIGatewayProxyResponseEvent());
		}
		logger.error("UNEXPECTED FLAG VALUE ERROR");
		return apiGatewayResponse("UNEXPECTEDEXCEPTION", 500, new APIGatewayProxyResponseEvent());
	}

	public APIGatewayProxyResponseEvent apiGatewayResponse(Object body, int statusCode,
			APIGatewayProxyResponseEvent responseEvent) {
			responseEvent.setIsBase64Encoded(false);
			if (body != null)
			responseEvent.setBody(new Gson().toJson(body));
			responseEvent.setStatusCode(statusCode);
			return responseEvent;
	}
}
