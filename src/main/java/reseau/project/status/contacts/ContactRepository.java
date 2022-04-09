package reseau.project.status.contacts;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CassandraRepository<Contact, Integer> {
    List<Contact> findAllByUserNumber(int userNumber);
}
