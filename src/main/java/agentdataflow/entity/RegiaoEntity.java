package agentdataflow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class RegiaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sigla;

    @ElementCollection // para armazenar múltiplos valores de geracao, compra, e precoMedio em uma coleção
    private List<Double> geracao;

    @ElementCollection
    private List<Double> compra;

    @ElementCollection
    private List<Double> precoMedio;
}
