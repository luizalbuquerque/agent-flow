package agentdataflow.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class RegiaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sigla;

    @ManyToOne
    @JoinColumn(name = "agente_id")
    private AgenteEntity agente;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regiao")
    private List<MedicaoEntity> geracao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regiao")
    private List<MedicaoEntity> compra;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regiao")
    private List<MedicaoEntity> precoMedio;
}
