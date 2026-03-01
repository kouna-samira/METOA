package com.groupe2.METOA.Controller;

import com.groupe2.METOA.Dto.ClientReqDto;
import com.groupe2.METOA.Dto.ClientResDto;
import com.groupe2.METOA.Service.Client.ClientService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    //pagination

    @GetMapping
    public Page<ClientResDto> getAllClients(@ParameterObject Pageable pageable) {
        return clientService.getClients(pageable);
    }

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    // Ajouter un client
    @PostMapping(path = "add")
    public ResponseEntity<String> addClient(@Valid  @RequestBody ClientReqDto clientReqDto) {
        clientService.addClient(clientReqDto);
        return ResponseEntity.status(201).body("Client ajouté avec succès !");
    }

    // Récupérer un client par ID
    @GetMapping("/get_client_by_id/{idclient}")
    public ResponseEntity<ClientResDto> getClientById(@PathVariable String idclient) {
        ClientResDto client = clientService.getClientById(idclient);
        return ResponseEntity.status(200).body(this.clientService.getClientById(idclient));
    }

    // Récupérer tous les clients
    @GetMapping(path = "/get_all_client")
    public ResponseEntity< List<ClientResDto>> getAllClients() {
        List<ClientResDto> clients = clientService.getAllClient();
        return ResponseEntity.ok(clientService.getAllClient());
    }

    // Mettre à jour un client
    @PatchMapping("/update_by_id/{idclient}")
    public ResponseEntity<String> updateClient(@PathVariable String idclient, @RequestBody ClientReqDto clientReqDto) {
        clientService.updateClient(idclient, clientReqDto);
        return ResponseEntity.status(202).body("client ajouter avec succes");
    }

    // Supprimer un client
    @DeleteMapping("/delette_client/{idclient}")
    public ResponseEntity<String> deleteClient(@PathVariable String idclient) {
        clientService.deleteClient(idclient);
        return ResponseEntity.status(202).body("Client supprimé avec succès !");
    }
}
