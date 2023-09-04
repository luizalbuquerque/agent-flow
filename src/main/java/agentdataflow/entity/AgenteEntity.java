package agentdataflow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class AgenteEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String codigo;
        private String data;

        @OneToMany(cascade = CascadeType.ALL)
        private List<RegiaoEntity> regioes;
}