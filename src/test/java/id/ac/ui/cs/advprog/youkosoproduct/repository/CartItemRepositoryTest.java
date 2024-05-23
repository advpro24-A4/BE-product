//package id.ac.ui.cs.advprog.youkosoproduct.repository;
//
//import id.ac.ui.cs.advprog.youkosoproduct.model.Customer;
//import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
//import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.reset;
//import static org.mockito.Mockito.when;
//
//import java.util.List;
//import java.util.stream.StreamSupport;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
//@ExtendWith(MockitoExtension.class)
//public class CartItemRepositoryTest {
//
//    @Mock
//    private CartItemRepository cartItemRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @Test
//    void testSaveItem() {
//        int productId = 99;
//        int customerId = 1;
//
//        CartItem newItem = new CartItem();
//        newItem.setId(1);
//        newItem.setProduct(new Product(productId));
//        newItem.setCustomer(new Customer(customerId));
//        newItem.setQuantity(1);
//
//        when(cartItemRepository.save(newItem)).thenReturn(newItem);
//
//        CartItem savedItem = cartItemRepository.save(newItem);
//        assertThat(savedItem.getId()).isGreaterThan(0);
//    }
//
//    @Test
//    void testSaveMoreThanOneItem() {
//        int productId = 102;
//        int customerId = 2;
//
//        CartItem newItem1 = new CartItem();
//        newItem1.setProduct(new Product(productId));
//        newItem1.setCustomer(new Customer(customerId));
//        newItem1.setQuantity(2);
//
//        CartItem newItem2 = new CartItem();
//        newItem2.setCustomer(new Customer(customerId));
//        newItem2.setProduct(new Product(99));
//        newItem2.setQuantity(4);
//
//        when(cartItemRepository.saveAll(any())).thenReturn(List.of(newItem1, newItem2));
//
//        Iterable<CartItem> cartItemIterable = cartItemRepository.saveAll(List.of(newItem1, newItem2));
//        long itemCount = StreamSupport.stream(cartItemIterable.spliterator(), false).count();
//        assertThat(itemCount).isGreaterThan(1);
//    }
//
//    @Test
//    void testFindByCustomer() {
//        int customerId = 2;
//
//        when(cartItemRepository.findByCustomer(any())).thenReturn(List.of(new CartItem(), new CartItem()));
//
//        List<CartItem> listItems = cartItemRepository.findByCustomer(new Customer(customerId));
//        assertThat(listItems.size()).isEqualTo(2);
//    }
//
//    @Test
//    void testFindByCustomerProduct() {
//        int productId = 99;
//        int customerId = 1;
//
//        when(cartItemRepository.findByCustomerAndProduct(any(), any())).thenReturn(new CartItem());
//
//        CartItem item = cartItemRepository.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
//        assertThat(item).isNotNull();
//    }
//
//    @Test
//    void testUpdateQuantity() {
//        int productId = 102;
//        int customerId = 2;
//        int quantity = 4;
//
//        CartItem mockedItem = new CartItem();
//        mockedItem.setQuantity(quantity);
//
//        when(cartItemRepository.findByCustomerAndProduct(any(), any())).thenReturn(mockedItem);
//
//        cartItemRepository.updateQuantity(quantity, customerId, productId);
//        CartItem item = cartItemRepository.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
//
//        assertThat(item.getQuantity()).isEqualTo(4);
//    }
//
//    @Test
//    void deleteByProduct() {
//        int productId = 99;
//        int customerId = 1;
//
//        when(cartItemRepository.findByCustomerAndProduct(any(), any())).thenReturn(null);
//
//        cartItemRepository.deleteByCustomerAndProduct(customerId, productId);
//        CartItem item = cartItemRepository.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
//
//        assertThat(item).isNull();
//    }
//
//    @AfterEach
//    void tearDown() {
//        reset(cartItemRepository, productRepository, customerRepository);
//    }
//}
