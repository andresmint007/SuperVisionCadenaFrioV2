package com.medisupply.supervisioncadenfriov2.controller;


import com.medisupply.supervisioncadenfriov2.dto.EventDto;
import com.medisupply.supervisioncadenfriov2.dto.NotificationDto;
import com.medisupply.supervisioncadenfriov2.dto.RequestDto;
import com.medisupply.supervisioncadenfriov2.entity.Request;
import com.medisupply.supervisioncadenfriov2.service.ExternalConnections;
import com.medisupply.supervisioncadenfriov2.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/createrequest")
    public ResponseEntity<?> processTemperatureRequest(@Valid @RequestBody RequestDto requestDTO) {
        try {
            Request savedRequest = requestService.saveRequest(requestDTO);
            Object[] status = analyzeTemperature(requestDTO.getTemperature());
            String reason = ActionTemperature(status, String.valueOf(savedRequest.getId()),savedRequest.getTemperature(),savedRequest.getUbicacion());

            Map<String, Object> response = buildResponse(requestDTO, savedRequest, status, reason);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "error processing request");
            response.put("errors", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String ActionTemperature (Object[] TakeAction,String idTransaccion,Double temperatura,String ubicacion){
        ExternalConnections externalConnections = new ExternalConnections();
        if(Boolean.TRUE.equals(TakeAction[1])){
            NotificationDto notificationDTO = new NotificationDto(
                    "andreslab7@gmail.com",
                    "Alerta de temperatura",
                    (String) TakeAction[0]+ " Regirstro: "+temperatura.toString(),
                    "+573174669534",
                    (String) TakeAction[0]
            );
            externalConnections.enviarAlerta(notificationDTO);
            return "Alerta enviada";
        }else{

            EventDto event = new EventDto();

            event.setProducto_id(idTransaccion);
            event.setTipo_evento("TEMPERATURA_NORMAL");
            event.setFecha_evento(LocalDate.now());
            event.setDescripcion_evento((String) TakeAction[0]);

            EventDto.DataEvent dataEvent = new EventDto.DataEvent();
            dataEvent.setValor_temperatura(temperatura);
            dataEvent.setUnidad("Celsius");
            dataEvent.setUbicacion(ubicacion);

            return "Temperatura Ok";
        }
    }
    private Object[] analyzeTemperature(Double temperature) {
        if (temperature == null) {
            return new Object[]{"UNKNOWN", false};
        }

        if (temperature < 2) {
            return new Object[]{"Temperatura demasiado baja", true};
        } else if (temperature > 8) {
            return new Object[]{"Temperatura demasiado alta", true};
        } else {
            return new Object[]{"Temperatura normal", false};
        }
    }

    private Map<String, Object> buildResponse(RequestDto requestDTO, Request savedRequest, Object status, String action) {
        Map<String, Object> response = new HashMap<>();
        response.put("requestId", savedRequest.getId());
        response.put("status", status);
        response.put("action", action);
        response.put("temperature", requestDTO.getTemperature());
        response.put("type", requestDTO.getType());
        response.put("timestamp", System.currentTimeMillis());
        response.put("mensaje", "Success");
        return response;
    }

}
