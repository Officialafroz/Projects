package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.BusDTO;
import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.repository.AppUserRepository;
import com.elevata.gsrtc.repository.BusDepotRepository;
import com.elevata.gsrtc.repository.BusLayoutRepository;
import com.elevata.gsrtc.repository.BusRepository;
import com.elevata.gsrtc.entity.Bus;
import com.elevata.gsrtc.entity.BusDepot;
import com.elevata.gsrtc.entity.BusLayout;
import com.elevata.gsrtc.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;
    private final BusDepotRepository depotRepository;
    private final BusLayoutRepository busLayoutRepository;
    private final AppUserRepository userRepository;

    @Autowired
    public BusServiceImpl(BusRepository busRepository, BusDepotRepository depotRepository, BusLayoutRepository busLayoutRepository, AppUserRepository userRepository) {
        this.busRepository = busRepository;
        this.depotRepository = depotRepository;
        this.busLayoutRepository = busLayoutRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    @Override
    public Bus findById(int busId) {
        Optional<Bus> optionalBus = busRepository.findById(busId);

        if (optionalBus.isPresent()) {
            return optionalBus.get();
        } else {
            throw new RuntimeException("Bus not found for id");
        }
    }

    @Override
    @Transactional
    public void save(Bus bus) {
        if (
                bus.getBusDepot() != null &&
                bus.getBusLayout() != null &&
                bus.getBusDepot().getDepotId() > 0 &&
                bus.getBusLayout().getLayoutId() > 0
        ) {
            int depotId = bus.getBusDepot().getDepotId();
            int layoutId = bus.getBusLayout().getLayoutId();

            Optional<BusDepot> existingDepot = depotRepository.findById(depotId);
            Optional<BusLayout> existingBusLayout = busLayoutRepository.findById(layoutId);

            if (existingDepot.isEmpty() || existingBusLayout.isEmpty()) {
                throw new RuntimeException(
                        String.format("Invalid depot id - %d or layout id - %d", depotId, layoutId));
            }
            bus.setBusDepot(existingDepot.get());
            bus.setBusLayout(existingBusLayout.get());
        }

        busRepository.save(bus);
        System.out.println("Bus is saved.");
    }

    @Override
    @Transactional
    public void delete(int busId) {
        busRepository.deleteById(busId);
        System.out.println("Bus is safely deleted.");
    }

    @Override
    public BusDTO findByBusNumber(String busNumber) {
        Bus bus = busRepository.findByBusNumber(busNumber);
        BusDTO busDTO = new BusDTO();

        busDTO.setBusNumber(bus.getBusNumber());
        busDTO.setBusType(bus.getBusType());

        return busDTO;
    }

    @Override
    @Transactional
    public String add(BusDTO busDTO) {
        int busLayoutId = busDTO.getTotalSeats() == 46 ? 1 : busDTO.getTotalSeats() == 45 ? 2 : 3;
        Optional<BusLayout> busLayout = busLayoutRepository.findById(busLayoutId);
        AppUser depotAdmin = userRepository.findByBusDepotDepotId(busDTO.getDepotId())
                .orElseThrow(() -> new RuntimeException("Bus depot not found."));

        Bus bus = new Bus();
        bus.setBusNumber(busDTO.getBusNumber());
        bus.setBusType(busDTO.getBusType());
        busLayout.ifPresent(bus::setBusLayout);
        bus.setBusDepot(depotAdmin.getBusDepot());

        busRepository.save(bus);

        return "Bus added.";
    }

    @Override
    public List<BusDTO> findAllBuses(int depotId) {
        List<BusDTO> busDTOS = new ArrayList<>();
        List<Bus> buses = busRepository.findAllByBusDepotDepotId(depotId);


        buses.forEach(bus -> {
            BusDTO busDTO = new BusDTO();

            busDTO.setBusNumber(bus.getBusNumber());
            busDTO.setBusType(bus.getBusType());
            busDTO.setTotalSeats(bus.getBusLayout().getTotalSeats());
            busDTO.setDepotId(depotId);

            busDTOS.add(busDTO);
        });

        return busDTOS;

    }

    @Override
    @Transactional
    public String deleteBus(String busNumber) {
        Bus bus = busRepository.findByBusNumber(busNumber);
        busRepository.deleteById(bus.getBusId());

        return "Bus deleted...";
    }
}
