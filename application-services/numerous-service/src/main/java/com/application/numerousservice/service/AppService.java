package com.application.numerousservice.service;

import com.application.numerousservice.entity.secondary.Product;
import com.application.numerousservice.entity.primary.User;
import com.application.numerousservice.repository.secondary.ProductRepository;
import com.application.numerousservice.repository.primary.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public AppService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void saveData(String userName, String productName) {
        User user = new User();
        user.setName(userName);
        userRepository.save(user);
        saveProductData(productName);
    }

    @Transactional
    private void saveProductData(String productName) {
        Product product = new Product();
        product.setName(productName);
        productRepository.save(product);
    }
}
