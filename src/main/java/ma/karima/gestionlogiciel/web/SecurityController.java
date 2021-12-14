package ma.karima.gestionlogiciel.web;

import ma.karima.gestionlogiciel.dao.RoleRepository;
import ma.karima.gestionlogiciel.dao.UserRepository;
import ma.karima.gestionlogiciel.entities.Role;
import ma.karima.gestionlogiciel.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@Configuration
@Controller
public class SecurityController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public String index() {
        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) auth).getUsername();
        User user = userRepository.findByEmail(email);
        if (user.isFirstTimeOnline())
            return "redirect:/passwordChange?id=" + user.getId();
        return "redirect:/logiciels";
    }

    @GetMapping("/passwordChange")
    public String change(Model model, Long id) {
        model.addAttribute("id", id);
        model.addAttribute("password", new String());
        return "passwordChange";
    }

    @PostMapping("/password")
    public String password(@ModelAttribute("password") String password, Long id){
        User user = userRepository.findById(id).get();
        PasswordEncoder pe = passwordEncoder();
        user.setPassword(pe.encode(password));
        user.setFirstTimeOnline(false);
        userRepository.save(user);
        return "redirect:/";
    }


    @GetMapping("/registration")
    public String formClient(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("mode", "register");
        return "registration";
    }

    @PostMapping("/saveClient")
    public String saveClient(Model model, @ModelAttribute(value = "user") User user, BindingResult bindingResult, Long id) {
        if (bindingResult.hasErrors())
            return "registration";
        PasswordEncoder pe = passwordEncoder();
        user.setPassword(pe.encode(user.getPassword()));
        Role role = roleRepository.findByName("CLIENT");
        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setFirstTimeOnline(true);
        user.setActive(true);
        userRepository.save(user);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "login";
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/login";
    }

    @GetMapping("/403")
    public String nonAutorise() {
        return "403";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}