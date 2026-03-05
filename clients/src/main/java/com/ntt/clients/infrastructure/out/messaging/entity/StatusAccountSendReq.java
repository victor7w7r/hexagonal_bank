package com.ntt.clients.infrastructure.out.messaging.entity;

import java.time.LocalDate;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountSendReq {

    private LocalDate startDate;
    private LocalDate endDate;
    private Long clientRef;
    private String clientName;
}
