package cl.bree2003.AlkeWalletM6.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
@Immutable
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @Enumerated(EnumType.STRING)
    private TransactionEnum type;
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double total;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    @JsonBackReference
    private UserEntity user;
    private String emailReceiver;
}
