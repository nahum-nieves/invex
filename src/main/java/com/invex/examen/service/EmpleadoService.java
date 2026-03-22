package com.invex.examen.service;

import com.invex.examen.dto.EmpleadoDto;
import com.invex.examen.entities.Empleado;
import com.invex.examen.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    public Optional<EmpleadoDto> findById(Integer id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        return empleado.map(e -> {
            EmpleadoDto dto = new EmpleadoDto();
            BeanUtils.copyProperties(e, dto);
            return dto;
        });
    }


    @Transactional
    public List<EmpleadoDto> saveAll(List<EmpleadoDto> empleados) {
        return empleados.stream()
            .map(dto -> {
            Empleado empleado = new Empleado();
            BeanUtils.copyProperties(dto, empleado);
            return empleadoRepository.save(empleado);
        }).map(saved -> {
            EmpleadoDto dto = new EmpleadoDto();
            BeanUtils.copyProperties(saved, dto);
            return dto;
        }).toList();
    }

    @Transactional
    public EmpleadoDto update(Integer id, EmpleadoDto empleadoDto) {
        return empleadoRepository.findById(id)
                .map(existing -> {
                    BeanUtils.copyProperties(empleadoDto, existing, "id");
                    return empleadoRepository.save(existing);
                }).map(empleado -> {
                    EmpleadoDto dto = new EmpleadoDto();
                    BeanUtils.copyProperties(empleado, dto);
                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("Empleado not found"));
    }

    @Transactional
    public void deleteById(Integer id) {
        empleadoRepository.deleteById(id);
    }

    public List<EmpleadoDto> searchByName(String name) {
        return empleadoRepository.searchByName(name).stream().map(empleado -> {
            EmpleadoDto dto = new EmpleadoDto();
            BeanUtils.copyProperties(empleado, dto);
            return dto;
        }).toList();
    }
}
