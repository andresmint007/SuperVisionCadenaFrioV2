package com.medisupply.supervisioncadenfriov2.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.Column;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("requests")
public class Request {
    @PrimaryKey
    private UUID id;

    @Column("temperature")
    private Double temperature;

    @Column("ubicacion")
    private String ubicacion;

    @Column("tiposensor")
    private String tiposensor;

    @Column("timestamp")
    private Instant timestamp;

    public Request(Double temperature, String tiposensor, String ubicacion) {
        this.id = UUID.randomUUID();
        this.temperature = temperature;
        this.tiposensor = tiposensor;
        this.timestamp = Instant.now();
        this.ubicacion = ubicacion;
    }
}
