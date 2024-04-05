package com.amal.sunbase.Service.Interface;

import com.amal.sunbase.Dto.Request.CustomerDto;
import com.amal.sunbase.Dto.Response.CustomerResponseDto;
import com.amal.sunbase.Model.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interface for managing customer-related operations.
 */
public interface CustomerService {

    /**
     * Creates a new customer in the system.
     *
     * @param customerDto - DTO containing customer details for creation
     * @param sync - Boolean indicating whether to synchronize with existing customer by email
     * @return - The created customer object
     * @throws Exception - Thrown if an error occurs during the operation
     */
    Customer createCustomer(CustomerDto customerDto, boolean sync) throws Exception;

    /**
     * Updates an existing customer with the provided details.
     *
     * @param id - ID of the customer to be updated
     * @param updateCustomerDto - DTO containing updated customer details
     * @return - The updated customer object
     * @throws Exception - Thrown if an error occurs during the operation
     */
    Customer updateCustomer(Long id, CustomerDto updateCustomerDto) throws Exception;

    /**
     * Searches for customers based on a specific type (e.g., firstName, city, email, phone).
     *
     * @param searchBy - Type of search (firstName, city, email, phone)
     * @param searchQuery - Search query string
     * @return - List of CustomerResponseDto objects matching the search criteria
     */
    List<CustomerResponseDto> searchBySpecificType(String searchBy, String searchQuery);

    /**
     * Retrieves all customers from the database, paginated and sorted.
     *
     * @param pageNo - Page number
     * @param pageSize - Number of customers per page
     * @param sortBy - Field to sort the customers by
     * @return - A page of CustomerResponseDto objects
     */
    Page<CustomerResponseDto> getAllCustomers(int pageNo, int pageSize, String sortBy);

    /**
     * Retrieves a customer by their ID.
     *
     * @param id - ID of the customer to retrieve
     * @return - Customer object with the specified ID
     * @throws Exception - Thrown if the customer with the given ID is not found
     */
    Customer getCustomerById(Long id) throws Exception;

    /**
     * Deletes a customer by their ID.
     *
     * @param id - ID of the customer to delete
     * @throws Exception - Thrown if an error occurs during the deletion process
     */
    void deleteCustomer(Long id) throws Exception;
}