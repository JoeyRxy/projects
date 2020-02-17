package mine.learn.vue.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mine.learn.vue.demo.entity.Users;
import mine.learn.vue.demo.repository.UserRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * UsersHandler
 */
@RestController
@RequestMapping("/users")
public class UsersHandler {

    @Autowired
    private UserRepo repo;

    @GetMapping(value = "/all")
    public List<Users> findAll() {
        return repo.findAll();
    }

}