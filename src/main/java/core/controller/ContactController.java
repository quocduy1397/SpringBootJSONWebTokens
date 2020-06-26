package core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.model.Contact;
import core.model.Department;
import core.repository.ContactRepository;
import core.repository.DepartmentRepository;

@RestController
@RequestMapping("/contact")
public class ContactController {

	@Autowired
	private ContactRepository contactService;
	
	@Autowired
	private DepartmentRepository departmentService;
	
	@GetMapping(value = "/all/")
	public List<Contact> getAll()
	{
		return contactService.findAll();
	}
	
	@GetMapping(value = "/page/")
	public List<Contact> findAllWithPage(@RequestBody HashMap<String, Object> fields)
	{
		int page = 0;
		int size = 1;
		try 
		{
			page = (int) fields.get("page");
			size = (int) fields.get("size");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		Page<Contact> pageResults = contactService.findAll(PageRequest.of(page, size));
		return pageResults.toList();
	}
	
	@GetMapping(value = "/skip-limit/")
	public List<Contact> findAllWithSkipAndLimit(@RequestBody HashMap<String, Object> fields)
	{
		int page = 0;
		int size = 1;
		try 
		{
			//pass record of start index, get record from (start + 1) index to end index
			size = (int) fields.get("end") - (int) fields.get("start");
			page = ((int) fields.get("start"))/size;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		Page<Contact> pageResults = contactService.findAll(PageRequest.of(page, size));
		return pageResults.toList();
	}
	
	@GetMapping(value = "/position/")
	public List<Contact> findByPosition(@RequestBody HashMap<String, Object> fields)
	{
		int page = 0;
		int size = 1;
		try 
		{
			//pass record of start index, get record from (start + 1) index to end index
			size = (int) fields.get("end") - (int) fields.get("start");
			page = ((int) fields.get("start"))/size;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String position = fields.get("position") == null ? "" : fields.get("position").toString();
		
		Document conditions = new Document();
		List<Document> listConditions = new ArrayList<>();
		listConditions.add(new Document("position", position));
		listConditions.add(new Document("contactID", "CT00672"));
		conditions.append("$and", listConditions);
		
		Document selectedFields = new Document();
		selectedFields.append("position", true).append("contactID", true)
		.append("department", true).append("departmentDBRef", true)
		.append("departmentDBRefOther", true);
		List<Contact> list = contactService.findByConditionAndReturnSelectedFields(conditions, selectedFields);
		Page<Contact> pageResults = new PageImpl<Contact>(list, PageRequest.of(page, size), list.size());
		return pageResults.toList();
	}
	
	@PostMapping(value = "/add/")
	public Contact addContact()
	{
		Department department = departmentService.findByDepartmentID("DP055").orElse(null);
		Contact contact = new Contact();
		contact.setContactID("CT10000000");
		contact.setDepartmentDBRefOther(department);
		return contactService.save(contact);
	}
}
