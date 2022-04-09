package reseau.project.status.status;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import reseau.project.status.contacts.Contact;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends CassandraRepository<Status, Integer> {
    Optional<Status> findByUserNumberAndPostTime(int userNumber, LocalDateTime postTime);
    List<Status> findAllByUserNumber(int userNumber);

}
