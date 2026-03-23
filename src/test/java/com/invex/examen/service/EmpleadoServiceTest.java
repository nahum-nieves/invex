package com.invex.examen.service;

import com.invex.examen.dto.EmpleadoDto;
import com.invex.examen.entities.Empleado;
import com.invex.examen.exception.ValidationException;
import com.invex.examen.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmpleadoServiceTest {
    @Mock
    private EmpleadoRepository empleadoRepository;
    @InjectMocks
    private EmpleadoService empleadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_returnsAllEmpleados() {
        Empleado empleado = new Empleado();
        empleado.setId(1);
        when(empleadoRepository.findAll()).thenReturn(Collections.singletonList(empleado));
        List<EmpleadoDto> result = empleadoService.findAll();
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    void findById_found() {
        Empleado empleado = new Empleado();
        empleado.setId(2);
        empleado.setPrimerNombre("Juan");
        when(empleadoRepository.findById(2)).thenReturn(Optional.of(empleado));
        Optional<EmpleadoDto> result = empleadoService.findById(2);
        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getPrimerNombre());
    }

    @Test
    void findById_notFound() {
        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());
        Optional<EmpleadoDto> result = empleadoService.findById(99);
        assertFalse(result.isPresent());
    }

    @Test
    void saveAll_savesAndReturnsDtos() {
        EmpleadoDto dto = new EmpleadoDto();
        dto.setPrimerNombre("Ana");
        Empleado saved = new Empleado();
        saved.setPrimerNombre("Ana");
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(saved);
        List<EmpleadoDto> result = empleadoService.saveAll(Collections.singletonList(dto));
        assertEquals(1, result.size());
        assertEquals("Ana", result.get(0).getPrimerNombre());
    }

    @Test
    void update_successful() {
        EmpleadoDto dto = new EmpleadoDto();
        dto.setPrimerNombre("Luis");
        dto.setApellidoPaterno("Pérez");
        Empleado existing = new Empleado();
        existing.setId(5);
        when(empleadoRepository.findById(5)).thenReturn(Optional.of(existing));
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(invocation -> invocation.getArgument(0));
        EmpleadoDto result = empleadoService.update(5, dto);
        assertEquals("Luis", result.getPrimerNombre());
    }

    @Test
    void update_notFound_throws() {
        EmpleadoDto dto = new EmpleadoDto();
        when(empleadoRepository.findById(100)).thenReturn(Optional.empty());
        assertThrows(ValidationException.class, () -> empleadoService.update(100, dto));
    }

    @Test
    void update_invalidEmpleado_throwsValidation() {
        EmpleadoDto dto = new EmpleadoDto();
        dto.setPrimerNombre("");
        assertThrows(ValidationException.class, () -> empleadoService.update(1, dto));
    }

    @Test
    void deleteById_delegatesToRepository() {
        empleadoService.deleteById(7);
        verify(empleadoRepository,times(1)).deleteById(7);
    }

    @Test
    void searchByName_returnsDtos() {
        Empleado empleado = new Empleado();
        empleado.setPrimerNombre("Carlos");
        when(empleadoRepository.searchByName("Car")).thenReturn(Collections.singletonList(empleado));
        List<EmpleadoDto> result = empleadoService.searchByName("Car");
        assertEquals(1, result.size());
        assertEquals("Carlos", result.get(0).getPrimerNombre());
    }
}