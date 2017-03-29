package hello;


import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.ProfileOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/linkedin")
public class LinkedInController {

    private LinkedIn linkedIn;
    private ConnectionRepository connectionRepository;

    public LinkedInController(LinkedIn linkedIn, ConnectionRepository connectionRepository) {
        this.linkedIn = linkedIn;
   
        this.connectionRepository = connectionRepository;
    }
    

    @GetMapping
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
            return "redirect:/connect/linkedin";
            
        }
        ProfileOperations user = linkedIn.profileOperations();
        System.out.println(user);
        model.addAttribute("linkedInProfile",linkedIn.profileOperations().getUserProfileFull());
        
    
        
        return "linkedin";
    }

    
}
