package core.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ContactsList")
public class Contact {
	@Id
	private ObjectId _id;
	
	@NotBlank(message = "ContactID is must not empty or null")
	@Pattern(regexp = "^CT", message = "Invalid ContactID")
	private String contactID;
	private String creator;
	
	@JsonFormat(pattern="dd/MM/yyyy")
    private Date dateCreate;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dateModify;
	
    private String contactName;
    private String englishName;
    private String address;
    
    @JsonFormat(pattern="dd/MM/yyyy")
	private Date birthday;
    
    @Pattern(regexp = "^$|[0-9]{10}", message = "Invalid phone number")
    private String telephone;
    private String identifyCard;
    
    @JsonFormat(pattern="dd/MM/yyyy")
	private Date joiningDate;
    private String position;
    private String group;
    private String department;
    private String knowledge;
    
    @DBRef
    private Department departmentDBRef;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinProperty(name="departmentDBRefOther")
    private Department departmentDBRefOther;
    
    @Email(message = "Invalid email")
    private String email;
    
    private String username;
	private String password;
	
	public String get_id()
	{
		return _id == null ? null : _id.toHexString();
	}
	
}
