package nwt.jtlserver.com.service;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.xml.XmlMapper;

import javax.json.JsonObject;
import javax.xml.parsers.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;

@Service
public class JTLFileService {

	private final Logger log = LoggerFactory.getLogger(JTLFileService.class);
	private static ArrayList<File> foundFiles = new ArrayList<File>();
	
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    
    
	public JTLFileService(){
		
	}
	
	public void jtlSearch(File file) throws JsonParseException, JsonMappingException, IOException{
		
	    
		if(file.isDirectory()){
			if(file.canRead()){
				for(File temp: file.listFiles()){
					if(temp.isDirectory()){
						jtlSearch(temp);
					} else {
						if(FilenameUtils.getExtension(temp.getName()).equals("jtl")){
							log.info(temp.getAbsolutePath());
							convertToJson(temp);
							foundFiles.add(temp);
						}
					}
				}
			}
		}
	}
	
	public void convertToJson(File jtlFile) throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		List entries = xmlMapper.readValue(jtlFile, List.class);
		
		List<String> lines = Files.readAllLines(jtlFile.toPath(), Charset.defaultCharset());
		log.debug(entries.toString());
		log.debug(lines.toString());
		
		ObjectMapper jsonMapper = new ObjectMapper();
		String json = jsonMapper.writeValueAsString(entries);
		
		log.info(json);
	}
}
