package com.ntt.clients.infrastructure.in.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ntt.clients.application.port.in.ClientCreateUseCase;
import com.ntt.clients.application.port.in.ClientSearchUseCase;
import com.ntt.clients.domain.exception.EntityNotFoundException;
import com.ntt.clients.domain.model.Client;
import com.ntt.clients.infrastructure.in.rest.mapper.ClientRestMapper;
import com.ntt.clients.infrastructure.in.rest.model.ClientResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClientRestAdapter.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ClientRestAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientSearchUseCase clientSearchUseCase;

  @MockitoBean
  private ClientCreateUseCase clientCreateUseCase;

    @MockitoBean
    private ClientRestMapper clientRestMapper;

    private final Client client = Client.builder()
        .id(1L)
        .name("Victor")
        .gender("Masculino")
        .age(20)
        .idNumber("1725082786")
        .address("Call Segovia y Raices")
        .phone("0984565509")
        .password("victorContrasena")
        .status(true)
        .build();

    private final ClientResponse clientResponse = ClientResponse.builder()
        .name("Victor")
        .gender("Masculino")
        .age(20)
        .idNumber("1725082786")
        .address("Call Segovia y Raices")
        .phone("0984565509")
        .password("victorContrasena")
        .status(true)
        .build();

    @Test
    public void clientControllerTest_getAllClientsReturnsListOfClients()
        throws Exception {
        when(clientSearchUseCase.findAll()).thenReturn(List.of(client));
        when(clientRestMapper.toClientResponseList(any())).thenReturn(
            List.of(clientResponse)
        );
        mockMvc
            .perform(get("/clients"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").value(clientResponse));

        verify(clientSearchUseCase, times(1)).findAll();
    }

    @Test
    public void clientControllerTest_getStatusAccountReturnsList()
        throws Exception {
        when(
            clientSearchUseCase.requestStatusAccount(any(), any(), any())
        ).thenReturn(List.of());
        mockMvc
            .perform(
                get("/clients/reportes")
                    .param("fechaInicio", "2021-01-01")
                    .param("fechaFin", "2021-01-01")
                    .param("idNumber", "1725082786")
            )
            .andExpect(status().isOk());
        verify(clientSearchUseCase, times(1)).requestStatusAccount(
            any(),
            any(),
            any()
        );
    }

    @Test
    public void clientControllerTest_getStatusAccountReturnsBadRequest()
        throws Exception {
        when(clientSearchUseCase.requestStatusAccount(any(), any(), any())).thenThrow(
            new EntityNotFoundException("ERROR: Cliente no encontrado")
        );
        mockMvc
            .perform(
                get("/clients/reportes")
                    .param("fechaInicio", "2021-01-01")
                    .param("fechaFin", "2021-01-01")
                    .param("idNumber", "1725082786")
            )
            .andExpect(status().isBadRequest());
        verify(clientSearchUseCase, times(1)).requestStatusAccount(
            any(),
            any(),
            any()
        );
    }

    @Test
    public void clientControllerTest_saveClientReturnsOk() throws Exception {
        doNothing().when(clientCreateUseCase).save(any());
        when(clientRestMapper.toClient(any())).thenReturn(client);
        mockMvc
            .perform(
                post("/clients")
                    .content(
                        "{\"name\":\"Victor Jimenez\"," +
                            "\"gender\":\"masculino\"," +
                            "\"age\":30," +
                            "\"idNumber\":\"1725082786\"," +
                            "\"address\":\"Amazonas y NNUU\"," +
                            "\"phone\":\"0984565509\"," +
                            "\"password\":\"ntttest\"}"
                    )
                    .contentType("application/json")
            )
            .andExpect(status().isCreated())
            .andExpect(
                jsonPath("$.status").value("Client guardado exitosamente")
            );
        verify(clientCreateUseCase, times(1)).save(any());
    }

    @Test
    public void clientControllerTest_saveClientReturnsBadRequest()
        throws Exception {
        doThrow(new EntityNotFoundException("ERROR: Client ya existe"))
            .when(clientCreateUseCase)
            .save(any());
        mockMvc
            .perform(
                post("/clients")
                    .content(
                        "{\"name\":\"Victor Jimenez\"," +
                            "\"gender\":\"masculino\"," +
                            "\"age\":30," +
                            "\"idNumber\":\"1725082786\"," +
                            "\"address\":\"Amazonas y NNUU\"," +
                            "\"phone\":\"0984565509\"," +
                            "\"password\":\"ntttest\"}"
                    )
                    .contentType("application/json")
            )
            .andExpect(status().isBadRequest());
        verify(clientCreateUseCase, times(1)).save(any());
    }

    @Test
    public void clientControllerTest_updateClientReturnsOk()
        throws Exception {
        doNothing().when(clientCreateUseCase).update(any());
        mockMvc
            .perform(
                put("/clients")
                    .content(
                        "{\"name\":\"Victor Jimenez\"," +
                            "\"gender\":\"masculino\"," +
                            "\"age\":30," +
                            "\"idNumber\":\"1725082786\"," +
                            "\"address\":\"Amazonas y NNUU\"," +
                            "\"phone\":\"0984565509\"," +
                            "\"password\":\"ntttest\"}"
                    )
                    .contentType("application/json")
            )
            .andExpect(status().isAccepted())
            .andExpect(
                jsonPath("$.status").value("Client actualizado exitosamente")
            );
        verify(clientCreateUseCase, times(1)).update(any());
    }

    @Test
    public void clientControllerTest_updateClientReturnsBadRequest()
        throws Exception {
        doThrow(new EntityNotFoundException("ERROR: Client no encontrado"))
            .when(clientCreateUseCase)
            .update(any());
        mockMvc
            .perform(
                put("/clients")
                    .content(
                        "{\"name\":\"Victor Jimenez\"," +
                            "\"gender\":\"masculino\"," +
                            "\"age\":30," +
                            "\"idNumber\":\"1725082786\"," +
                            "\"address\":\"Amazonas y NNUU\"," +
                            "\"phone\":\"0984565509\"," +
                            "\"password\":\"ntttest\"}"
                    )
                    .contentType("application/json")
            )
            .andExpect(status().isBadRequest());
        verify(clientCreateUseCase, times(1)).update(any());
    }

    @Test
    public void clientControllerTest_deleteClientReturnsOk()
        throws Exception {
        doNothing().when(clientCreateUseCase).delete(any());
        mockMvc
            .perform(delete("/clients/1725082786"))
            .andExpect(status().isAccepted())
            .andExpect(
                jsonPath("$.status").value("Client eliminado exitosamente")
            );
        verify(clientCreateUseCase, times(1)).delete(any());
    }

    @Test
    public void clientControllerTest_deleteClientReturnsBadRequest()
        throws Exception {
        doThrow(new EntityNotFoundException("ERROR: Client no encontrado"))
            .when(clientCreateUseCase)
            .delete(any());
        mockMvc
            .perform(delete("/clients/1725082786"))
            .andExpect(status().isBadRequest());
        verify(clientCreateUseCase, times(1)).delete(any());
    }
}
