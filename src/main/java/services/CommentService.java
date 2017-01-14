package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Nutritionist;
import domain.Recipe;
import domain.User;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NutritionistService nutritionistService;
	
	@Autowired
	private RecipeService recipeService;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public CommentService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Comment create(Recipe recipe) {
		
		UserAccount userAccount;
		Date date = new Date(System.currentTimeMillis() - 1000);
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Authority au1 = new Authority();
		au1.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au1));
		
		Comment result;

		result = new Comment();
		result.setRecipe(recipe);
		
		if(userAccount.getAuthorities().contains(au) ){
			result.setUser(userService.findByPrincipal());
			result.setNutritionist(null);
		}else{
			result.setNutritionist(nutritionistService.findByPrincipal());
			result.setUser(null);
		}
		
		result.setMomentCreate(date);

		return result;
	}

	public Collection<Comment> findAll() {
					
		Collection<Comment> result;

		result = commentRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Comment findOne(int commentId) {
		Comment result;

		result = commentRepository.findOne(commentId);
		Assert.notNull(result);

		return result;
	}

	public Comment save(Comment comment) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Authority au1 = new Authority();
		au1.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au1));
		
		Assert.notNull(comment);
		
		Comment result;
		result = commentRepository.save(comment);
		
		return result;
	}

	public void delete(Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(comment.getId() != 0);
		
		User user= comment.getUser();
		Nutritionist nutri = comment.getNutritionist();
		Collection<Comment> c = comment.getRecipe().getComments();
		Recipe r = comment.getRecipe();
		if(user!=null){
			Collection<Comment> comments = user.getComments();
			comments.remove(comment);
			user.setComments(comments);
			userService.save2(user);
		}else{
			Collection<Comment> comments = nutri.getComments();
			comments.remove(comment);
			nutri.setComments(comments);
			nutritionistService.save2(nutri);
		}
		c.remove(comment);
		r.setComments(c);
		recipeService.save(r);

		commentRepository.delete(comment);
	}
	
	// Other business methods -------------------------------------------------

}
