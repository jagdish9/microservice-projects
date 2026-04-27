package com.application.numerousservice.repository.secondary;

import com.application.numerousservice.entity.secondary.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
