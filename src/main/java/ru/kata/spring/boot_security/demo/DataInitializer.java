package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements ApplicationRunner {

    private UserService userService;
    private RoleDao roleDao;

    @Autowired
    public DataInitializer(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");

        roleDao.save(userRole);
        roleDao.save(adminRole);

        if (userService.findByUsername("user") == null) {
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            User user = new User("user", "user",
                    "Иван", "Иванович", "Москва",
                    21, "ivan@mail.ru", userRoles);
            userService.saveUser(user);
        }

        if (userService.findByUsername("admin") == null) {
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminRoles.add(userRole);
            User admin = new User("admin", "admin",
                    "Андрей", "Андреевич", "Санкт-Петербург",
                    21, "andrey@mail.ru", adminRoles);
            userService.saveUser(admin);
        }

    }
}
