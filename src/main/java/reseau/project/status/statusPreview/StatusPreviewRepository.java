package reseau.project.status.statusPreview;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPreviewRepository extends CassandraRepository<StatusPreview, Integer> {
}
