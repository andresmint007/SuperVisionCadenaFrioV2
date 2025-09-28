package com.medisupply.supervisioncadenfriov2.service;

import com.medisupply.supervisioncadenfriov2.dto.RequestDto;
import com.medisupply.supervisioncadenfriov2.entity.Request;
import com.medisupply.supervisioncadenfriov2.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    /**
     * Sauvegarde une requête pour audit/logging
     */
    public Request saveRequest(RequestDto requestDTO) {
        Request request = new Request(requestDTO.getTemperature(), requestDTO.getType(), requestDTO.getLocation());
        Request saved = requestRepository.save(request);

        log.info("Request saved - ID: {}, Temperature: {}, Type: {}",
                saved.getId(), saved.getTemperature(), saved.getTiposensor());

        return saved;
    }
    /**
     * Récupère tous les logs de requêtes
     */
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    /**
     * Récupère une requête spécifique par ID
     */
    public Request getRequestById(UUID id) {
        return requestRepository.findById(id).orElse(null);
    }


}
