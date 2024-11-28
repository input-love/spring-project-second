package andrew.samardak.spring_project_second.repository;

import andrew.samardak.spring_project_second.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByTransactionTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
