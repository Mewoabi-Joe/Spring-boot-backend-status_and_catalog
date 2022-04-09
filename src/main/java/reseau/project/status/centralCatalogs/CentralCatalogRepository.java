package reseau.project.status.centralCatalogs;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CentralCatalogRepository extends CassandraRepository<CentralCatalog, String> {
}
