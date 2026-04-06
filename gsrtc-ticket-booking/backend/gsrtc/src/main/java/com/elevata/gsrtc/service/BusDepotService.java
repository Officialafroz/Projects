package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.BusDepotSummaryDTO;
import com.elevata.gsrtc.dto.DepotDTO;
import com.elevata.gsrtc.dto.DepotDropDownDTO;
import com.elevata.gsrtc.entity.BusDepot;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusDepotService {
    List<BusDepot> findAll();

    BusDepot findById(int depotId);

    Page<BusDepotSummaryDTO> getAllDepots(int page, int size);

    BusDepotSummaryDTO getSummaryByName(String depotName);

    void save(BusDepot busDepot);

    String save(DepotDTO dto);

    String delete(int depotId);

    List<String> getDepotNameList();

    List<DepotDropDownDTO> getDepots();

    Page<DepotDTO> getList(int page, int size);

    String update(int depotId, String depotName, String address, String password);

    DepotDTO getByName(String depotName);
}
