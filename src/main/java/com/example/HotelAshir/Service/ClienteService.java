package com.example.HotelAshir.Service;

import com.example.HotelAshir.Exception.ApiRequestException;
import com.example.HotelAshir.Model.Cliente;
import com.example.HotelAshir.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente registrarCliente(Cliente cliente){
        if(cliente.getNombre()==null){
            throw new ApiRequestException("El campo de nombre no esta en el body");
        } else if(cliente.getApellido()==null){
            throw new ApiRequestException("El campo de apellido no esta en el body");
        } else if(cliente.getCedula()==null){
            throw new ApiRequestException("El campo de cedula no esta en el body");
        }
        this.clienteRepository.save(cliente);
        return cliente;
    }
}

