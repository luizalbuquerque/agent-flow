package agentdataflow.repositoty;

import agentdataflow.entity.AgenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenteRepository extends JpaRepository<AgenteEntity, Long> {
}

