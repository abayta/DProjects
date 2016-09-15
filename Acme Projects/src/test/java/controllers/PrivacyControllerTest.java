package controllers;  
  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;  
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;  
  
import org.junit.Before;  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.test.context.ContextConfiguration;  
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
import org.springframework.test.context.web.WebAppConfiguration;  
import org.springframework.test.web.servlet.MockMvc;  
import org.springframework.test.web.servlet.setup.MockMvcBuilders;  
import org.springframework.web.context.WebApplicationContext;  

import security.PrivacyController;
  
@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration  
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })

public class PrivacyControllerTest {  
      
    @Autowired  
    private WebApplicationContext wac;  
  
    private MockMvc mockMvc;  
      
    @Before  
    public void init() {  
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  
    }  
     
  //Positive Test
    @Test  
    public void checkLopdlssiPrivacyController() throws Exception {  
        mockMvc.perform(get("/privacy/lopd-lssi"))  
            .andExpect(status().isOk())  
            .andExpect(view().name("privacy/lopd-lssi"))
            .andExpect(forwardedUrl("privacy/lopd-lssi"))
            .andExpect(handler().handlerType(PrivacyController.class))
            .andDo(print());
    }  
    
  //Positive Test
    @Test  
    public void checkcookiesPrivacyController() throws Exception {  
        mockMvc.perform(get("/privacy/cookies"))  
            .andExpect(status().isOk())  
            .andExpect(view().name("privacy/cookies"))
            .andExpect(forwardedUrl("privacy/cookies"))
            .andExpect(handler().handlerType(PrivacyController.class))
            .andDo(print());
    }  
    
}  