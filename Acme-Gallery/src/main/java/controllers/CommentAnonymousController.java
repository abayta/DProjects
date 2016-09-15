package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Comment;
import domain.Painting;

import services.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentAnonymousController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	@Autowired
	private CommentService commentService;

	// Constructors -----------------------------------------------------------

	public CommentAnonymousController() {
		super();
	}
	
	@RequestMapping(value="/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int commentId){
		ModelAndView res;
		Comment comment;
		Painting painting;
		Collection<Comment> comments;
		
		comment = commentService.findOne(commentId);
		comments = comment.getChildren();
		painting = comment.getPainting();
		
		res = new ModelAndView("painting/details");
		res.addObject("requestURI", "comment/details.do");		
		res.addObject("painting", painting);
		res.addObject("comment", comment);
		res.addObject("comments", comments);
		
		return res;
	}

	// Listing ----------------------------------------------------------------

}
