package reseau.project.status.carts;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface CartRepository extends CassandraRepository<Cart, CartKey> {
    Optional<Cart> findByKey_UserNumberAndKey_OwnerNumberAndKey_categoryId(int userNumber, int ownerNumber, UUID categoryId);

    Optional<Cart> findByKey_UserNumberAndKey_OwnerNumberAndKey_categoryIdAndKey_ItemId(int userNumber, int ownerNumber, UUID categoryId, UUID itemId);
}
