package reseau.project.status.catalogs;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import reseau.project.status.businesses.Business;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CatalogRepository extends CassandraRepository<Catalog, UUID> {
    Optional<Catalog> findByBusinessIdAndCatalogId(UUID businessId, UUID catalogId);
    List<Catalog> findAllByBusinessId(UUID businessId);

    List<Catalog> findByBusinessId(UUID businessId);

    void deleteByBusinessIdAndCatalogId(UUID fromString, UUID fromString1);
}
