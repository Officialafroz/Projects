package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.BusDepotSummaryDTO;
import com.elevata.gsrtc.dto.DepotDTO;
import com.elevata.gsrtc.dto.DepotDropDownDTO;
import com.elevata.gsrtc.repository.BusDepotRepository;
import com.elevata.gsrtc.entity.BusDepot;
import com.elevata.gsrtc.service.BusDepotService;
import com.elevata.gsrtc.util.StringNormalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BusDepotServiceImpl implements BusDepotService {
    private BusDepotRepository depotRepository;

    @Autowired
    public BusDepotServiceImpl(BusDepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    @Override
    public List<BusDepot> findAll() {
        return depotRepository.findAll();
    }

    @Override
    public BusDepot findById(int depotId) {
        Optional<BusDepot> optionalBusDepot = depotRepository.findById(depotId);

        if (optionalBusDepot.isPresent()) {
            return optionalBusDepot.get();
        } else {
            throw new RuntimeException("Bus depot not found for id");
        }
    }

    @Override
    public Page<BusDepotSummaryDTO> getAllDepots(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("depotName"));
        return depotRepository.findDepotSummary(pageable);
    }

    @Override
    public BusDepotSummaryDTO getSummaryByName(String depotName) {

        return depotRepository.findDepotSummaryByDepotName(depotName)
                .orElseThrow(() -> new RuntimeException("Depot not found"));
    }

    @Transactional
    @Override
    public void save(BusDepot busDepot) {
        depotRepository.save(busDepot);
        System.out.println("Bus depot is saved.");
    }

    @Override
    public String save(DepotDTO dto) {

        BusDepot depot = new BusDepot();
        depot.setDepotName(dto.getDepotName());
        depot.setCity(dto.getCity());
        depot.setEmail(dto.getEmail());
        depot.setAddress(dto.getAddress());
        depot.setPassword(dto.getPassword());

        depotRepository.save(depot);

        return "Bus depot saved.";
    }

    @Transactional
    @Override
    public String delete(int depotId) {
        if (depotRepository.existsById(depotId)) {
            depotRepository.deleteById(depotId);
            return "Bus depot safely deleted.";
        }

        throw new IllegalArgumentException("Invalid depot id " + depotId);
    }

    public List<String> getDepotNameList() {
        System.out.println(depotRepository.findAllDepotNames());

        return depotRepository.findAllDepotNames();
    }

    public List<DepotDropDownDTO> getDepots() {
        return depotRepository.getDepots();
    }

    @Override
    public Page<DepotDTO> getList(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("depotName"));
        return depotRepository.getList(pageable);
    }

    @Override
    public String update(int depotId, String depotName, String address, String password) {

        BusDepot existingDepot = depotRepository.findById(depotId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid depot id " + depotId));

        depotName = StringNormalizer.normalize(depotName);
        address = StringNormalizer.normalize(address);
        password = StringNormalizer.normalize(password);

        if (depotName != null) {
            existingDepot.setDepotName(depotName);
        }

        if (address != null) {
            existingDepot.setAddress(address);
        }

        if (password != null) {
            existingDepot.setPassword(password);
        }

        depotRepository.save(existingDepot);

        return "Depot update successfully for id " + depotId;
    }

    @Override
    public DepotDTO getByName(String depotName) {
        return depotRepository.findByDepotName(depotName)
                .orElseThrow(() -> new IllegalArgumentException("Depot not found for name " + depotName));
    }
}
