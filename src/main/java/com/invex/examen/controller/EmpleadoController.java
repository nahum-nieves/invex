package com.invex.examen.controller;

import com.invex.examen.dto.EmpleadoDto;
import com.invex.examen.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmpleadoController implements EmployeesApi {
    private final EmpleadoService empleadoService;

    @Override
    public ResponseEntity<List<EmpleadoDto>> employeesGet() {
        List<EmpleadoDto> empleados = empleadoService.findAll().stream().map(e -> {
            EmpleadoDto dto = new EmpleadoDto();
            org.springframework.beans.BeanUtils.copyProperties(e, dto);
            return dto;
        }).toList();
        return ResponseEntity.ok(empleados);
    }

    @Override
    public ResponseEntity<EmpleadoDto> employeesIdGet(Integer id) {
        return empleadoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<EmpleadoDto>> employeesPost(List<EmpleadoDto> empleadoDto) {
        List<EmpleadoDto> saved = empleadoService.saveAll(empleadoDto);
        return ResponseEntity.status(201).body(saved);
    }

    @Override
    public ResponseEntity<EmpleadoDto> employeesIdPut(Integer id, EmpleadoDto empleadoDto) {
        try {
            EmpleadoDto updated = empleadoService.update(id, empleadoDto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> employeesIdDelete(Integer id) {
        empleadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<EmpleadoDto>> employeesSearchGet(String name) {
        List<EmpleadoDto> result = empleadoService.searchByName(name);
        return ResponseEntity.ok(result);
    }
}
