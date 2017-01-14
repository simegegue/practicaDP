package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;
import domain.Recipe;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class CommentServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private CommentService commentService;
	
	//Tests ---------------------------------------
	//Positive ------------------------------------
	@Test
	public void testCreate(){
		Comment comment;
		Recipe recipe = new Recipe();
		super.authenticate("user1");
		comment=commentService.create(recipe);
		Assert.notNull(comment);
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		Comment comment;
		super.authenticate("user1");
		comment=commentService.findOne(112);
		String s="comment44";
		comment.setText(s);
		Assert.isTrue(comment.getText().equals(s));
		super.authenticate(null);
	}
	
	@Test
	public void testDelete(){
		Comment comment;
		super.authenticate("user1");
		comment=commentService.findOne(112);
		commentService.delete(comment);
		Collection<Comment>comments=commentService.findAll();
		Assert.isTrue(!comments.contains(comment));
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<Comment>comments;
		comments=commentService.findAll();
		Assert.notNull(comments);
	}
	
	@Test
	public void testFindOne(){
		Comment comment;
		comment=commentService.findOne(112);
		Assert.notNull(comment);
	}
	
	//Negative ----------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		Comment comment;
		super.authenticate("cook");
		comment=commentService.create(null);
		Assert.notNull(comment);
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		Comment comment;
		super.authenticate("nutritionist1");
		comment=commentService.findOne(106);
		String s="";
		comment.setText(s);
		Assert.isTrue(comment.getText().equals(s));
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		Comment comment;
		super.authenticate("user1");
		comment=commentService.findOne(200);
		commentService.delete(comment);
		Collection<Comment>comments=commentService.findAll();
		Assert.isTrue(!comments.contains(comment));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<Comment>comments;
		comments=commentService.findAll();
		comments=null;
		Assert.notNull(comments);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFNindOne(){
		Comment comment;
		comment=commentService.findOne(02);
		Assert.notNull(comment);
	}

}