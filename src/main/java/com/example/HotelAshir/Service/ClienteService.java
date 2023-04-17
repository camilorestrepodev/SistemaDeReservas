package com.example.HotelAshir.Service;

import com.example.HotelAshir.Dto.ClienteDto;
import com.example.HotelAshir.Exception.ApiRequestException;
import com.example.HotelAshir.Model.Cliente;
import com.example.HotelAshir.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteDto registrarCliente(ClienteDto clienteDto){
        if(clienteDto.getNombre()==null){
            throw new ApiRequestException("El campo de nombre no esta en el body");
        } else if(clienteDto.getApellido()==null){
            throw new ApiRequestException("El campo de apellido no esta en el body");
        } else if(clienteDto.getCedula()==null){
            throw new ApiRequestException("El campo de cedula no esta en el body");
        }
        Cliente cliente = new Cliente(
                clienteDto.getNombre(),
                clienteDto.getApellido(),
                clienteDto.getCedula(),
                clienteDto.getCorreo(),
                clienteDto.getEdad(),
                clienteDto.getDireccion());
        clienteRepository.save(cliente);
        return clienteDto;
    }
}

