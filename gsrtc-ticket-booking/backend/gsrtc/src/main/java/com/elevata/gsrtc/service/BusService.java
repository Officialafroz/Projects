package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.BusDTO;
import com.elevata.gsrtc.entity.Bus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusService {

    String add(BusDTO busDTO);

    List<BusDTO> findAllBuses(int depotId);

    String deleteBus(String busNumber);

    List<Bus> findAll();

    Bus findById(int busId);

    void save(Bus bus);

    void delete(int busId);

    BusDTO findByBusNumber(String busNumber);
}
