package andrew.samardak.spring_project_second.service;

import andrew.samardak.spring_project_second.dto.request.TransactionAcceptRequestDto;

public interface TransactionService {

    void processTransaction(TransactionAcceptRequestDto dto);
}
