package org.korcula.repository;

import org.korcula.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	//find Product based of Customer id
	@Query(value="SELECT p.id, p.product_name, p.quantity, p.price FROM Product p JOIN Customer c ON p.customer_id = c.id WHERE c.id=:customerId;", nativeQuery = true)
	Product findProductByCustomerId(@Param("customerId") Integer customerId);
}
