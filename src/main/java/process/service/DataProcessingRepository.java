package process.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import process.domain.Message;

/**
 * Main in-memory repository for CURD operations.
 */
@Repository
public interface DataProcessingRepository extends CrudRepository<Message, Long> {
}
