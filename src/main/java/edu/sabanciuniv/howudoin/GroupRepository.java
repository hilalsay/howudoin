package edu.sabanciuniv.howudoin;

//import edu.sabanciuniv.howudoin.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
}
