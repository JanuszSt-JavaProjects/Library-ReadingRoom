package com.librarybackend.library.controller;

import com.librarybackend.library.domain.Customer;
import com.librarybackend.library.domain.dto.customerDto.CustomerDto;
import com.librarybackend.library.mapper.ClientMapper;
import com.librarybackend.library.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    ClientMapper clientMapper;
    CustomerService customerService;

    CustomerController(ClientMapper clientMapper, CustomerService customerService) {
        this.clientMapper = clientMapper;
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerDto create(@RequestBody CustomerDto customerDto) {

        Customer customer = clientMapper.mapClientDtoToClient(customerDto);
        customer = customerService.save(customer);
        return clientMapper.mapClientToClientDto(customer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        customerService.remove(id);
    }

    @PutMapping
    public CustomerDto update(@RequestBody CustomerDto customerDto) {
        Customer customer = clientMapper.mapClientDtoToClient(customerDto);
        customer = customerService.update(customer);
        return clientMapper.mapClientToClientDto(customer);
    }

    @GetMapping("/{id}")
    public CustomerDto getOne(@PathVariable long id) {
        return clientMapper.mapClientToClientDto(customerService.getOne(id));
    }

    @GetMapping
    public Set<CustomerDto> getAll() {

        Set<CustomerDto> customerDtos = new HashSet<>();
        customerService.getAll().forEach(client -> customerDtos.add(clientMapper.mapClientToClientDto(client)));
        return customerDtos;
    }
}
