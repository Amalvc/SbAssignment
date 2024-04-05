package com.amal.sunbase.Transformer;

import com.amal.sunbase.Dto.Response.CustomerResponseDto;
import com.amal.sunbase.Model.Customer;

public class CustomerTransformer {
    public static CustomerResponseDto CustomerResponse(Customer customer) {
        return CustomerResponseDto.builder()
                .uuid(customer.getUuid())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .state(customer.getState())
                .address(customer.getAddress())
                .city(customer.getCity())
                .build();
    }
}
