package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Customer;

public interface ShoppingCartService {
    int addProduct(int productId, int quantity, Customer customer);
}
