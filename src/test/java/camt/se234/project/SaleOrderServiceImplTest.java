package camt.se234.project;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import camt.se234.project.dao.OrderDao;
import camt.se234.project.dao.OrderDaoImpl;
import camt.se234.project.entity.Product;
import camt.se234.project.entity.SaleOrder;
import camt.se234.project.entity.SaleTransaction;
import camt.se234.project.service.SaleOrderServiceImpl;

public class SaleOrderServiceImplTest {
	OrderDao orderDao;
	SaleOrderServiceImpl orderService;
	OrderDaoImpl orderImpl;

	public void setup() {

		orderDao = mock(OrderDao.class);
		orderService = new SaleOrderServiceImpl();
		orderService.setOrderDao(orderDao);
		orderImpl = new OrderDaoImpl();
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
          assertThat(orderService.getSaleOrders(), hasItems(new SaleOrder("saleOrder1","saleOrderId1", mockTransactions1),
          		new SaleOrder("saleOrder2","saleOrderId2", mockTransactions2)));
     
  }

}
