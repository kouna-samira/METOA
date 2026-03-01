package com.groupe2.METOA.Service.Client;

import com.groupe2.METOA.Dto.ClientReqDto;
import com.groupe2.METOA.Dto.ClientResDto;
import com.groupe2.METOA.Entity.Client;
import com.groupe2.METOA.exception.EmailAlreadyExistsException;
import com.groupe2.METOA.repository.ClientRepo;
import com.groupe2.METOA.exception.RessourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceimpl implements ClientService{
    private final ClientRepo clientRepo;

    public ClientServiceimpl(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }




    @Override
    public void addClient(ClientReqDto clientReqDto) {
       
        Client client = new Client();
        client.setNom(clientReqDto.getNom());
        client.setPrenom(clientReqDto.getPrenom());
        client.setEmail(clientReqDto.getEmail());
        client.setTelephone(clientReqDto.getTelephone());

        clientRepo.save(client);

    }

    @Override
    public ClientResDto getClientById(String idclient) {
        Client client = clientRepo.findById(idclient)
                .orElseThrow(() -> new RessourceNotFoundException("Client not found"));

        return new ClientResDto(
                client.getIdclient(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone()
        );
    }


    @Override
    public List<ClientResDto> getAllClient() {
        return clientRepo.findAll()
                .stream()
                .map(client -> new ClientResDto(
                        client.getIdclient(),
                        client.getNom(),
                        client.getPrenom(),
                        client.getEmail(),
                        client.getTelephone()
                ))
                .collect(Collectors.toList());
    }


    @Override
    public void updateClient(String idclient, ClientReqDto clientReqDto) {
        Client client = clientRepo.findById(idclient)
                .orElseThrow(() -> new RessourceNotFoundException("Client not found"));

        client.setNom(clientReqDto.getNom());
        client.setPrenom(clientReqDto.getPrenom());
        client.setEmail(clientReqDto.getEmail());
        client.setTelephone(clientReqDto.getTelephone());

        clientRepo.save(client);
    }

    // Supprimer un client
    @Override
    public void deleteClient(String idclient) {
        if (!clientRepo.existsById(idclient)) {
            throw new RessourceNotFoundException("Client not found");
        }

        clientRepo.deleteById(idclient);
    }

    @Override
    public Page<ClientResDto> getClients(Pageable pageable) {
        Page<Client> clients = clientRepo.findAll(pageable);
        return clients.map(client -> new ClientResDto(
                client.getIdclient(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone()
        ));


    }



}
