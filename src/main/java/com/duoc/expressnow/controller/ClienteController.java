package com.duoc.expressnow.controller;

import com.duoc.expressnow.model.ClienteModel;
import com.duoc.expressnow.repository.ClienteRepository;
import com.duoc.expressnow.seguridad.ClienteSeguridad;
import com.duoc.expressnow.service.ClienteService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
    @RequestMapping("/api/usuarios")
    public class ClienteController {

    @Autowired
    private ClienteService service;
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private ClienteSeguridad seguridad;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/registrar")
    public ClienteModel registrar(@RequestBody ClienteModel cliente) {
        return service.registrar(cliente);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody ClienteModel loginRequest) {
        ClienteModel u = repository.findByUsername(loginRequest.getUsername());

        if (u != null && encoder.matches(loginRequest.getPassword(), u.getPassword())) {
            String token = seguridad.generarToken(u.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("access_token", token);
            response.put("token_type", "Bearer");
            return response;
        }
        throw new RuntimeException("Credenciales incorrectas");
    }

    // AGREGAR ESTO EN EL CLIENTECONTROLLER DE EXPRESSNOW (Puerto 8081)
    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> obtenerUsuarioPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }
}
