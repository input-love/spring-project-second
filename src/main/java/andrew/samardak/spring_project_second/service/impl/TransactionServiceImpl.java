package andrew.samardak.spring_project_second.service.impl;

import andrew.samardak.spring_project_second.dto.request.TransactionAcceptRequestDto;
import andrew.samardak.spring_project_second.dto.response.TransactionResultResponseDto;
import andrew.samardak.spring_project_second.entity.Transaction;
import andrew.samardak.spring_project_second.kafka.producer.KafkaProducerService;
import andrew.samardak.spring_project_second.repository.TransactionRepository;
import andrew.samardak.spring_project_second.service.TransactionService;
import andrew.samardak.spring_project_second.utils.constants.KafkaHeaderConstants;
import andrew.samardak.spring_project_second.utils.enums.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Value("${spring-project.kafka.topics.producer.transaction-result}")
    private String topic;

    @Value("${spring-project.kafka.transaction.max-count}")
    private Integer maxCount;

    @Value("${spring-project.kafka.transaction.time-window-min}")
    private Integer timeWindowMin;

    private final TransactionRepository transactionRepository;

    private final KafkaProducerService<TransactionResultResponseDto> kafkaProducerService;

    @Override
    public void processTransaction(TransactionAcceptRequestDto dto) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMinutes(timeWindowMin);

        List<Transaction> transactions = transactionRepository.findByTransactionTimeBetween(startTime, endTime);

        if (transactions.size() >= maxCount) {
            for (Transaction transaction : transactions) {
                sendBlockedTransactions(transaction);
            }
        }


    }

    private void sendBlockedTransactions(Transaction transaction) {
        kafkaProducerService.sendMessage(
                topic, buildBody(transaction), buildHeader()
        );
    }

    private TransactionResultResponseDto buildBody(Transaction transaction) {
        return new TransactionResultResponseDto(
                transaction.getAccount().getId(),
                transaction.getId(),
                TransactionStatus.BLOCKED
        );
    }

    private Map<String, String> buildHeader() {
        return Map.of(
                KafkaHeaderConstants.ACCEPT_TYPE_HEADER, KafkaHeaderConstants.ERROR_TIMESTAMP_VALUE
        );
    }
}
