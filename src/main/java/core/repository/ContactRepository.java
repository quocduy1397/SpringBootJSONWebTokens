package core.repository;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import core.model.Contact;

@Service
public interface ContactRepository extends MongoRepository<Contact, ObjectId> {
	List<Contact> findByPosition(String position);
	
	@Query(value = "{ 'contactID' : ?0 }")
	List<Contact> findByContactID(String contactID);
	
	@Query(value = "?0", fields = "?1")
	List<Contact> findByConditionAndReturnSelectedFields(Document query, Document project);
}
