package ru.urfu.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.movies.entity.Actor;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findByMovieCreatedBy(String createdBy);
}