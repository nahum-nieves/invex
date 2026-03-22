package com.invex.examen.repository;

import com.invex.examen.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    @Query("SELECT e FROM Empleado e WHERE LOWER(e.primerNombre) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(e.segundoNombre) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Empleado> searchByName(@Param("name") String name);
}

