package com.invex.examen.service;

import com.invex.examen.dto.EmpleadoDto;
import com.invex.examen.entities.Empleado;
import com.invex.examen.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public List<Empleado> findAll() {
        log.info("Buscando todos los empleados");
        return empleadoRepository.findAll();
    }

    public Optional<EmpleadoDto> findById(Integer id) {
        log.info("Buscando empleado por id: {}", id);
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isEmpty()) {
            log.warn("Empleado no encontrado con id: {}", id);
        }
        return empleado.map(e -> {
            EmpleadoDto dto = new EmpleadoDto();
            BeanUtils.copyProperties(e, dto);
            return dto;
        });
    }


    @Transactional
    public List<EmpleadoDto> saveAll(List<EmpleadoDto> empleados) {
        log.info("Guardando lista de empleados. Total: {}", empleados.size());
        return empleados.stream()
            .map(dto -> {
                log.debug("Guardando empleado: {}", dto);
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
        log.info("Actualizando empleado con id: {}", id);
        return empleadoRepository.findById(id)
                .map(existing -> {
                    BeanUtils.copyProperties(empleadoDto, existing, "id");
                    return empleadoRepository.save(existing);
                }).map(empleado -> {
                    EmpleadoDto dto = new EmpleadoDto();
                    BeanUtils.copyProperties(empleado, dto);
                    return dto;
                })
                .orElseThrow(() -> {
                    log.warn("Empleado no encontrado para actualizar con id: {}", id);
                    return new RuntimeException("Empleado not found");
                });
    }

    @Transactional
    public void deleteById(Integer id) {
        log.info("Eliminando empleado con id: {}", id);
        empleadoRepository.deleteById(id);
    }

    public List<EmpleadoDto> searchByName(String name) {
        log.info("Buscando empleados por nombre: {}", name);
        return empleadoRepository.searchByName(name).stream().map(empleado -> {
            EmpleadoDto dto = new EmpleadoDto();
            BeanUtils.copyProperties(empleado, dto);
            return dto;
        }).toList();
    }
}
