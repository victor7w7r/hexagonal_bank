package com.ntt.clients.infrastructure.out.messaging.mapper;

import com.ntt.clients.infrastructure.out.messaging.entity.StatusAccountSendReq;
import java.time.LocalDate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMessagingOutputMapper {
    StatusAccountSendReq toStatusAccountSendReq(
        LocalDate startDate,
        LocalDate endDate,
        Long clientRef,
        String clientName
    );
}
