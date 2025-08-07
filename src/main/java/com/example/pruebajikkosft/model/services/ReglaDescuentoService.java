package com.example.pruebajikkosft.model.services;

import com.example.pruebajikkosft.model.entities.ReglaDescuento;
import com.example.pruebajikkosft.model.repositories.ReglaDescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReglaDescuentoService implements GenericService<ReglaDescuento, Integer> {

    private final ReglaDescuentoRepository reglaDescuentoRepository;

    @Autowired
    public ReglaDescuentoService(ReglaDescuentoRepository reglaDescuentoRepository) {
        this.reglaDescuentoRepository = reglaDescuentoRepository;
    }

    @Override
    public List<ReglaDescuento> findAll() {
        return reglaDescuentoRepository.findAll();
    }

    @Override
    public Optional<ReglaDescuento> findById(Integer id) {
        return reglaDescuentoRepository.findById(id);
    }

    @Override
    public ReglaDescuento save(ReglaDescuento reglaDescuento) {
        return reglaDescuentoRepository.save(reglaDescuento);
    }

    @Override
    public void deleteById(Integer id) {
        reglaDescuentoRepository.deleteById(id);
    }
}