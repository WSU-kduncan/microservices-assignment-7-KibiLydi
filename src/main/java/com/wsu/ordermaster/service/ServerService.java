package com.wsu.ordermaster.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wsu.ordermaster.DTO.ServerDTO;
import com.wsu.ordermaster.exception.DatabaseErrorException;
import com.wsu.ordermaster.exception.InvalidRequestException;
import com.wsu.ordermaster.model.Server;
import com.wsu.ordermaster.repository.ServerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServerService
{

    private final ServerRepository serverRepository;  
      
    public Page<ServerDTO> get(String search, String sortField, String sortOrder, Integer page, Integer rpp) 
    {
        try
        {
                Page<Object[]> servers = serverRepository.findBySearch(search, PageRequest.of(page-1, rpp));
                return servers.map(server -> ServerDTO.builder().id((Integer) server[0]).firstName((String) server[1])
                    .lastName((String) server[2]).availability((String) server[3]).build());
        } 
        catch (Exception e) 
        {
            log.error("Failed to retrieve servers. search:{}, sortField:{}, sortOrder:{}, page:{}, rpp:{}. Exception:",
                    search, sortField, sortOrder, page, rpp, e);
            throw new DatabaseErrorException("Failed to retrieve servers.", e);
        }
    }

    public ServerDTO save(ServerDTO serverDTO) 
    {
        if (serverRepository.existsById(serverDTO.getId()))
        {
            throw new InvalidRequestException("Server ID already exists.");
        }
        try 
        {
            return convertToDTO(serverRepository.save(convertToEntity(serverDTO)));
        }
        catch (Exception e) 
        {
            log.error("Failed to create new server. Exception:", e);
            throw new DatabaseErrorException("Failed to create new server", e);
        }
    }

    public ServerDTO update(Integer id, ServerDTO serverDTO) 
    {
        if (!serverRepository.existsById(id)) 
        {
            throw new InvalidRequestException("Invalid server id.");
        }
        try 
        {
            Server server = convertToEntity(serverDTO);
            server.setId(id);
            return convertToDTO(serverRepository.save(server));
        } 
        catch (Exception e)
        {
            log.error("Failed to update server, serverId:{}. Exception:{}", id, e);
            throw new DatabaseErrorException("Failed to update server", e);
        }
    }

    /**
     * This method used for convert DTO to entity model class.
     */
    public Server convertToEntity(ServerDTO serverDTO) 
    {
        return Server.builder().firstName(serverDTO.getFirstName())
                .lastName(serverDTO.getLastName()).availability(serverDTO.getAvailability()).build();
    }

    /**
     * This method used for convert Entity model class to DTO
     */
    public ServerDTO convertToDTO(Server server) 
    {
        return ServerDTO.builder().id(server.getId()).firstName(server.getFirstName())
                .lastName(server.getLastName()).availability(server.getAvailability()).build();
    }

    public void delete(Integer id) 
    {
        if (!serverRepository.existsById(id)) 
        {
            throw new InvalidRequestException("Invalid server code.");
        }
        try 
        {
            serverRepository.deleteById(id);
        } 
        catch (Exception e) 
        {
            log.error("Failed to delete server, serverId:{}. Exception:{}", id, e);
            throw new DatabaseErrorException("Failed to delete server", e);
        }
    }
}
