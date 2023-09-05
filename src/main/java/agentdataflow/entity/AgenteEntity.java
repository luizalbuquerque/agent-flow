package agentdataflow.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "agente")
public class AgenteEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Integer codigo;
        private OffsetDateTime data;

        @OneToMany(cascade = CascadeType.ALL, mappedBy = "agente")
        private List<RegiaoEntity> regioes;
}