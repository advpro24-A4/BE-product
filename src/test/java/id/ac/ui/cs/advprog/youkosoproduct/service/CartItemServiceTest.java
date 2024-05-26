package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.dto.NotificationDTO;
import id.ac.ui.cs.advprog.youkosoproduct.exception.BadRequestException;
import id.ac.ui.cs.advprog.youkosoproduct.exception.NotFoundException;
import id.ac.ui.cs.advprog.youkosoproduct.model.*;
import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.CartItemBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.ProductBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.model.enumaration.NotificationType;
import id.ac.ui.cs.advprog.youkosoproduct.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartItemServiceTest {

    @Mock
    private ICartItemRepository cartItemRepository;
    @Mock
    private IProductRepository productRepository;
    @Mock
    private KafkaService kafkaService;

    @Mock
    private ICartRepository cartRepository;

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrderItemRepository orderItemRepository;

    private CartItemServiceImpl cartItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItemService = new CartItemServiceImpl(cartItemRepository, cartRepository, productRepository, orderRepository, orderItemRepository, kafkaService);
    }

    @Test
    void addProductToCartItem_ProductNotFound_ThrowBadRequestException() {
        String userId = "user123";
        int productId = 1;
        int quantity = 5;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> cartItemService.addProductToCartItem(userId, productId, quantity));
    }

    @Test
    void addProductToCartItem_ProductOutOfStock_ThrowBadRequestException() {
        String userId = "user123";
        int productId = 1;
        int quantity = 5;
        Product product = new ProductBuilder()
                .productId(productId)
                .productName("Product 1")
                .productPrice(100)
                .productStock(0)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(BadRequestException.class, () -> cartItemService.addProductToCartItem(userId, productId, quantity));
    }

    @Test
    void addProductToCartItem_QuantityExceedsStock_ThrowBadRequestException() {
        String userId = "user123";
        int productId = 1;
        int quantity = 10;
        Product product = new ProductBuilder()
                .productId(productId)
                .productName("Product 1")
                .productPrice(100)
                .productStock(5)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(BadRequestException.class, () -> cartItemService.addProductToCartItem(userId, productId, quantity));
    }

    @Test
    void addProductToCartItem_CartItemDoesNotExist_CreateNewCartItem() {
        String userId = "user123";
        int productId = 1;
        int quantity = 2;
        Product product = new ProductBuilder()
                .productId(productId)
                .productName("Product 1")
                .productPrice(100)
                .productStock(5)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartItemRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.empty());
        when(cartItemRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CartItem result = cartItemService.addProductToCartItem(userId, productId, quantity);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(productId, result.getProduct().getId());
        assertEquals(quantity, result.getQuantity());
        assertEquals(product.getProductPrice() * quantity, result.getPrice());
    }

    @Test
    void addProductToCartItem_CartItemExists_UpdateCartItem() {
        String userId = "user123";
        int productId = 1;
        int quantity = 2;
        int existingQuantity = 3;
        int existingPrice = 300;
        Product product = new ProductBuilder()
                .productId(productId)
                .productName("Product 1")
                .productPrice(100)
                .productStock(5)
                .build();
        CartItem existingCartItem = new CartItemBuilder()
                .user(userId)
                .product(product)
                .quantity(existingQuantity)
                .price(existingPrice)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartItemRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.of(existingCartItem));
        when(cartItemRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CartItem result = cartItemService.addProductToCartItem(userId, productId, quantity);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(productId, result.getProduct().getId());
        assertEquals(existingQuantity + quantity, result.getQuantity());
        assertEquals(existingPrice + product.getProductPrice() * quantity, result.getPrice());
    }

    @Test
    void findByUserId_ReturnCartItems() {
        String userId = "user123";
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem());
        cartItems.add(new CartItem());

        when(cartItemRepository.findByUserId(userId)).thenReturn(cartItems);
        List<CartItem> result = cartItemService.findByUserId(userId);

        assertNotNull(result);
        assertEquals(cartItems.size(), result.size());
    }

    @Test
    void checkout_CartIsEmpty_ThrowBadRequestException() {
        String userId = "user123";
        String address = "Address";
        String recipientName = "Recipient";
        String recipientPhone = "Phone";


        when(cartItemRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                cartItemService.checkout(userId, address, recipientName, recipientPhone)
        );
        assertEquals("Cart is empty", exception.getMessage());

        verify(cartItemRepository).findByUserId(userId);
    }


    @Test
    void removeProductFromCartItem_CartItemFound_QuantityGreaterThanOne_UpdateQuantityAndPrice() {
        String userId = "user123";
        int productId = 1;
        int initialQuantity = 3;
        int initialPrice = 300;
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(initialQuantity);
        cartItem.setPrice(initialPrice);
        Product product = new Product();
        product.setProductDiscount(5);
        cartItem.setProduct(product);

        when(cartItemRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CartItem result = cartItemService.removeProductFromCartItem(userId, productId);

        assertNotNull(result);
        assertEquals(initialQuantity - 1, result.getQuantity());
        assertEquals(initialPrice - product.finalPrice(), result.getPrice());
    }

    @Test
    void removeProductFromCartItem_CartItemFound_QuantityOne_DeleteCartItem() {
        // Arrange
        String userId = "user123";
        int productId = 1;
        int initialQuantity = 1;
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(initialQuantity);
        Product product = new Product(); // Create a dummy product
        cartItem.setProduct(product); // Set product to cartItem

        when(cartItemRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.of(cartItem));

        CartItem result = cartItemService.removeProductFromCartItem(userId, productId);

        assertNull(result);
        verify(cartItemRepository, times(1)).delete(cartItem);
    }


    @Test
    void removeProductFromCartItem_CartItemFound_QuantityEqualsOne_RemoveItem() {
        String userId = "user123";
        int productId = 1;
        int initialQuantity = 1;
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(initialQuantity);

        when(cartItemRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.of(cartItem));
        doNothing().when(cartItemRepository).delete(cartItem);

        CartItem result = cartItemService.removeProductFromCartItem(userId, productId);

        assertNull(result);
        verify(cartItemRepository, times(1)).delete(cartItem);
    }

    @Test
    void removeProductFromCartItem_CartItemNotFound_ThrowNotFoundException() {
        String userId = "user123";
        int productId = 1;

        when(cartItemRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cartItemService.removeProductFromCartItem(userId, productId));
    }

    @Test
    void checkout_CartItemsAvailable_CreateOrderAndNotify() {
        String userId = "user123";
        String address = "Address";
        String recipientName = "Recipient";
        String recipientPhone = "Phone";
        List<CartItem> cartItems = new ArrayList<>();
        Product product1 = new Product(1);
        product1.setProductName("Product 1");
        product1.setProductStock(10);
        CartItem cartItem1 = new CartItem();
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        cartItem1.setPrice(200);
        cartItems.add(cartItem1);

        ICartItemRepository existingCartItemRepository = mock(ICartItemRepository.class);
        when(existingCartItemRepository.findByUserId(userId)).thenReturn(cartItems);
        IProductRepository existingProductRepository = mock(IProductRepository.class);
        when(existingProductRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        IOrderRepository existingOrderRepository = mock(IOrderRepository.class);
        when(existingOrderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        IOrderItemRepository existingOrderItemRepository = mock(IOrderItemRepository.class);
        KafkaService existingKafkaService = mock(KafkaService.class);

        CartItemServiceImpl existingCartItemService = new CartItemServiceImpl(existingCartItemRepository, cartRepository, existingProductRepository, existingOrderRepository, existingOrderItemRepository, existingKafkaService);
        Order order = existingCartItemService.checkout(userId, address, recipientName, recipientPhone);

        assertNotNull(order);
        assertEquals(userId, order.getUserId());
        assertEquals(address, order.getAlamatPengiriman());
        assertEquals(recipientName, order.getNamaPenerima());
        assertEquals(recipientPhone, order.getNoHpPenerima());
        assertEquals("PENDING", order.getStatus());
        List<OrderItem> orderItems = order.getOrderItems();
        assertNotNull(orderItems);
        assertEquals(cartItems.size(), orderItems.size());
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            OrderItem orderItem = orderItems.get(i);
            assertEquals(order, orderItem.getOrder());
            assertEquals(cartItem.getProduct(), orderItem.getProduct());
            assertEquals(cartItem.getQuantity(), orderItem.getQuantity());
            assertEquals(cartItem.getPrice(), orderItem.getPrice());
        }
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            assertEquals(8, product.getProductStock());
        }
        ArgumentCaptor<NotificationDTO> notificationCaptor = ArgumentCaptor.forClass(NotificationDTO.class);
        verify(existingKafkaService, times(2)).sendNotification(notificationCaptor.capture());
        List<NotificationDTO> notificationsSent = notificationCaptor.getAllValues();
        assertEquals(2, notificationsSent.size());

        NotificationDTO stockNotification = notificationsSent.getFirst();
        assertEquals("Product " + product1.getProductName() + " is running out of stock", stockNotification.getMessage());
        assertEquals(String.valueOf(product1.getId()), stockNotification.getProductId());
        assertEquals(NotificationType.PRODUCT, stockNotification.getType());
        assertEquals(userId, stockNotification.getUserId());

        NotificationDTO orderNotification = notificationsSent.get(1);
        assertEquals("New order " + order.getId() + " has been created", orderNotification.getMessage());
        assertEquals(String.valueOf(order.getId()), orderNotification.getOrderId());
        assertEquals(NotificationType.ORDER, orderNotification.getType());
        assertEquals(userId, orderNotification.getUserId());
    }

    @Test
    void checkout_ProductOutOfStock_ThrowsBadRequestException() {
        String userId = "user123";
        String address = "Address";
        String recipientName = "Recipient";
        String recipientPhone = "Phone";

        List<CartItem> cartItems = new ArrayList<>();
        Product product1 = new Product(1);
        product1.setProductName("Product 1");
        product1.setProductStock(1);
        CartItem cartItem1 = new CartItem();
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        cartItem1.setPrice(200);
        cartItems.add(cartItem1);

        ICartItemRepository cartItemRepository = mock(ICartItemRepository.class);
        when(cartItemRepository.findByUserId(userId)).thenReturn(cartItems);

        IProductRepository productRepository = mock(IProductRepository.class);
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        IOrderRepository orderRepository = mock(IOrderRepository.class);
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        IOrderItemRepository orderItemRepository = mock(IOrderItemRepository.class);

        KafkaService kafkaService = mock(KafkaService.class);

        CartItemServiceImpl cartItemService = new CartItemServiceImpl(cartItemRepository, cartRepository, productRepository, orderRepository, orderItemRepository, kafkaService);

        assertThrows(BadRequestException.class, () -> cartItemService.checkout(userId, address, recipientName, recipientPhone));
    }
}
