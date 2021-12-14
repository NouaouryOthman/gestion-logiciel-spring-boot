package ma.karima.gestionlogiciel.web;

import ma.karima.gestionlogiciel.dao.LienRepository;
import ma.karima.gestionlogiciel.dao.LogicielRepository;
import ma.karima.gestionlogiciel.dao.UserRepository;
import ma.karima.gestionlogiciel.entities.Lien;
import ma.karima.gestionlogiciel.entities.Logiciel;
import ma.karima.gestionlogiciel.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Date;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LogicielRepository logicielRepository;

    @Autowired
    LienRepository lienRepository;

    @GetMapping("/users")
    public String users(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "motCle", defaultValue = "") String motCle) {
        Page<User> pageUsers = userRepository.findByEmailContains(motCle, PageRequest.of(page, size));
        model.addAttribute("users", pageUsers.getContent());
        model.addAttribute("pages", new int[pageUsers.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("motCle", motCle);
        model.addAttribute("size", size);
        return "users";
    }

    @GetMapping(path = "/supprimerUser")
    public String supprimerUser(Long id, int page, int size, String motCle) {
        userRepository.deleteById(id);
        return "redirect:/users?page=" + page + "&size=" + size + "&motCle" + motCle;
    }

    @GetMapping(path = "/activerUser")
    public String activerUser(Long id, int page, int size, String motCle) {
        User user = userRepository.findById(id).get();
        if (user.isActive())
            user.setActive(false);
        else
            user.setActive(true);
        userRepository.save(user);
        return "redirect:/users?page=" + page + "&size=" + size + "&motCle" + motCle;
    }

    @GetMapping("/ajouterLogiciel")
    public String ajouterLogiciel(Model model,
                                  @RequestParam(name = "id", defaultValue = "") Long id) {
        model.addAttribute("logiciel", new Logiciel());
        model.addAttribute("idClient", id);
        return "formLogiciel";
    }

    @Transactional
    @PostMapping("/saveLogiciel")
    public String saveLogiciel(@Valid Logiciel logiciel, BindingResult bindingResult, Long id) {
        if (bindingResult.hasErrors())
            return "redirect:/ajouterLogiciel?id=" + id;
        User user = userRepository.findById(id).get();
        logiciel.setDateAchat(new Date());
        logiciel.setUser(user);
        Long idLog = logicielRepository.findTopByOrderByIdDesc().getId() + 1;
        logiciel.setId(idLog);
        logicielRepository.save(logiciel);
        Logiciel l = logicielRepository.findTopByOrderByIdDesc();
        return "redirect:/ajouterLiens?id=" + l.getId();
    }

    @GetMapping("/ajouterLiens")
    public String ajouterLiens(Model model,
                               @RequestParam(name = "id", defaultValue = "") Long id) {
        model.addAttribute("lien", new Lien());
        model.addAttribute("id", id);
        return "formLien";
    }

    @Transactional
    @PostMapping("/saveLien")
    public String saveLien(@Valid Lien lien, BindingResult bindingResult, Long id) {
        if (bindingResult.hasErrors())
            return "redirect:/ajouterLiens?id=" + id;
        lien.setLogiciel(logicielRepository.findById(id).get());
        lienRepository.save(lien);
        return "redirect:/users";
    }

    @GetMapping("/ajouterLogicielGratuit")
    public String ajouterLogicielGratuit(Model model) {
        model.addAttribute("logiciel", new Logiciel());
        return "formLogicielGratuit";
    }

    @Transactional
    @PostMapping("/saveLogicielGratuit")
    public String saveLogicielGratuit(@Valid Logiciel logiciel, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formLogicielGratuit";
        Long idLog = logicielRepository.findTopByOrderByIdDesc().getId() + 1;
        logiciel.setId(idLog);
        logiciel.setGratuit(true);
        logicielRepository.save(logiciel);
        Logiciel l = logicielRepository.findTopByOrderByIdDesc();
        return "redirect:/ajouterLiens?id=" + l.getId();
    }
}
