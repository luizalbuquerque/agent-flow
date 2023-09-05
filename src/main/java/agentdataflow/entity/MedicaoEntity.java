package agentdataflow.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "medicao")
@AllArgsConstructor
@NoArgsConstructor
public class MedicaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;

    @ManyToOne
    @JoinColumn(name = "regiao_id")
    private RegiaoEntity regiao;
}
