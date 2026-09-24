package io.github.claus7777.game_crm;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shoudThrowExceptionWhenEmailAlreadyExists(){
        Customer existingCustomer = new Customer();
        existingCustomer.setEmail("emailduplicado@email.com");

        Mockito.when(customerRepository.findByEmail("emailduplicado@email.com"))
            .thenReturn(Optional.of(existingCustomer));
            
        Customer newCustomer = new Customer();
        newCustomer.setEmail("emailduplicado@email.com");

        assertThrows(RuntimeException.class, () -> {
            customerService.registerCustomer(newCustomer);
        });

        verify(customerRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldSaveCustomerWhenEmailIsUnique(){
        Customer newCustomer = new Customer();
        newCustomer.setName("Bob");
        newCustomer.setEmail("bob@bobmail.com");

        Mockito.when(customerRepository.findByEmail("bob@bobmail.com")).thenReturn(Optional.empty());

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setName("Bob");
        savedCustomer.setEmail("bob@bobmail.com");
        Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        Customer result = customerService.registerCustomer(newCustomer);

        Assertions.assertThat(result.getId()).isEqualTo(1L);
        Assertions.assertThat(result.getEmail()).isEqualTo("bob@bobmail.com");

        verify(customerRepository, times(1)).save(newCustomer);
    }

    @Test
    void shouldDeleteCustomer(){
        Customer newCustomer = new Customer();
        newCustomer.setId(1L);
        newCustomer.setName("João Eduardo");
        newCustomer.setEmail("joao@joaomail.com");
        Mockito.when(customerRepository.findByEmail("joao@joaomail.com")).thenReturn(Optional.of(newCustomer));

        customerService.deleteCustomer(newCustomer);
        verify(customerRepository, times(1)).delete(newCustomer);
    }

    @Test
    void shouldThrowExceptionWhenCustomerDoesNotExist(){
        Customer newCustomer = new Customer();
        newCustomer.setId(1L);
        newCustomer.setEmail("João");
        newCustomer.setEmail("joao@joaomail.com");

        assertThrows(RuntimeException.class, () -> {
            customerService.deleteCustomer(newCustomer);
        });
    }

    @Test
    void shouldAddGameToCustomer(){
        Customer newCustomer = new Customer();
        newCustomer.setId(1L);
        newCustomer.setName("Rafa");
        newCustomer.setEmail("rafa@rafamail.com");

        Mockito.when(customerRepository.findById(newCustomer.getId())).thenReturn(Optional.of(newCustomer));

        customerService.addGameToCustomer(newCustomer.getId(), 1L, "Big Game");

        Assertions.assertThat(newCustomer.getFavoriteGames().size() == 1);
        Assertions.assertThat(newCustomer.getFavoriteGames().get(0).getName() == "Big Game");
        Assertions.assertThat(newCustomer.getFavoriteGames().get(0).getId() == 1L);
    }

    @Test
    void shouldThrowExceptionWhenCustomerAlreadyHasFiveGames(){
        Customer newCustomer = new Customer();
        newCustomer.setId(1L);
        newCustomer.setName("Rafa");
        newCustomer.setEmail("rafa@rafamail.com");

        Mockito.when(customerRepository.findById(newCustomer.getId())).thenReturn(Optional.of(newCustomer));

        customerService.addGameToCustomer(newCustomer.getId(), 1L, "Big Game");
        customerService.addGameToCustomer(newCustomer.getId(), 2L, "Big Game 2");
        customerService.addGameToCustomer(newCustomer.getId(), 3L, "Big Game 3");
        customerService.addGameToCustomer(newCustomer.getId(), 4L, "Big Game 4");
        customerService.addGameToCustomer(newCustomer.getId(), 5L, "Big Game 5");

        assertThrows(RuntimeException.class, () -> {
            customerService.addGameToCustomer(newCustomer.getId(), 6L, "Big Game 6"); 
        });
    }

    @Test
    void shouldRemoveGameFromCustomer(){
        Customer newCustomer = new Customer();
        newCustomer.setId(1L);
        newCustomer.setName("Rafa");
        newCustomer.setEmail("rafa@rafamail.com");

        Mockito.when(customerRepository.findById(newCustomer.getId())).thenReturn(Optional.of(newCustomer));
        customerService.addGameToCustomer(newCustomer.getId(), 1L, "Big Game");

        customerService.removeGameFromCustomer(newCustomer.getId(), 1L);

        Assertions.assertThat(newCustomer.getFavoriteGames().size() == 0);
    }

    @Test
    void shouldThrowExceptionGameIsNotInCustomerList(){
        Customer newCustomer = new Customer();
        newCustomer.setId(1L);
        newCustomer.setName("Rafa");
        newCustomer.setEmail("rafa@rafamail.com");

        Game newGame = new Game();
        newGame.setId(1L);
        newGame.setName("Big Game");

        Mockito.when(customerRepository.findById(newCustomer.getId())).thenReturn(Optional.of(newCustomer));
        
        assertThrows(RuntimeException.class, () -> {
            customerService.removeGameFromCustomer(newCustomer.getId(), newGame.getId());
        });
    }
}
