package core.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Departments")
public class Department {
	@Id
	private ObjectId _id;
	
	private String departmentID;
	private String departmentName;
    
    public String get_id()
	{
		return _id == null ? null : _id.toHexString();
	}
}
