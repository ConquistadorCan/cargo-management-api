package com.mfc.logistics.cargo_management_api;

import com.mfc.logistics.cargo_management_api.enums.UserRoleEnum;
import com.mfc.logistics.cargo_management_api.models.User;
import com.mfc.logistics.cargo_management_api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindById() {
        User u1 = new User("JohnDoe", "john.doe@example.com", "password123", UserRoleEnum.ADMIN);
        userRepository.save(u1);

        User user = userRepository.findById(u1.getId()).orElse(null);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("JohnDoe", user.getUsername());
        Assertions.assertEquals("john.doe@example.com", user.getEmail());
        Assertions.assertEquals("password123", user.getPassword());
        Assertions.assertEquals(UserRoleEnum.ADMIN, user.getRole());
    }

    @Test
    public void testUpdate() {
        User u1 = new User("JaneDoe", "jane.doe@example.com", "pass123", UserRoleEnum.CUSTOMER);
        userRepository.save(u1);

        u1.setEmail("new.email@example.com");
        u1.setPassword("newpass456");
        userRepository.save(u1);

        User updatedUser = userRepository.findById(u1.getId()).orElse(null);
        Assertions.assertEquals("new.email@example.com", updatedUser.getEmail());
        Assertions.assertEquals("newpass456", updatedUser.getPassword());
    }

    @Test
    public void testDelete() {
        User u1 = new User("DeleteMe", "delete@example.com", "delete123", UserRoleEnum.CUSTOMER);
        userRepository.save(u1);

        Long id = u1.getId();
        userRepository.delete(u1);

        Assertions.assertTrue(userRepository.findById(id).isEmpty());
    }

    @Test
    public void testFindAll() {
        userRepository.save(new User("User1", "user1@example.com", "123", UserRoleEnum.CUSTOMER));
        userRepository.save(new User("User2", "user2@example.com", "456", UserRoleEnum.ADMIN));

        List<User> users = userRepository.findAll();
        Assertions.assertEquals(2, users.size());
    }

    @Test
    public void testUniqueUsername() {
        User u1 = new User("JohnDoe", "john.doe@example.com", "password123", UserRoleEnum.ADMIN);
        userRepository.save(u1);

        User u2 = new User("JohnDoe", "doe@example.com", "password123", UserRoleEnum.ADMIN);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.saveAndFlush(u2);
        });
    }

    @Test
    public void testUniqueEmail() {
        User u1 = new User("John", "john.doe@example.com", "password123", UserRoleEnum.ADMIN);
        userRepository.save(u1);

        User u2 = new User("JohnDoe", "john.doe@example.com", "password123", UserRoleEnum.ADMIN);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.saveAndFlush(u2);
        });
    }

}
