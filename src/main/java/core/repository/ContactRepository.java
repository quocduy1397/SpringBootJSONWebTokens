package core.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import core.model.Contact;

@Service
public interface ContactRepository extends MongoRepository<Contact, ObjectId> {
	List<Contact> findByPosition(String position);
	
	@Query(value = "{ ?0 : ?1 }", fields = "{ 'contactID' : 1, 'position' : 1 }")
	List<Contact> findByPositionAndReturnSelectedFields(String key ,String position);
}
