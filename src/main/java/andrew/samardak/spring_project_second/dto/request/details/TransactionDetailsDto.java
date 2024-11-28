package andrew.samardak.spring_project_second.dto.request.details;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDetailsDto {

    private BigDecimal amount;
}
