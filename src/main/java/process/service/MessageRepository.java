package process.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import process.domain.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {
}
