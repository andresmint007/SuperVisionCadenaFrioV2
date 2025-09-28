package com.medisupply.supervisioncadenfriov2.service;

import com.medisupply.supervisioncadenfriov2.dto.EventDto;
import com.medisupply.supervisioncadenfriov2.dto.NotificationDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class ExternalConnections {

    private final RestTemplate restTemplate = new RestTemplate();


    private String apiUrlNotifications="http://notifications.default.svc.cluster.local:9020";
    private String apiUrlNormalizadas ="http://normalizadas.default.svc.cluster.local:9020";

    public void enviarAlerta(NotificationDto notification) {
        String url = apiUrlNotifications + "/notifications";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NotificationDto> request = new HttpEntity<>(notification, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
    public void enviarNormalidadTemperatura(EventDto eventdto) {
        String url = apiUrlNormalizadas + "/EnviarTrazabilidad";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EventDto> request = new HttpEntity<>( eventdto,headers);
        restTemplate.postForEntity(url, request, String.class);
    }
}
