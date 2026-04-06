package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.repository.BusLayoutRepository;
import com.elevata.gsrtc.entity.BusLayout;
import com.elevata.gsrtc.service.BusLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BusLayoutServiceImpl implements BusLayoutService {
    private BusLayoutRepository busLayoutRepository;

    @Autowired
    public BusLayoutServiceImpl(BusLayoutRepository busLayoutRepository) {
        this.busLayoutRepository = busLayoutRepository;
    }

    @Override
    public List<BusLayout> findAll() {
        return busLayoutRepository.findAll();
    }

    @Override
    public BusLayout findById(int layoutId) {
        Optional<BusLayout> optionalBusLayout = busLayoutRepository.findById(layoutId);

        if (optionalBusLayout.isPresent()) {
            return optionalBusLayout.get();
        } else {
            throw new RuntimeException("Bus layout not found for id");
        }
    }

    @Override
    @Transactional
    public void save(BusLayout BusLayout) {
        busLayoutRepository.save(BusLayout);
        System.out.println("Bus Layout is saved.");
    }

    @Override
    @Transactional
    public void delete(int layoutId) {
        busLayoutRepository.deleteById(layoutId);
        System.out.println("Bus layout is safely deleted.");
    }
}

