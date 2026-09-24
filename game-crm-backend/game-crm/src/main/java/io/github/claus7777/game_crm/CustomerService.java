package io.github.claus7777.game_crm;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer findById(Long id){
        return customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer does not exist!"));
    }

    public Customer registerCustomer(Customer customer){
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()){
            throw new RuntimeException("Email is already in use!");
        }

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Customer customer){
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()){
            customerRepository.delete(customer);
        }
        else throw new RuntimeException("Customer does not exist!");
    }

    public void deleteCustomerById(Long id){
        Customer customerToDelete = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer does not exist!")
        );
        
        customerRepository.delete(customerToDelete);
    }

    public Game addGameToCustomer(Long customerId, Long gameId, String gameName){
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        if (customer.getFavoriteGames().size() >= 5){
            throw new RuntimeException("Customer already has 5 games");
        }

        Game newGame = new Game();
        newGame.setApiGameId(gameId);
        newGame.setName(gameName);
        customer.addFavoriteGames(newGame);

        customerRepository.save(customer);
        return newGame;
    }

    public void removeGameFromCustomer(Long customerId, Long gameId){
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        for (Game game : customer.getFavoriteGames()) {
            if (game.getId() == gameId){
                customer.getFavoriteGames().remove(game);
                customerRepository.save(customer);
                return;
            }
        }

        throw new RuntimeException("Game with id: " + gameId + " is not one of " + customer.getName() + "favorite games. Nothing to remove.");
    }
    
}
