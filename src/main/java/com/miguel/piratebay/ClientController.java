package com.miguel.piratebay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private ClientMapper clientMapper;

    @Autowired
    public ClientController(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createClient(@RequestBody ClientDto clientDto){
        //Verificamos que el firstName no sea nullo o vacio
        if(clientDto.getFirstName() == null || "".equals(clientDto.getFirstName().trim())){
            Map response = new HashMap();
            response.put("code",1001);
            response.put("message","El cliente no tiene primer nombre");
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
        //Verificamos que el lastName no sea nullo o vacio
        if(clientDto.getLastName() == null || "".equals(clientDto.getLastName().trim())){
            Map response = new HashMap();
            response.put("code",1002);
            response.put("message","El cliente no tiene apellido");
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
        clientMapper.create(clientDto);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity listClient(){
        return new ResponseEntity<>(clientMapper.listAll(), HttpStatus.OK);
    }

    //La ruta para la eliminacion deberia ser /api/v1/clients/123
    @RequestMapping(
            path = "/{clientId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity removeClient(@PathVariable("clientId") Integer clientId){
        clientMapper.delete(clientId);
        Map response = new HashMap();
        response.put("code",0);
        response.put("message", "Eliminacion correcta");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(
            path = "/{clientId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity updateClient(@PathVariable("clientId") Integer clientId, @RequestBody ClientDto clientDto){
        //Verificamos que el firstName no sea nullo o vacio
        if(clientDto.getFirstName() == null || "".equals(clientDto.getFirstName().trim())){
            Map response = new HashMap();
            response.put("code",1001);
            response.put("message","El cliente no tiene primer nombre");
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
        //Verificamos que el lastName no sea nullo o vacio
        if(clientDto.getLastName() == null || "".equals(clientDto.getLastName().trim())){
            Map response = new HashMap();
            response.put("code",1002);
            response.put("message","El cliente no tiene apellido");
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
        //Asignacion del clientId al clientDto (porque lo usaba el mapper) a partir del PathVariable
        clientDto.setClientId(clientId);
        //Invocacion de la base de datos para la actualizacion de la base de datos
        clientMapper.update(clientDto);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }
}
