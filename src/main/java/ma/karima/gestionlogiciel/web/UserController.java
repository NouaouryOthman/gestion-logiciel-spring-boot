package ma.karima.gestionlogiciel.web;

import ma.karima.gestionlogiciel.dao.LienRepository;
import ma.karima.gestionlogiciel.dao.LogicielRepository;
import ma.karima.gestionlogiciel.dao.UserRepository;
import ma.karima.gestionlogiciel.entities.Logiciel;
import ma.karima.gestionlogiciel.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    LogicielRepository logicielRepository;
    @Autowired
    LienRepository lienRepository;

    @GetMapping("/logiciels")
    public String logiciels(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "5") int size) {
        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) auth).getUsername();
        User user = userRepository.findByEmail(email);
        Page<Logiciel> logicielPage = logicielRepository.listeLogiciels(user.getId(), PageRequest.of(page, size));
        model.addAttribute("logiciels", logicielPage.getContent());
        model.addAttribute("pages", new int[logicielPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "logiciels";
    }

    @GetMapping("/liens")
    public String liens(Model model, Long id) {
        model.addAttribute("logiciel", logicielRepository.findById(id).get());
        model.addAttribute("lien", lienRepository.liensLogiciel(id));
        return "liens";
    }

    @GetMapping("/logicielsGratuits")
    public String logicielsGratuits(Model model,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "5") int size,
                                    @RequestParam(name = "motCle", defaultValue = "") String motCle) {
        Page<Logiciel> pageLogiciels = logicielRepository.findByNomContainsAndGratuitIsTrue(motCle, PageRequest.of(page, size));
        model.addAttribute("logiciels", pageLogiciels.getContent());
        model.addAttribute("pages", new int[pageLogiciels.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("motCle", motCle);
        model.addAttribute("size", size);
        return "logicielsGratuits";
    }
}
