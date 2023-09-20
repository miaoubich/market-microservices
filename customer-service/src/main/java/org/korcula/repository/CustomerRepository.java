package org.korcula.repository;

import org.korcula.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

//	List<ResponseCustomerAndProductName> getCustomerAndProductName();
}
