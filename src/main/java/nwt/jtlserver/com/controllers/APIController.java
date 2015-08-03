package nwt.jtlserver.com.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class APIController {

	protected PerformanceController perfController;
	
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private static final Logger LOG = LoggerFactory.getLogger(APIController.class);
	
	@Autowired
	public APIController(PerformanceController perfController){
		this.perfController = perfController;
	}
	
	public String addPerformanceResults(String perfResults){
		JsonObjectBuilder objBuild = Json.createObjectBuilder();
		objBuild.add("api", "v0.0.4");
		objBuild.add("time", dateFormat.format(new Date()));
		objBuild.add("type", "AddPerformanceResults");
		objBuild.add("items", perfController.addPerfResults(perfResults));
		
		return objBuild.build().toString();
	}
	
	public String getPerformanceResults(String perfResults){
		return perfController.getPerfResults(perfResults).toString();
	}
	
	public String updatePerformanceResults(String perfResults){
		return perfController.updatePerfResult(perfResults).toString();
	}
}
