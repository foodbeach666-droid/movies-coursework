package ru.urfu.movies.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.urfu.movies.entity.Movie;
import ru.urfu.movies.repository.MovieRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }
}