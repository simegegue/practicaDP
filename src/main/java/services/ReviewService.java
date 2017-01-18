package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReviewRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Critic;
import domain.Review;

@Service
@Transactional
public class ReviewService {

	// Managed repository -------------------------------------------
	@Autowired
	private ReviewRepository reviewRepository;
	
	//Supporting services ------------------------------------------
	
	@Autowired
	private CriticService criticService;
	
	// Constructor --------------------------------------------------
	public ReviewService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Review create() {
			
			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Authority au = new Authority();
			au.setAuthority("CRITIC");
			Assert.isTrue(userAccount.getAuthorities().contains(au));
			Date date = new Date(System.currentTimeMillis() - 1000);
			
			Review result;
			result = new Review();
			
			Critic critic;
			critic = criticService.findByPrincipal();
			
			result.setCritic(critic);
			result.setAuthoredMoment(date);
			
			return result;
		}
	
		public Collection<Review> findAll() {
			Collection<Review> result;
	
			result = reviewRepository.findAll();
			Assert.notNull(result);
	
			return result;
		}
	
		public Review findOne(int reviewId) {
			Review result;
	
			result = reviewRepository.findOne(reviewId);
			Assert.notNull(result);
	
			return result;
		}

		public Review save(Review review) {
			
			Assert.notNull(review);
			Review result;
			result = reviewRepository.save(review);
			
			return result;
		}
	
		public void delete(Review review) {
			
			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Authority au = new Authority();
			au.setAuthority("CRITIC");
			Assert.isTrue(userAccount.getAuthorities().contains(au));
	
			reviewRepository.delete(review);
		}
		
		// Other business methods -------------------------------------------------
		public Double getRatio(){
			Double result;
			
			result = reviewRepository.ratioPositiveReview();
			return result;
		}
}