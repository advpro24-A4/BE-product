package id.ac.ui.cs.advprog.youkosoproduct.repository;

import id.ac.ui.cs.advprog.youkosoproduct.model.Customer;
import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartItemRepositoryTest {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testSaveItem() {
        int productId = 99;
        int customerId = 1;

        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (productOptional.isPresent() && customerOptional.isPresent()) {
            Product product = productOptional.get();
            Customer customer = customerOptional.get();

            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setCustomer(customer);
            newItem.setQuantity(1);

            CartItem savedItem = cartItemRepository.save(newItem);
            assertThat(savedItem.getId()).isGreaterThan(0);
        }
    }

    @Test
    void testSaveMoreThanOneItem() {
        int productId = 102;
        int customerId = 2;

        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (productOptional.isPresent() && customerOptional.isPresent()) {
            Product product = productOptional.get();
            Customer customer = customerOptional.get();

            CartItem newItem1 = new CartItem();
            newItem1.setProduct(product);
            newItem1.setCustomer(customer);
            newItem1.setQuantity(2);

            CartItem newItem2 = new CartItem();
            newItem2.setCustomer(new Customer(customerId));
            newItem2.setProduct(new Product(99));
            newItem2.setQuantity(4);

            Iterable<CartItem> cartItemIterable = cartItemRepository.saveAll(List.of(newItem1, newItem2));

            long itemCount = StreamSupport.stream(cartItemIterable.spliterator(), false).count();
            assertThat(itemCount).isGreaterThan(1);
        }
    }

    @Test
    void testFindByCustomer() {
        int customerId = 2;
        List<CartItem> listItems = cartItemRepository.findByCustomer(new Customer(customerId));

        assertThat(listItems.size()).isEqualTo(2);
    }

    @Test
    void testFindByCustomerProduct() {
        int productId = 99;
        int customerId = 1;

        CartItem item = cartItemRepository.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
        assertThat(item).isNotNull();
    }

    @Test
    void testUpdateQuantity() {
        int productId = 102;
        int customerId = 2;
        int quantity = 4;

        cartItemRepository.updateQuantity(quantity, customerId, productId);
        CartItem item = cartItemRepository.findByCustomerAndProduct(new Customer(customerId), new Product(productId));

        assertThat(item.getQuantity()).isEqualTo(4);
    }


    @Test
    void deleteByProduct() {
        int productId = 99;
        int customerId = 1;

        cartItemRepository.deleteByCustomerAndProduct(customerId, productId);
        CartItem item = cartItemRepository.findByCustomerAndProduct(new Customer(customerId), new Product(productId));

        assertThat(item).isNull();
    }

    @AfterEach
    void tearDown() {
        cartItemRepository.deleteAll();
    }

}
