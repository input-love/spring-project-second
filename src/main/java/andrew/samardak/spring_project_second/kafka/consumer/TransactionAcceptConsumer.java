package andrew.samardak.spring_project_second.kafka.consumer;

import andrew.samardak.spring_project_second.dto.request.TransactionAcceptRequestDto;
import andrew.samardak.spring_project_second.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionAcceptConsumer {

    private final TransactionService transactionService;

    @KafkaListener(
            id = "${spring-project.kafka.topics.consumer.transactions-accept.id}",
            topics = "${spring-project.kafka.topics.consumer.transactions-accept.name}",
            containerFactory = "kafkaTransactionAcceptListenerContainerFactory"
    )
    public void listener(
            @Payload TransactionAcceptRequestDto dto
    ) {
        transactionService.processTransaction(dto);
    }
}
