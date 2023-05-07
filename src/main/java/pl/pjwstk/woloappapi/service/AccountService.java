package pl.pjwstk.woloappapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.database.Account;
import pl.pjwstk.woloappapi.repository.AccountRepository;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository repository;


    public Account findById(Long id){
        return repository.findOne(id);
    }

    public List<Account> getAll(){
        return repository.findAll();
    }

    public void create(Account account){
        repository.save(account);
    }

    public void update(Account updatedAccount){
        Account accountToBeUpdated = repository.findOne(updatedAccount.getId());
        accountToBeUpdated.setName(updatedAccount.getName());
        accountToBeUpdated.setEmail(updatedAccount.getEmail());

    }
    public void delete(Long id){
        repository.delete(id);
    }


}

