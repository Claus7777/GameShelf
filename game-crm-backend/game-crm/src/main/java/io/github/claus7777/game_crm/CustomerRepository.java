package io.github.claus7777.game_crm;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Optional<Customer> findByEmail(String email);

    List<Customer> findByNameContainingIgnoreCase(String namePart);
}
