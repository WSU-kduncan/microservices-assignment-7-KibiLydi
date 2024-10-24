package com.wsu.ordermaster.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.wsu.ordermaster.DTO.ServerDTO;
import com.wsu.ordermaster.DTO.ServiceResponseDTO;
import com.wsu.ordermaster.service.ServerService;
import com.wsu.ordermaster.utilities.Constants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerController 
{
    private final ServerService serverService;
    @GetMapping
    public ResponseEntity<ServiceResponseDTO> getServers(@RequestParam(required = false) String search,
                                                  @RequestParam(required = false, defaultValue = "1") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer rpp,
                                                  @RequestParam(required = false, defaultValue = "dateLastUpdated") String sortField,
                                                  @RequestParam(required = false, defaultValue = Constants.DESC) String sortOrder)
    {
        Page<ServerDTO> serverDTOPagePage = serverService.get(search, sortField, sortOrder, page, rpp);
        return new ResponseEntity<>(ServiceResponseDTO.builder().meta(Map.of(Constants.MESSAGE, "Servers retrieved successfully.", Constants.PAGE_COUNT,
                serverDTOPagePage.getTotalPages(), Constants.RESULT_COUNT, serverDTOPagePage.getTotalElements())).data(serverDTOPagePage.getContent())
                .build(), HttpStatus.OK);
    }

        @PostMapping
    public ResponseEntity<ServiceResponseDTO> createServer(@RequestBody @Valid ServerDTO serverDTO)
    {
        return new ResponseEntity<>(ServiceResponseDTO.builder().meta(Map.of(Constants.MESSAGE, "Server created successfully"))
                .data(serverService.save(serverDTO)).build(), HttpStatus.OK);
    }

        @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> updateServer(@PathVariable Integer id, @RequestBody @Valid ServerDTO serverDTO)
    {
        return new ResponseEntity<>(ServiceResponseDTO.builder().meta(Map.of(Constants.MESSAGE, "Server updated successfully"))
                .data(serverService.update(id, serverDTO)).build(), HttpStatus.OK);
    }

        @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> deleteServer(@PathVariable Integer id) 
    {
        serverService.delete(id);
        return new ResponseEntity<>(ServiceResponseDTO.builder().meta(Map.of(Constants.MESSAGE, "Server deleted successfully")).build(), HttpStatus.OK);
    }
    
}
