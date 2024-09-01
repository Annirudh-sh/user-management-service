package com.example.repository;

import com.example.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.opentracing.Traced;

import java.util.Optional;

@ApplicationScoped
@Traced
public class UserRepository implements PanacheRepository<User> {

    public Optional<User> findByUserName(String username) {
        return find("username", username).firstResultOptional();
    }
}
