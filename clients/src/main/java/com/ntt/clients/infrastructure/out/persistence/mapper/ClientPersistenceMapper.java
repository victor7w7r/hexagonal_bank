package com.ntt.clients.infrastructure.out.persistence.mapper;

import com.ntt.clients.domain.model.Client;
import com.ntt.clients.infrastructure.out.persistence.entity.ClientEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientPersistenceMapper {
  ClientEntity toClientEntity(Client client);

  Client toClient(ClientEntity entity);

  List<Client> toClientList(List<ClientEntity> entityList);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(source = "name", target = "name")
  @Mapping(source = "gender", target = "gender")
  @Mapping(source = "age", target = "age")
  @Mapping(source = "address", target = "address")
  @Mapping(source = "phone", target = "phone")
  @Mapping(source = "password", target = "password")
  @Mapping(source = "status", target = "status")
  void update(Client source, @MappingTarget ClientEntity target);
}
