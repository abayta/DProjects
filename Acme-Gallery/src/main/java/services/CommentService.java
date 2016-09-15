package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Comment;
import domain.Customer;
import domain.Painting;

import repositories.CommentRepository;

@Service
@Transactional
public class CommentService {
	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentRepository commentRepository;

	// Services ---------------------------------------------------------------

	@Autowired
	private PaintingService paintingService;
	@Autowired
	private CustomerService customerService;
	// Constructors -----------------------------------------------------------

	public CommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public Comment create(int paintingId, Integer commentId){
		Comment res;
		Painting painting;
		Customer customer;
		
		painting = paintingService.findOne(paintingId);
		customer = customerService.findByPrincipal();
		res = new Comment();
		
		if(commentId!=null){
			Comment father = findOne(commentId);
			res.setParent(father);
		}
		res.setPainting(painting);
		res.setCustomer(customer);
		res.setCreationMoment(new Date());
		
		return res;
	}
	
	public void save(Comment comment){
		Assert.notNull(comment);
		checkByPrincipal(comment);
		comment.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
		if(comment.getParent()!=null){
			Assert.isTrue(comment.getPainting().equals(comment.getParent().getPainting()));
		}
		commentRepository.save(comment);
	}
	
	public Collection<Comment> findInitialComments(int paintingId){
		Collection<Comment> res;
		res = commentRepository.findInitialComments(paintingId);
		return res;
	}

	public Comment findOne(int commentId) {
		Comment res = commentRepository.findOne(commentId);
		return res;
	}
	
	public Collection<Comment> findWithMoreChildrenComments(){
		Collection<Comment> comments = commentRepository.findWithMoreChildrenComments();
		return comments;
	}
	
	// Ancillary methods -------------------------------------------------------
	
	public void checkByPrincipal(Comment comment){
		Customer customer = customerService.findByPrincipal();
		Assert.isTrue(comment.getCustomer().equals(customer));
	}

	public Collection<Comment> findChildren(int commentId) {
		Collection<Comment> comments = commentRepository.findChildren(commentId);
		return comments;
	}

}
