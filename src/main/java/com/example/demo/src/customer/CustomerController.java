package com.example.demo.src.customer;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.customer.model.PostCustomerReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller @RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService service;

    @RequestMapping(value = "", method = RequestMethod.POST)
    BaseResponse<?> makeCustomer(PostCustomerReq req){

        return new BaseResponse<>(null);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    BaseResponse<?> getCustomer(@PathVariable Long customerId){

        return new BaseResponse<>(null);
    }
}
