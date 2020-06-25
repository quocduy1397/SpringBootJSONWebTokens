package core.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.model.Contact;
import core.repository.ContactRepository;

@RestController
@RequestMapping("/contact")
public class ContactController {

	@Autowired
	private ContactRepository service;
	
	@GetMapping(value = "/all/")
	public List<Contact> getAll()
	{
		return service.findAll();
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
		Page<Contact> pageResults = service.findAll(PageRequest.of(page, size));
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
		Page<Contact> pageResults = service.findAll(PageRequest.of(page, size));
		return pageResults.toList();
	}
	
	@GetMapping(value = "/position/")
	public List<Contact> findByGroup(@RequestBody HashMap<String, Object> fields)
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
		List<Contact> list = service.findByPositionAndReturnSelectedFields("position", position);
		Page<Contact> pageResults = new PageImpl<Contact>(list, PageRequest.of(page, size), list.size());
		return pageResults.toList();
	}
}
