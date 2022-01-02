package com.librarybackend.library.repository;


import com.librarybackend.library.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Override
    List<Customer> findAll() ;
}
