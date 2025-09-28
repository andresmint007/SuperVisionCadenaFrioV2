package com.medisupply.supervisioncadenfriov2.repository;

import com.medisupply.supervisioncadenfriov2.entity.Request;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestRepository extends CassandraRepository<Request, UUID> {
}
