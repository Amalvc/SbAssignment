package com.amal.sunbase.Service.Impl;

import com.amal.sunbase.Dto.Request.CustomerDto;
import com.amal.sunbase.Dto.Response.CustomerResponseDto;
import com.amal.sunbase.Exception.EmailAlreadyExistException;
import com.amal.sunbase.Exception.PhoneNumberAlreadyExistException;
import com.amal.sunbase.Exception.UserNotFoundException;
import com.amal.sunbase.Model.Customer;
import com.amal.sunbase.Repository.CustomerRepository;
import com.amal.sunbase.Service.Interface.CustomerService;
import com.amal.sunbase.Transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class responsible for customer-related operations.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Creates a new customer in the system.
     * If sync is false, checks if a customer with the provided email already exists.
     * If sync is true and a customer with the provided email exists, updates the existing customer.
     * Throws exceptions if email or phone number already exist.
     *
     * @param createCustomerDto - DTO containing customer details for creation
     * @param sync - Boolean indicating whether to synchronize with existing customer by email
     * @return - The created or updated customer object
     * @throws EmailAlreadyExistException - Thrown if the provided email already exists and sync is false
     * @throws PhoneNumberAlreadyExistException - Thrown if the provided phone number already exists
     */
    @Override
    public Customer createCustomer(CustomerDto createCustomerDto, boolean sync)throws Exception {
        //Fetch customer from database
        Optional<Customer> customerByEmail=customerRepository.findByEmail(createCustomerDto.getEmail());
        //If sync is false and customer is already present then throw email already exist
        if(sync==false && customerByEmail.isPresent()){
            throw new EmailAlreadyExistException();//change
        }

        //If sync is true and email is already present inside database then we update the entry
        if(sync && customerByEmail.isPresent()){
            return updateCustomer(customerByEmail.get().getId(),createCustomerDto);
        }

        //if phone number is already exists then throw phone number already exist exception
        Optional<Customer>customerByPhone=customerRepository.findByPhone(createCustomerDto.getPhone());
        if(customerByPhone.isPresent()){
            throw new PhoneNumberAlreadyExistException();//change
        }

        //Build customer details
        Customer customer=Customer.builder().
                uuid(String.valueOf(UUID.randomUUID())).
                firstName(createCustomerDto.getFirstName()).
                lastName(createCustomerDto.getLastName()).
                city(createCustomerDto.getCity()).
                state(createCustomerDto.getState()).
                address(createCustomerDto.getAddress()).
                email(createCustomerDto.getEmail()).
                phone(createCustomerDto.getPhone()).build();

        return customerRepository.save(customer);
    }

    /**
     * Updates an existing customer with the provided details.
     * Throws exception if customer with given id is not found.
     *
     * @param id - ID of the customer to be updated
     * @param updateCustomerDto - DTO containing updated customer details
     * @return - The updated customer object
     * @throws UserNotFoundException - Thrown if customer with given id is not found
     */
    @Override
    public Customer updateCustomer(Long id, CustomerDto updateCustomerDto)throws Exception {
        //Fetch customer ,if not found throw exception
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isEmpty()){
            throw new UserNotFoundException();
        }

        Customer existingCustomer = customer.get();

        //Entries are optional so user can update any properties according to choice
        if (updateCustomerDto.getEmail() != null) {
            existingCustomer.setEmail(updateCustomerDto.getEmail());
        }
        if (updateCustomerDto.getFirstName() != null) {
            existingCustomer.setFirstName(updateCustomerDto.getFirstName());
        }
        if (updateCustomerDto.getLastName() != null) {
            existingCustomer.setLastName(updateCustomerDto.getLastName());
        }
        if (updateCustomerDto.getAddress() != null) {
            existingCustomer.setAddress(updateCustomerDto.getAddress());
        }
        if (updateCustomerDto.getCity() != null) {
            existingCustomer.setCity(updateCustomerDto.getCity());
        }
        if (updateCustomerDto.getState() != null) {
            existingCustomer.setState(updateCustomerDto.getState());
        }
        if (updateCustomerDto.getPhone() != null) {
            existingCustomer.setPhone(updateCustomerDto.getPhone());
        }

        return customerRepository.save(existingCustomer);

    }

    /**
     * Retrieves all customers from the database, paginated and sorted.
     *
     * @param pageNo - Page number
     * @param pageSize - Number of customers per page
     * @param sortBy - Field to sort the customers by
     * @return - A page of CustomerResponseDto objects
     */
    @Override
    public Page<CustomerResponseDto> getAllCustomers(int pageNo, int pageSize, String sortBy) {
        Pageable pageable;

        //If sortBy is present based on sortBy we create pageable
        if (sortBy != null && !sortBy.isEmpty()) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }

        //Then pass pageable and fetch all customer
        Page<Customer> customersPage = customerRepository.findAll(pageable);

        //Modifying response with helper function
        return customersPage.map(this::convertToDto);
    }

    /**
     * Searches for customers based on a specific type (e.g., firstName, city, email, phone).
     *
     * @param searchBy - Type of search (firstName, city, email, phone)
     * @param searchQuery - Search query string
     * @return - List of CustomerResponseDto objects matching the search criteria
     * @throws IllegalArgumentException - Thrown if an invalid searchBy parameter is provided
     */
    @Override
    public List<CustomerResponseDto> searchBySpecificType(String searchBy, String searchQuery) {
        List<Customer> searchResults;

        //Based on searchBy we choose different case, and fetch data based on query provided
        switch (searchBy) {
            case "firstName":
                searchResults = customerRepository.findByFirstNameContainingIgnoreCase(searchQuery);
                break;
            case "city":
                searchResults = customerRepository.findByCityContainingIgnoreCase(searchQuery);
                break;
            case "email":
                searchResults = customerRepository.findByEmailContainingIgnoreCase(searchQuery);
                break;
            case "phone":
                searchResults = customerRepository.findByPhoneContainingIgnoreCase(searchQuery);
                break;
            default:
                throw new IllegalArgumentException("Invalid searchBy parameter");
        }
        return searchResults.stream()
                .map(CustomerTransformer::CustomerResponse)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Customer entity to a CustomerResponseDto.
     *
     * @param customer - Customer entity to be converted
     * @return - CustomerResponseDto object representing the converted customer
     */
    private CustomerResponseDto convertToDto(Customer customer){
        return CustomerTransformer.CustomerResponse(customer);
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id - ID of the customer to retrieve
     * @return - Customer object with the specified ID
     * @throws UserNotFoundException - Thrown if customer with given ID is not found
     */
    @Override
    public Customer getCustomerById(Long id)throws Exception {
        //Fetch customer using id, if not found throw exception
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isEmpty()){
            throw new UserNotFoundException();
        }
        return customer.get();
    }

    /**
     * Deletes a customer by their ID.
     *
     * @param id - ID of the customer to delete
     * @throws UserNotFoundException - Thrown if customer with given ID is not found
     */
    @Override
    public void deleteCustomer(Long id)throws Exception {
        //Fetch customer using id, if not found throw exception
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isEmpty()){
            throw new UserNotFoundException();
        }

        customerRepository.delete(customer.get());

    }
}
