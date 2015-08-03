package nwt.jtlserver.com.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.util.JSON;


@Document(collection = "performance_results")
public class PerformanceResults {

	@Field("name")
	@Id
	private String name;
	
	@Field("project")
	private String project;
	
	@Field("results")
	private JSON results;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getProject() {
		return project;
	}
	
	public void setProject(String project) {
		this.project = project;
	}
	
	public JSON getResults() {
		return results;
	}
	
	public void setResults(JSON results) {
		this.results = results;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) 
			return true;
		if(obj == null) 
			return false;
		if(getClass() != obj.getClass()) 
			return false;
		
		PerformanceResults other = (PerformanceResults) obj;
		if(name == null){
			if(other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}
	}

