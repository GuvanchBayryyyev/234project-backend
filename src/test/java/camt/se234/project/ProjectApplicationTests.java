package camt.se234.project;


import camt.se234.project.dao.OrderDao;
import camt.se234.project.dao.OrderDaoImpl;
import camt.se234.project.dao.ProductDao;
import camt.se234.project.dao.ProductDaoImpl;
import camt.se234.project.dao.UserDao;
import camt.se234.project.dao.UserDaoImpl;
import camt.se234.project.entity.Product;
import camt.se234.project.entity.SaleOrder;
import camt.se234.project.entity.SaleTransaction;
import camt.se234.project.entity.User;
import camt.se234.project.repository.ProductRepository;
import camt.se234.project.service.AuthenticationServiceImpl;
import camt.se234.project.service.ProductServiceImpl;
import camt.se234.project.service.SaleOrderService;
import camt.se234.project.service.SaleOrderServiceImpl;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    ProductRepository productRepository;
    @Autowired
    SaleOrderService saleOrderService;
    @Test
    public void testAddTransaction(){
        SaleOrder order = SaleOrder.builder()
                .saleOrderId("O001")
                .build();
        Product p1 = productRepository.findByProductId("p0001");
        Product p2 = productRepository.findByProductId("p0002");
        List<SaleTransaction> transactions = new ArrayList<>();
        transactions.add(SaleTransaction.builder().transactionId("t0001")
                .product(p1)
                .amount(10)
                .build());
        transactions.add(SaleTransaction.builder().transactionId("t0002")
                .product(p2)
                .amount(12)
                .build());
        order.setTransactions(transactions);
        SaleOrder result = saleOrderService.addSaleOrder(order);
        assertThat(result.getId(),is(notNullValue()));

    }
    UserDao userDao;
    AuthenticationServiceImpl authService;
    UserDaoImpl userDaoImpl;
    
    ProductDao proDao;
    ProductServiceImpl proService;
    ProductDaoImpl proDaoImpl;
    
    OrderDao orderDao;
    SaleOrderServiceImpl orderService;
    OrderDaoImpl orderImpl;
	
    @Before
	public void setup() {
    	userDao = mock(UserDao.class);
    	authService = new AuthenticationServiceImpl();
    	authService.setUserDao(userDao);
    	userDaoImpl = new UserDaoImpl();
    	
    	proDao = mock(ProductDao.class);
    	proService = new ProductServiceImpl();
    	proService.setProductDao(proDao);
    	proDaoImpl = new ProductDaoImpl();
    	
    	orderDao = mock(OrderDao.class);
    	orderService = new SaleOrderServiceImpl();
    	orderService.setOrderDao(orderDao);
    	orderImpl = new OrderDaoImpl();
	}
    
    @Test
    public void testAuthenticate() {//using mock object
    	List<User> mockUsers = new ArrayList<User>();
    	mockUsers.add(new User("111", "Admin", "admin", "admin"));
    	when(userDao.getUser("Admin","admin")).thenReturn(new User("111", "Admin", "admin", "admin") );
    	assertThat(authService.authenticate("Admin","admin"),is(new User("111", "Admin", "admin", "admin")));
		assertThat(authService.authenticate("somebody","anybody"),is(nullValue()));//check if this user doesnt exist
		 	
    }
    @Test
    public void testGetAllProducts() {//using mock object
    	List<Product> mockPros = new ArrayList<Product>();
    	mockPros.add(new Product("000","123","apple","fresh apple","somehere",20.0));
    	mockPros.add(new Product("001","124","orange","fresh orange","somehere",25.0));
    	when(proDao.getProducts()).thenReturn(mockPros);
    	assertThat(proService.getAllProducts(),is(mockPros));
    }
    @Test
    public void testGetAvailProds() {//using mock object
    	List<Product> mockPros2 = new ArrayList<>();
    	mockPros2.add(new Product("000","123","apple","fresh apple","somehere",20.0));
    	mockPros2.add(new Product("001","124","orange","fresh orange","somehere",0.0));
    	mockPros2.add(new Product("002","125","grape","fresh grape","somehere",30.0));

    	when(proDao.getProducts()).thenReturn(mockPros2);
    	assertThat(proService.getAvailableProducts(),hasItems(new Product("000","123","apple","fresh apple","somehere",20.0),
    			new Product("002","125","grape","fresh grape","somehere",30.0)));
      }
    @Test
    public void testGetUnavailProdSize() {//using mock object
    	List<Product> mockPros3 = new ArrayList<>();
    	mockPros3.add(new Product("000","123","apple","fresh apple","somehere",20.0));
    	mockPros3.add(new Product("001","124","orange","fresh orange","somehere",0.0));
    	mockPros3.add(new Product("002","125","grape","fresh grape","somehere",30.0));
    	when(proDao.getProducts()).thenReturn(mockPros3);
    	
    	assertThat(proService.getUnavailableProductSize(),is(1));// return 3-2
    }
    @Test
    public void testGetSaleOrders() {
            List<SaleTransaction> mockTransactions1 = new ArrayList<>();
            List<SaleTransaction> mockTransactions2 = new ArrayList<>();
            mockTransactions1.add(new SaleTransaction("trans1", "transId1", new SaleOrder("saleOrder1","saleOrderId1", mockTransactions1),
                    new Product("000","123","apple","fresh apple","somehere",20.0), 5));
            mockTransactions2.add(new SaleTransaction("trans2", "transId2", new SaleOrder("saleOrder2","saleOrderId2", mockTransactions2),
                    new Product("001","124","orange","fresh orange","somehere",0.0), 2));
            List<SaleOrder> mockOrders = new ArrayList<SaleOrder>();
            mockOrders.add(new SaleOrder("saleOrder1","saleOrderId1", mockTransactions1));
            mockOrders.add(new SaleOrder("saleOrder2","saleOrderId2", mockTransactions2));
            when(orderDao.getOrders()).thenReturn(mockOrders);
            assertThat(saleOrderService.getSaleOrders(), hasItems(new SaleOrder("saleOrder1","saleOrderId1", mockTransactions1),
            		new SaleOrder("saleOrder2","saleOrderId2", mockTransactions2)));
       
    }
    
	
	
}
//@Test
//public void testGetSaleOrders() {
//    List<SaleTransaction> mockTransactions1 = new ArrayList<>();
//    List<SaleTransaction> mockTransactions2 = new ArrayList<>();
//    mockTransactions1.add(new SaleTransaction("transaction1", new SaleOrder("saleOrder1", mockTransactions1),
//            new Product("1", "apple", "Kind of fruit", "location",35.0), 5));
//    mockTransactions2.add(new SaleTransaction("transaction2", new SaleOrder("saleOrder2", mockTransactions2),
//            new Product("2", "yogurt", "milk product", "location", 12.0), 2));
//    List<SaleOrder> mockOrders = new ArrayList<>();
//    mockOrders.add(new SaleOrder("saleOrder1", mockTransactions1));
//    mockOrders.add(new SaleOrder("saleOrder2", mockTransactions2));
//    when(orderDao.getOrders()).thenReturn(mockOrders);
//    assertThat(saleOrderService.getSaleOrders(), hasItems(new SaleOrder("saleOrder1", mockTransactions1),
//            new SaleOrder("saleOrder2", mockTransactions2)));
//}
