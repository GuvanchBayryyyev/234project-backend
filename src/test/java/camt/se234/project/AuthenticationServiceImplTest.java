package camt.se234.project;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import camt.se234.project.dao.UserDao;
import camt.se234.project.dao.UserDaoImpl;
import camt.se234.project.entity.User;
import camt.se234.project.service.AuthenticationServiceImpl;

public class AuthenticationServiceImplTest {
	UserDao userDao;
    AuthenticationServiceImpl authService;
    UserDaoImpl userDaoImpl;
    
    @Before
    public void setup() {
    	userDao = mock(UserDao.class);
    	authService = new AuthenticationServiceImpl();
    	authService.setUserDao(userDao);
    	userDaoImpl = new UserDaoImpl();
    }
    @Test
    public void testAuthenticate() {//using mock object
    	List<User> mockUsers = new ArrayList<User>();
    	mockUsers.add(new User("111", "Admin", "admin", "admin"));
    	when(userDao.getUser("Admin","admin")).thenReturn(new User("111", "Admin", "admin", "admin") );
    	assertThat(authService.authenticate("Admin","admin"),is(new User("111", "Admin", "admin", "admin")));
		assertThat(authService.authenticate("somebody","anybody"),is(nullValue()));//check if this user doesnt exist
		 	
    }

}
