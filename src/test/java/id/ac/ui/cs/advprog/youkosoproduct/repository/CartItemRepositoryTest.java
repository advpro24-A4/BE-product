package id.ac.ui.cs.advprog.youkosoproduct.repository;

import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
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
public class CartItemRepositoryTest {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveItem() {
        int productId = 99;

        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(1);

            CartItem savedItem = cartItemRepository.save(newItem);
            assertThat(savedItem.getId()).isGreaterThan(0);
        }
    }

    @Test
    void testSaveMoreThanOneItem() {
        int productId = 102;

        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            CartItem newItem1 = new CartItem();
            newItem1.setProduct(product);
            newItem1.setQuantity(2);

            CartItem newItem2 = new CartItem();
            newItem2.setProduct(new Product(99));
            newItem2.setQuantity(4);

            Iterable<CartItem> cartItemIterable = cartItemRepository.saveAll(List.of(newItem1, newItem2));

            long itemCount = StreamSupport.stream(cartItemIterable.spliterator(), false).count();
            assertThat(itemCount).isGreaterThan(1);
        }
    }

    @Test
    void testFindProduct() {
        int productId = 102;

        Optional<CartItem> optionalItem = cartItemRepository.findById(productId);
        assertThat(optionalItem).isNotNull();
    }

    @Test
    void testUpdateQuantity() {
        int productId = 102;
        int quantity = 10;

        cartItemRepository.updateQuantity(quantity, productId);
        Optional<CartItem> optionalItem = cartItemRepository.findById(productId);

        optionalItem.ifPresent(cartItem -> assertThat(cartItem.getQuantity()).isEqualTo(quantity));
    }

    @Test
    void deleteByProduct() {
        int productId = 99;

        cartItemRepository.deleteProduct(productId);
        Optional<CartItem> optionalItem = cartItemRepository.findById(productId);
        assertThat(optionalItem).isEmpty();
    }

    @AfterEach
    void tearDown() {
        cartItemRepository.deleteAll();
    }
}
