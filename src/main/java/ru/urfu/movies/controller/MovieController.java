package ru.urfu.movies.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.urfu.movies.entity.Actor;
import ru.urfu.movies.entity.Movie;
import ru.urfu.movies.repository.ActorRepository;
import ru.urfu.movies.service.MovieService;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final ActorRepository actorRepository;
    @GetMapping
    public String listMovies(Model model) {
        model.addAttribute("movies", movieService.findAll());
        model.addAttribute("actors", actorRepository.findAll());
        return "movies";
    }
    @GetMapping("/add")
    public String addMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movie-form";
    }
    @PostMapping("/add")
    public String saveMovie(@ModelAttribute Movie movie) {
        movieService.save(movie);
        return "redirect:/movies";
    }
    @GetMapping("/edit/{id}")
    public String editMovieForm(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.findById(id));
        return "movie-form";
    }
    @PostMapping("/edit/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movie) {
        Movie existingMovie = movieService.findById(id);

        existingMovie.setTitle(movie.getTitle());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setReleaseYear(movie.getReleaseYear());
        existingMovie.setBoxOffice(movie.getBoxOffice());
        existingMovie.setCreatedBy(movie.getCreatedBy());
        existingMovie.setDirector(movie.getDirector());

        movieService.save(existingMovie);
        return "redirect:/movies";
    }
    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
        return "redirect:/movies";
    }
    @GetMapping("/actor/add")
    public String addActorForm(Model model) {
        model.addAttribute("actor", new Actor());
        model.addAttribute("movies", movieService.findAll());
        return "actor_form";
    }
    @PostMapping("/actor/add")
    public String saveNewActor(@ModelAttribute Actor actor) {
        actorRepository.save(actor);
        return "redirect:/movies";
    }
    @GetMapping("/actor/edit/{id}")
    public String editActorForm(@PathVariable Long id, Model model) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid actor Id:" + id));
        model.addAttribute("actor", actor);
        model.addAttribute("movies", movieService.findAll());
        return "actor_form";
    }
    @PostMapping("/actor/edit/{id}")
    public String updateActor(@PathVariable Long id, @ModelAttribute Actor actor) {
        actor.setId(id);
        actorRepository.save(actor);
        return "redirect:/movies";
    }
    @GetMapping("/actor/delete/{id}")
    public String deleteActor(@PathVariable Long id) {
        actorRepository.deleteById(id);
        return "redirect:/movies";
    }
}