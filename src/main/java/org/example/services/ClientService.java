package org.example.services;

import org.example.Exceptions.BadCredentialsException;
import org.example.Exceptions.NotFoundException;
import org.example.models.Client;
import org.example.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientService implements  AutoCloseable{

    private ClientRepository clientRepository = new ClientRepository();

    //registr
    public void register(Client newClient){

        Boolean exists = clientRepository.existsByEmail(newClient.getEmail());

        if(exists){
            throw new IllegalStateException(String.format("",newClient.getEmail()));
        }
        clientRepository.save(newClient);

//        Optional<Client> clienOptional = clientRepository.findByEmail(newClient.getEmail());
//
//        if(clienOptional.isPresent()){
//           throw new IllegalStateException();
//        }
    }
    //login

    public boolean login(String email, String password){
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("client with email=" + email+ "not found!"));

        if(password.equals(client.getPassword())){
            return  true;
        }
        throw new BadCredentialsException("incorrect password");
    }

    //findAll

    public List<Client> findAll (){
        return clientRepository.findAll();
    }

    @Override
    public void close() throws Exception {
        clientRepository.close();
    }
}
