package io.github.claus7777.game_crm;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import io.github.claus7777.game_crm.Customer;
import io.github.claus7777.game_crm.CustomerRepository;

@DataJpaTest
public class CustomerRepositoryTest {
    
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldFindCustomerByEmail(){
        Customer customer = new Customer();
        customer.setName("João");
        customer.setEmail("email@email.com");
        customerRepository.save(customer);

        Optional<Customer> found = customerRepository.findByEmail("email@email.com");

        Assertions.assertThat(found).isPresent();
        Assertions.assertThat(found.get().getName()).isEqualTo("João");
    }

    @Test
    void shouldFindCustomerbyNameContaining(){
        Customer customer = new Customer();
        customer.setName("João Eduardo");
        customer.setEmail("joao@joaomail.com");
        customerRepository.save(customer);



        List<Customer> found = customerRepository.findByNameContainingIgnoreCase("Edu");

        Assertions.assertThat(found).isNotEmpty();
        Assertions.assertThat(found.get(0).getName()).isEqualTo("João Eduardo");

    }

    @Test 
    void shouldNotFindCustomerNameContaining(){
        Customer newCustomer = new Customer();
        newCustomer.setName("Maria Lucia");
        newCustomer.setEmail("maria@mariamail.com");
        customerRepository.save(newCustomer);

        List<Customer> found = customerRepository.findByNameContainingIgnoreCase("Edu");

        Assertions.assertThat(found.contains(newCustomer)).isFalse();
        Assertions.assertThat(found).isEmpty();
    }

    @Test
    void shouldFindAllCustomers(){
        Customer newCustomer = new Customer();
        newCustomer.setName("Maria Lucia");
        newCustomer.setEmail("maria@mariamail.com");
        customerRepository.save(newCustomer);

        Customer otherCustomer = new Customer();
        otherCustomer.setName("João Eduardod");
        otherCustomer.setEmail("joao@joaomail.com");
        customerRepository.save(otherCustomer);

        List<Customer> found = customerRepository.findAll();
        Assertions.assertThat(found.size() == 2);
        Assertions.assertThat(found.get(0).getName()).isEqualTo("Maria Lucia");
        Assertions.assertThat(found.get(1).getName()).isEqualTo("João Eduardod");
    }



}
