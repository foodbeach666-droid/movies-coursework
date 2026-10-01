package ru.urfu.movies.config;

import ru.urfu.movies.entity.Actor;
import ru.urfu.movies.entity.Movie;
import ru.urfu.movies.entity.Role;
import ru.urfu.movies.entity.User;
import ru.urfu.movies.repository.ActorRepository;
import ru.urfu.movies.repository.MovieRepository;
import ru.urfu.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(Set.of(Role.ROLE_USER));
            userRepository.save(user);

            User guest = new User();
            guest.setUsername("guest");
            guest.setPassword(passwordEncoder.encode("guest"));
            guest.setRoles(Set.of(Role.ROLE_READ_ONLY));
            userRepository.save(guest);
        }

        if (movieRepository.count() == 0) {
            Movie movie = new Movie();
            movie.setTitle("Интерстеллар");
            movie.setGenre("Фантастика");
            movie.setReleaseYear(2014);
            movie.setBoxOffice(701.8);
            movie.setCreatedBy("admin");
            movieRepository.save(movie);

            Actor actor1 = new Actor();
            actor1.setName("Мэттью Макконахи");
            actor1.setRoleName("Купер");
            actor1.setFee(20.0);
            actor1.setMovie(movie);
            actorRepository.save(actor1);

            Actor actor2 = new Actor();
            actor2.setName("Энн Хэтэуэй");
            actor2.setRoleName("Брэнд");
            actor2.setFee(15.0);
            actor2.setMovie(movie);
            actorRepository.save(actor2);
        }
    }
}