package core.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import core.model.Department;

@Service
public interface DepartmentRepository extends MongoRepository<Department, ObjectId> {
	Optional<Department> findByDepartmentID(String departmentID);
}
