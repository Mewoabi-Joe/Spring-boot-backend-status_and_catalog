package reseau.project.status.items;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends CassandraRepository<Item, UUID> {
    Optional<Item> findByCatalogIdAndItemId(UUID catalogId, UUID itemId);
    List<Item> findAllByCatalogId(UUID categoryId);
    List<Item> findByCatalogId(UUID categoryId);
    void deleteByCatalogIdAndItemId(UUID fromString, UUID fromString1);
    void deleteByCatalogId(UUID fromString);

}
