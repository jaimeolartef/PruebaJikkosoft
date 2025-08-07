package com.example.pruebajikkosft.model.repositories;

import com.example.pruebajikkosft.model.entities.ReglaDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReglaDescuentoRepository extends JpaRepository<ReglaDescuento, Integer> {
}
