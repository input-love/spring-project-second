package andrew.samardak.spring_project_second.dto.response;

import andrew.samardak.spring_project_second.utils.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResultResponseDto {

    private Long accountId;

    private Long transactionId;

    private TransactionStatus transactionStatus;
}
