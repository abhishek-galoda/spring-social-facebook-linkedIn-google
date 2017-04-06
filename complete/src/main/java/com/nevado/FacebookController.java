package com.nevado;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FacebookController {

	private MyBean myBean;

	 @Autowired 
	 private HttpSession httpSession;
	 
	FacebookController(MyBean myBean) {
		this.myBean = myBean;
	}

	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout() {
		httpSession.invalidate();
		
	}
	
	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
	public String helloFacebook(Model model) {
		ConnectionRepository connectionRepository = myBean.getConnectionRepository();
		Facebook facebook = myBean.getFacebook();

		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return "redirect:/connect/facebook";
		}

		User user = facebook.userOperations().getUserProfile();
		System.out.println(facebook.isAuthorized());
		System.out.println(": " + user.getFirstName() + " : " + user.getLastName() + " : " + user.getEmail());
		model.addAttribute("facebookProfile", user);
		PagedList<Post> feed = facebook.feedOperations().getFeed();
		model.addAttribute("feed", feed);

		return "facebook";
	}

}
