package com.sakri.server.repo;

import com.sakri.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepo extends JpaRepository <Server,Long>{

    Server findByIdAddress(String ipAddress);


}
