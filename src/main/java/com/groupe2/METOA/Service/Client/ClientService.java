package com.groupe2.METOA.Service.Client;

import com.groupe2.METOA.Dto.ClientResDto;

import java.util.List;

public interface ClientService {
    void addClient(ClientResDto clientResDto);
    ClientResDto getClientById(String idclient);
    List<ClientResDto>getAllClient();
    void updateClient(String idclient,ClientResDto clientResDto);
    void deleteClient(String idclient);
}
