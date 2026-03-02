package com.groupe2.METOA.Service.Trajet;

import com.groupe2.METOA.Dto.TrajetReqDto;
import com.groupe2.METOA.Dto.TrajetResDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TrajetService {
    void addTrajet(TrajetReqDto trajetReqDto);
    TrajetResDto getTrajetById(String idTrajet);
    List<TrajetResDto> getAllTrajets();
    void updateTrajet(String idTrajet, TrajetResDto trajetResDto);
    void deleteTrajet(String idTrajet);
    Page<TrajetResDto> getTrajets(int page, int size);
}
