package nwt.jtlserver.com.controllers;

import java.io.StringReader;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

@Service
@PropertySource("config/application.properties")
public class MongoDBController {

	private static final Logger LOG = LoggerFactory.getLogger(MongoDBController.class);
	
	private MongoClient mongo;
	private DB db;
	
	@Value("${mongodb.host}")
	private String host;
	
	@Value("${mongodb.port}")
	private int port;
	
	@Value("${mongodb.db}")
	private String dbName;
	
	public MongoDBController(){
	}
	
	@PostConstruct
	public void init(){
		try {
			mongo = new MongoClient(host, port);
			db = mongo.getDB("spade");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/*-----------------------------------------------------------
	 * Performance Results Methods ------------------------------
	 * ----------------------------------------------------------
	 * */
	
	
	public JsonArray addPerfResults(String perfResults){
		DBCollection coll = db.getCollection("performance");
		BasicDBObject newPerfResults = (BasicDBObject) JSON.parse(perfResults);
		newPerfResults.append("_id", newPerfResults.getString("name"));
		
		BasicDBObject query = new BasicDBObject();
		query.append("name", newPerfResults.getString("name"));
		
		BasicDBObject removeId = new BasicDBObject("_id", 0);
		DBCursor cursor = coll.find(query, removeId);
		
		JsonObjectBuilder objBuild = Json.createObjectBuilder();
		JsonArrayBuilder arrBuild = Json.createArrayBuilder();
		JsonObject json = objBuild.build();
		
		if(cursor.count() > 0){
			LOG.info("Performance Results Found: ");
			BasicDBObject found = (BasicDBObject) cursor.next();
			json = Json.createReader(new StringReader(found.toString())).readObject();
			arrBuild.add(json);
		} else{
			LOG.info("New Performance Results Created: " + coll.insert(newPerfResults));
			json = Json.createReader(new StringReader(newPerfResults.toString())).readObject();
			arrBuild.add(json);
		}
		
		LOG.info("----------------------------------- ARR BUILD -------------------------------------------------\n"
				+ arrBuild.build().toString());
		
		return arrBuild.build();
	}
	
	public JsonArray updatePerfResults(String perfResults){
		BasicDBObject doc = (BasicDBObject) JSON.parse(perfResults);
		doc.append("_id", doc.get("name"));
		
		DBCollection coll = db.getCollection("performance");
		BasicDBObject query = new BasicDBObject();
		
		query.put("_id", doc.get("name"));
		
		DBCursor cursor = coll.find(query);
		
		JsonObjectBuilder objBuild = Json.createObjectBuilder();
		JsonArrayBuilder arrBuild = Json.createArrayBuilder();
		JsonObject json = objBuild.build();
		
		if(cursor.count() > 0){
			LOG.info("Performance Results Found and Updated: " + coll.update(query, doc, true, false));
			json = Json.createReader(new StringReader(perfResults)).readObject();
			return arrBuild.add(json).build();
		} else {
			LOG.info("Performance Results Created: " + coll.insert(doc));
			json = Json.createReader(new StringReader(perfResults)).readObject();
			return arrBuild.add(json).build();
		}
		
	}
	
	public JsonArray getPerfResults(String perfResults){
		DBCollection coll = db.getCollection("performance");
		BasicDBObject query = new BasicDBObject();
		BasicDBObject removeId = new BasicDBObject("_id", 0);
		
		query.put("name", perfResults);
		
		DBCursor cursor = coll.find(query, removeId);
		JsonObjectBuilder objBuild = Json.createObjectBuilder();
		JsonArrayBuilder arrBuild = Json.createArrayBuilder();
		JsonObject json = objBuild.build();
		
		while(cursor.hasNext()){
			BasicDBObject found = (BasicDBObject) cursor.next();
			LOG.info("Found Performance Results: " + found.toString());
			json = Json.createReader(new StringReader(found.toString())).readObject();
			
			arrBuild.add(json);
		}
		
		LOG.info("----------------------------------- ARR BUILD -------------------------------------------------\n"
				+ arrBuild.build().toString());
		
		return arrBuild.build();
	}
}
