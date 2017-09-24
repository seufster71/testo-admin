package org.testo.admin.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.testo.core.service.MicroServiceClient;
import org.testo.core.utils.GlobalConstant;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;


@Component("MicroServiceClient")
public class MicroServiceClientImpl implements MicroServiceClient {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected RestTemplateBuilder restTemplateBuilder;
	
	@Autowired
	protected EurekaClient client;
	
	@Override
	public void process(Request request, Response response) {
		// use remote service
		RestTemplate restTemplate = restTemplateBuilder.build();
		InstanceInfo instanceInfo = client.getNextServerFromEureka((String) request.getParams().get(GlobalConstant.MICROSERVICENAME), false);
		String baseUrl = instanceInfo.getHomePageUrl();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Request> entity = new HttpEntity<>(request,headers);
		StringBuilder url = new StringBuilder();
		url.append(baseUrl);
		url.append((String) request.getParams().get(GlobalConstant.MICROSERVICEPATH));
		logger.info("call url " + url.toString());
		ResponseEntity<Response> result = restTemplate.exchange(url.toString(), HttpMethod.POST, entity, Response.class);
		
		response.setParams(((Response) result.getBody()).getParams());
	}

}
