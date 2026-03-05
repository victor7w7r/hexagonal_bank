package com.ntt.clients.infrastructure.in.rest.mapper;

import com.ntt.clients.domain.model.Client;
import com.ntt.clients.domain.model.StatusAccountReceive;
import com.ntt.clients.infrastructure.in.rest.model.ClientRequest;
import com.ntt.clients.infrastructure.in.rest.model.ClientResponse;
import com.ntt.clients.infrastructure.in.rest.model.StatusAccountReceiveRes;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClientRestMapper {
    Client toClient(ClientRequest clientRequest);
    ClientResponse toClientResponse(Client client);
    List<ClientResponse> toClientResponseList(List<Client> clientList);
    StatusAccountReceiveRes toStatusAccountReceiveRes(
        StatusAccountReceive statusAccountReceive
    );
    List<StatusAccountReceiveRes> toStatusAccountReceiveResList(
        List<StatusAccountReceive> statusAccountReceiveList
    );
}
