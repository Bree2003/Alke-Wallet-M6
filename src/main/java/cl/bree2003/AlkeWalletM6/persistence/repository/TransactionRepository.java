package cl.bree2003.AlkeWalletM6.persistence.repository;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
