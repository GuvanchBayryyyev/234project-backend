package camt.se234.project;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import camt.se234.project.dao.ProductDao;
import camt.se234.project.dao.ProductDaoImpl;
import camt.se234.project.entity.Product;
import camt.se234.project.service.ProductServiceImpl;

public class ProductServiceImplTest {
	 ProductDao proDao;
	    ProductServiceImpl proService;
	    ProductDaoImpl proDaoImpl;
	    
	    @Before
		public void setup() {
	    	
	    	proDao = mock(ProductDao.class);
	    	proService = new ProductServiceImpl();
	    	proService.setProductDao(proDao);
	    	proDaoImpl = new ProductDaoImpl();
	
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


}
