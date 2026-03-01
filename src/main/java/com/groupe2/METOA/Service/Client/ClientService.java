package com.groupe2.METOA.Service.Client;

import com.groupe2.METOA.Dto.ClientReqDto;
import com.groupe2.METOA.Dto.ClientResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    void addClient(ClientReqDto clientReqDto);
    ClientResDto getClientById(String idclient);
    List<ClientResDto>getAllClient();
    void updateClient(String idclient, ClientReqDto clientReqDto);
    void deleteClient(String idclient);
    Page<ClientResDto> getClients(Pageable pageable);

    
}
