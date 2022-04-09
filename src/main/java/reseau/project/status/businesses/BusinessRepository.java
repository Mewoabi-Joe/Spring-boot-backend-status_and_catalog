package reseau.project.status.businesses;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessRepository extends CassandraRepository<Business, Integer> {
    List<Business> findAllByUserNumber(int userNumber);
    Optional<Business> findByUserNumberAndBusinessId(int userNumber, UUID businessId);

    void deleteByUserNumberAndBusinessId(int userNumber, UUID businessId);
}
