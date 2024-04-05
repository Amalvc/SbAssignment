package com.amal.sunbase.Controller;

import com.amal.sunbase.Dto.Request.CustomerDto;
import com.amal.sunbase.Dto.Response.CustomerResponseDto;
import com.amal.sunbase.Model.Customer;
import com.amal.sunbase.Service.ApiManager;
import com.amal.sunbase.Service.Impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
    Customer controller for handling customer related operations
 **/
@RestController
@RequestMapping("/api/customers")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    ApiManager apiManager;

    /**
     * Endpoint to create a new customer.
     *
     * @param createCustomerDto - DTO containing customer details for creation
     * @param sync - Boolean indicating whether to synchronize with existing customer by email
     * @return - ResponseEntity with success message if customer creation is successful
     * @throws Exception - Thrown if an error occurs during the operation
     */
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDto createCustomerDto, @RequestParam boolean sync)throws Exception {

        customerService.createCustomer(createCustomerDto,sync);
        return ResponseEntity.ok("Customer created successfully");
    }

    /**
     * Endpoint to retrieve all customers, paginated and sorted.
     *
     * @param pageNo - Page number
     * @param pageSize - Number of customers per page
     * @param sortBy - Field to sort the customers by
     * @return - ResponseEntity containing a page of CustomerResponseDto objects
     */
    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<CustomerResponseDto> customersPage = customerService.getAllCustomers(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(customersPage);
    }

    /**
     * Endpoint to update an existing customer.
     *
     * @param id - ID of the customer to be updated
     * @param updateCustomerDto - DTO containing updated customer details
     * @return - ResponseEntity containing the updated customer object
     * @throws Exception - Thrown if an error occurs during the operation
     */
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDto updateCustomerDto) throws Exception {
        Customer response=customerService.updateCustomer(id, updateCustomerDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to search for customers based on a specific type (e.g., firstName, city, email, phone).
     *
     * @param searchBy - Type of search (firstName, city, email, phone)
     * @param searchQuery - Search query string
     * @return - ResponseEntity containing a list of CustomerResponseDto objects matching the search criteria
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchCustomers(@RequestParam String searchBy, @RequestParam String searchQuery) {
        List<CustomerResponseDto> response=customerService.searchBySpecificType(searchBy, searchQuery);
        return ResponseEntity.ok(response);
    }


    /**
     * Endpoint to retrieve a customer by their ID.
     *
     * @param id - ID of the customer to retrieve
     * @return - ResponseEntity containing the customer object with the specified ID
     * @throws Exception - Thrown if the customer with the given ID is not found
     */
    @GetMapping("getCustomer/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) throws Exception {
        Customer response= customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to delete a customer by their ID.
     *
     * @param id - ID of the customer to delete
     * @return - ResponseEntity with success message if deletion is successful
     * @throws Exception - Thrown if an error occurs during the deletion process
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) throws Exception {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @GetMapping("/sync")
    public ResponseEntity<Object[]> getTokenFromApi(){
        Object[]   customerObject =apiManager.getTokenAndCustomers();
        return new ResponseEntity<>(customerObject, HttpStatus.ACCEPTED);
    }
}
