package com.invex.examen.controller;

import com.invex.examen.dto.LoginRequest;
import com.invex.examen.dto.LoginResponse;
import com.invex.examen.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class LoginController implements LoginApi {

    private final UsuarioService usuarioService;

    public ResponseEntity<LoginResponse> loginPost(@RequestBody LoginRequest usuarioDto) {
        return ResponseEntity.accepted(new LoginResponse().token( usuarioService.login(usuarioDto)));
    }
}
