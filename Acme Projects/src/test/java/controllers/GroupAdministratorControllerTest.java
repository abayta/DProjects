package controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collection;

import static org.hamcrest.Matchers.*;

import org.junit.Before;  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;  
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
import org.springframework.test.context.web.WebAppConfiguration;  
import org.springframework.test.web.servlet.MockMvc;  
import org.springframework.test.web.servlet.setup.MockMvcBuilders;  

import org.springframework.web.context.WebApplicationContext;  

import controllers.administrator.GroupAdministratorController;

import domain.Group;
import security.LoginService;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;  

@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration  
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })

public class GroupAdministratorControllerTest {  
     
	@Autowired
	private LoginService loginService;
	
    @Autowired  
    private WebApplicationContext wac;  
  
    private MockMvc mockMvc;  
      
 // Authenticate-----------------------------------------------

 	public void authenticate(String username) {
 		UserDetails userDetails;
 		TestingAuthenticationToken authenticationToken;
 		SecurityContext context;

 		userDetails = loginService.loadUserByUsername(username);
 		authenticationToken = new TestingAuthenticationToken(userDetails, null);
 		context = SecurityContextHolder.getContext();
 		context.setAuthentication(authenticationToken);
 	}
    
    @Before  
    public void init() {  
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  
    }  
    
  //Positive Test
    @Test
    public void checkListAvailablePositive () throws Exception {
    	authenticate("admin1");
    	mockMvc.perform(get("/group/administrator/list-available"))
   		.andExpect(status().isOk())
   		.andExpect(view().name("group/list"))
    	.andExpect(model().attributeExists("groups"))
    	.andExpect(model().attributeExists("requestURI"))
    	.andExpect(model().attribute("groups", notNullValue()))
    	.andExpect(model().attribute("groups", instanceOf(Collection.class)))
    	.andExpect(model().attribute("requestURI", instanceOf(String.class)))
    	.andExpect(model().size(2))
    	.andExpect(forwardedUrl("group/list"))
    	.andExpect(handler().handlerType(GroupAdministratorController.class))
    	.andDo(print());
     }
    
    
    //Positive Test
    @Test
    public void checkListDetailsGroupPositive() throws Exception {
    	authenticate("admin1");
    	mockMvc.perform(get("/group/administrator/list-details").param("groupId", "8"))
   		.andExpect(status().isOk())
   		.andExpect(view().name("group/display"))
    	.andExpect(model().attributeExists("group"))
    	.andExpect(model().attribute("group", notNullValue()))
    	.andExpect(model().attribute("group", hasProperty("id", is (8))))
    	.andExpect(model().attribute("group", instanceOf(Group.class)))
    	.andExpect(model().size(1))
    	.andExpect(forwardedUrl("group/display"))
    	.andExpect(handler().handlerType(GroupAdministratorController.class))
    	.andDo(print());
     }
    
  //Negative Test
  //The AssertionError.class exception is thrown because we have entered a 
  //nonexistent "id" for the "groupId" attribute.
    @Test(expected=AssertionError.class)
    public void checkListDetailsGroupNegative() throws Exception {
    	authenticate("admin1");
    	mockMvc.perform(get("/group/administrator/list-details").param("groupId", "45"))
   		.andExpect(status().isOk())
   		.andExpect(view().name("group/display"));//The view name in this case is 'misc/panic'
    }
    
  //Positive Test
    @Test
    public void checkCreateTest() throws Exception {
    	authenticate("admin2");
    	mockMvc.perform(get("/group/administrator/create"))
   		.andExpect(status().isOk())
   		.andExpect(view().name("group/edit"))
   		.andExpect(forwardedUrl("group/edit"))
    	.andExpect(model().attributeExists("group"))
    	.andExpect(model().attribute("group", notNullValue()))
    	.andExpect(model().attribute("group", instanceOf(Group.class)))
    	.andExpect(model().size(2))
        .andExpect(handler().handlerType(GroupAdministratorController.class))
    	.andDo(print());
     }
}