package org.springRaveen.com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springRaveen.com.Entity.wish;
import org.springRaveen.com.global.GlobalData;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerMovie {

	@RequestMapping("/")
	public String movieHome() {
	GlobalData.wishCart.clear();
	return "index";
	
	
}
	@RequestMapping("/home")
	public String homelogin() {
	return "home";
		
}	
	@GetMapping("/wish")
	public String Wishlist(@RequestParam("id") int id,Model model) {
	//model.addAttribute("id",id);

	System.out.println(id);
	GlobalData.wishCart.add(id);
	/*model.addAttribute("id",id);
		return "wishlist";*/
	
	return "redirect:/home";
}
	@GetMapping("/wishlist")
	public String WishlistPage(Model model) {
	  model.addAttribute("id",GlobalData.wishCart);
		/*model.addAttribute("id",id);
		*/
	return "wishlist";
	
}
	
	@RequestMapping("/movie.html")
	public String welcomePartTwo() {
	return "movie";
}

	
}
