package core.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import core.model.Contact;

@Service
public interface ContactRepository extends MongoRepository<Contact, ObjectId> {
	
}
