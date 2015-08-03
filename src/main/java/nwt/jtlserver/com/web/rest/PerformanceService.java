package nwt.jtlserver.com.web.rest;

import nwt.jtlserver.com.controllers.APIController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Service
@RestController
@RequestMapping("/perf/api")
public class PerformanceService {

	private APIController apiController;
	private static final Logger LOG = LoggerFactory.getLogger(PerformanceService.class);
	
	@Autowired
	public PerformanceService(APIController api){
		apiController = api;
	}
	
	@RequestMapping(value = "/project_perf_results/{project}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> getPerformanceResults (@PathVariable String project){
		
		return new ResponseEntity<String>(apiController.getPerformanceResults(project), HttpStatus.OK);
	}
}
