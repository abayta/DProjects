package functionalRequirement23_28;


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
public class CreateComment {
	
	//Create a new comment regarding a painting.

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
	//To make new comment
	@Test
	public void makeInitialComment() {
		authenticate("customer1");
		
		Comment comment = commentService.create(17, null);
		comment.setText("Test comment");
		
		commentService.save(comment);
		Collection<Comment> comments = commentService.findInitialComments(17);
		Assert.isTrue(comments.size()==1);
	}
	
	@Test
	public void makeAnswerComment() {
		authenticate("customer1");
		
		Comment comment = commentService.create(12, 28);
		comment.setText("Test comment");
		
		commentService.save(comment);
		Collection<Comment> comments = commentService.findChildren(28);
		Assert.isTrue(comments.size()==1);
	}
	
	// Negative tests
	//To create answer comment from a comment of another painting
	@Test(expected = IllegalArgumentException.class)
	public void makeAnswerCommentNegative() {
		authenticate("customer1");
		
		Comment comment = commentService.create(17, 29);
		commentService.save(comment);
		
	}

}
