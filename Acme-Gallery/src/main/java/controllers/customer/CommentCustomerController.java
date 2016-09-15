package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Auction;
import domain.Comment;
import domain.Painting;

import services.AuctionService;
import services.CommentService;

@Controller
@RequestMapping("/comment/customer")
public class CommentCustomerController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	@Autowired
	private CommentService commentService;
	@Autowired
	private AuctionService auctionService;

	// Constructors -----------------------------------------------------------

	public CommentCustomerController() {
		super();
	}
	
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int paintingId, @RequestParam(required=false) Integer commentId){
		ModelAndView res;
		Comment comment;
		
		try{
			comment = commentService.create(paintingId, commentId);
			res = new ModelAndView("comment/create");
			res.addObject("comment", comment);
		}catch (Throwable oops) {
			res = new ModelAndView("redirect:../../painting/customer/details.do");
			res.addObject("paintingId", paintingId);
		}
		
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Comment comment, BindingResult binding) {
		ModelAndView res;
		
		if (binding.hasErrors()) {
			res = new ModelAndView("comment/create");
			res.addObject("comment", comment);
		} else {
			try {
				commentService.save(comment);
				res = (comment.getParent()==null)?new ModelAndView("redirect:../../painting/customer/details.do?paintingId="+comment.getPainting().getId()):
					new ModelAndView("redirect:details.do?commentId="+comment.getParent().getId());
			} catch (Throwable oops) {
				res = new ModelAndView("comment/create");
				res.addObject("comment", comment);
				res.addObject("message", "comment.commit.error");
			}
		}

		return res;
	}
	
	@RequestMapping(value="/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int commentId){
		ModelAndView res;
		Comment comment;
		Painting painting;
		Collection<Comment> comments;
		Auction auction;
		
		comment = commentService.findOne(commentId);
		comments = commentService.findChildren(commentId);
		painting = comment.getPainting();
		auction = auctionService.findByPaintingFinished(comment.getPainting().getId());
		
		res = new ModelAndView("painting/details");
		res.addObject("painting", painting);
		res.addObject("auction", auction);
		res.addObject("comment", comment);
		res.addObject("comments", comments);
		res.addObject("requestURI", "/comment/customer/details.do");
		
		return res;
	}

	// Listing ----------------------------------------------------------------

}
