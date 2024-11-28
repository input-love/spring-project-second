package andrew.samardak.spring_project_second.dto.request;

import andrew.samardak.spring_project_second.dto.request.details.AccountDetailsDto;
import andrew.samardak.spring_project_second.dto.request.details.TransactionDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionAcceptRequestDto {

    private Long clientId;

    private Long accountId;

    private Long transactionId;

    private LocalDateTime timestamp;

    private AccountDetailsDto account;

    private TransactionDetailsDto transaction;
}