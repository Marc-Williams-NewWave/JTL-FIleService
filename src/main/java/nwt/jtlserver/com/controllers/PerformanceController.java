package nwt.jtlserver.com.controllers;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offbytwo.jenkins.JenkinsServer;

@Service
public class PerformanceController {

	private MongoDBController db;
	private JenkinsServer jenkins;
	
	private static final Logger LOG = LoggerFactory.getLogger(PerformanceController.class);
	
	@Autowired
	public PerformanceController(MongoDBController db){
		this.db = db;
		try{
			jenkins = new JenkinsServer(new URI("http://192.168.1.10:8080"), "nwt-builder", "NWT91life");
		} catch (URISyntaxException e){
			e.printStackTrace();
		}
	}
	
//	@PostConstruct
//	public void init(){
//		TimerTask updateTask = new UpdateStatus(this);
//		Timer timer = new Timer(true);
//		LOG.info("Setting TimerTask in PerformanceController");
//		timer.scheduleAtFixedRate(updateTask, 15 * 1000, 3 * 1000);
//	}
	
	public JsonArray addPerfResults(String payload){
		return db.updatePerfResults(payload);
	}
	
	public JsonObject getPerfResults(String payload){
		JsonObjectBuilder objBuild = Json.createObjectBuilder();
		objBuild.add("api", "v0.0.4");
		objBuild.add("time", new Date().getTime());
		objBuild.add("type", "GetPerformanceResults");
		objBuild.add("items", db.getPerfResults(payload));
		
		return objBuild.build();
	}
	
	public JsonObject updatePerfResult(String paylod){
		JsonObjectBuilder objBuild = Json.createObjectBuilder();
		objBuild.add("api", "v0.0.4");
		objBuild.add("time", new Date().getTime());
		objBuild.add("type", "UpdatePerformanceResults");
		objBuild.add("items", db.updatePerfResults(paylod));
		
		return objBuild.build();
	}
	
	
	
//	public static class UpdateStatus extends TimerTask {
//		private PerformanceController perfController;
//		
//		public UpdateStatus(PerformanceController perfController){
//			super();
//			this.perfController = perfController;
//		}
//		
//		@Override
//		public void run(){
//			try{
//				perfController.updatePerfResults();
//			} catch (Exception e){
//				e.printStackTrace();
//			}
//		}
//	}
}
