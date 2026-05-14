package com.duoc.expressnow.service;


import com.duoc.expressnow.model.ClienteModel;
import com.duoc.expressnow.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    private BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();

    public ClienteModel registrar(ClienteModel cliente){
         cliente.setPassword(enconder.encode(cliente.getPassword()));
        return clienteRepository.save(cliente);
    }
}


