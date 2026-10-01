package ru.urfu.movies.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.urfu.movies.entity.Movie;
import ru.urfu.movies.service.MovieService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MovieService movieService;

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("authorities", authentication.getAuthorities());
        }
        return "index";
    }

    @GetMapping("/calc")
    public String calc(Model model) {
        List<Movie> movies = movieService.findAll();

        double totalBoxOffice = movies.stream()
                .mapToDouble(m -> m.getBoxOffice() != null ? m.getBoxOffice() : 0.0)
                .sum();

        double avgBoxOffice = movies.isEmpty() ? 0.0 : totalBoxOffice / movies.size();

        model.addAttribute("moviesCount", movies.size());
        model.addAttribute("totalBoxOffice", totalBoxOffice);
        model.addAttribute("avgBoxOffice", avgBoxOffice);

        return "calc";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}