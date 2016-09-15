package functionalRequirement24_25_27;


import java.util.Collection;

import javax.transaction.Transactional;

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
import org.springframework.util.Assert;

import security.LoginService;
import services.CommentService;
import utilities.PopulateDatabase;
import domain.Comment;


@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class NavegateChidrenParentComment {
	
	//Navigate to the children of a comment, if any, and to its parent, if any.

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	@Autowired
	private CommentService commentService;

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
	public void setUp() {
		PopulateDatabase.main(null);
	}

	// Positive Test
	//See children comments
	@Test
	public void seeInitialComment() {
		authenticate("customer1");
		Collection<Comment> comments = commentService.findChildren(29);
		Assert.isTrue(comments.size()==1);
		
		Comment comment = commentService.findOne(30);
		
		Assert.isTrue(comments.contains(comment));
	}
	
	
	//See parent comment
	@Test
	public void seeParentComment() {
		authenticate("customer1");
		
		Comment comment = commentService.findOne(30);
		Comment Parent = commentService.findOne(29);
		
		Assert.isTrue(comment.getParent().equals(Parent));
	}

}
