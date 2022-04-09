package reseau.project.status.centralBusinesses;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import reseau.project.status.businesses.Business;

import java.util.List;

@Repository
public interface CentralBusinessRepository extends CassandraRepository<CentralBusiness, String> {
}
