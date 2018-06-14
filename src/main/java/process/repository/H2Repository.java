package process.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import process.domain.Message;

/**
 * In-memory repository for CURD operations.
 */
@Repository
public interface H2Repository extends CrudRepository<Message, Long> {
}
