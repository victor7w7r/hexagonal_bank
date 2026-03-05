package com.ntt.clients.infrastructure.out.messaging.mapper;

import com.ntt.clients.infrastructure.out.messaging.entity.StatusAccountSendReq;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface ClientMessagingOutputMapper {
  StatusAccountSendReq toStatusAccountSendReq(
          LocalDate startDate,
          LocalDate endDate,
          Long clientRef,
          String clientName
  );
}
