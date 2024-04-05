package com.amal.sunbase.Repository;

import com.amal.sunbase.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Customer repository for managing CRUD operation
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhone(String phone);

    List<Customer> findByFirstNameContainingIgnoreCase(String searchQuery);

    List<Customer> findByCityContainingIgnoreCase(String searchQuery);

    List<Customer> findByEmailContainingIgnoreCase(String searchQuery);

    List<Customer> findByPhoneContainingIgnoreCase(String searchQuery);
}