package com.sakri.server.service.implementation;

import com.sakri.server.enumeration.Status;
import com.sakri.server.model.Server;
import com.sakri.server.repo.ServerRepo;
import com.sakri.server.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j

public class ServerServiceImpl  implements ServerService {

private  final ServerRepo  serverRepo ;

    @Override
    public Server create(Server server) {
        log.info("Saving new server :{} ", server.getName());
        server.setIdAddress(server.getIdAddress());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP :{} ", ipAddress);
        Server server = serverRepo.findByIdAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
         server.setStatus(address.isReachable( 1000 ) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepo.save(server);
         return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0,limit)).toList();

    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id",id);
        return serverRepo.findById(id).get();

    }

    @Override
    public Server update(Server server) {
        log.info("Fetching server by id{}",server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("deleting  server by id{}",id);
        serverRepo.deleteById(id);
        return Boolean.TRUE;
    }


    public String setServerImageUrl() {
      String []imageName = { "server1.png","server2.png","server3.png","server4.png"};
      return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageName[new Random().nextInt(4)]).toUriString();
    }
}
