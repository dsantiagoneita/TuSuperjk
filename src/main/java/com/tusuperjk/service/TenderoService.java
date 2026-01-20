package com.tusuperjk.service;

import com.tusuperjk.model.Tendero;
import com.tusuperjk.repository.TenderoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TenderoService {

    private final TenderoRepository tenderoRepository;

    @Autowired
    public TenderoService(TenderoRepository tenderoRepository) {
        this.tenderoRepository = tenderoRepository;
    }

    @Transactional(readOnly = true)
    public List<Tendero> listarTenderos() {
        return tenderoRepository.findAll();
    }
}
