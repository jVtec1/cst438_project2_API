package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//@RequestMapping("api/Users")
@RestController
public class UserController {
    @GetMapping("/GetUsers")
    public String getAllUser() {return "BOB, MARY, JOE";}

    @GetMapping("/CreateUser")
    public String createUser() {return "USER HAS BEEN CREATED";}

    @GetMapping("/DeleteUser")
    public String deleteUser() {return "USER HAS BEEN DELETED";}
}
//        @GetMapping
//        public List<User> getAllUsers() {
//            return userService.findAll();
//        }
//
//        @GetMapping("/{id}")
//        public User getUser(@PathVariable Long id) {
//            return userService.findById(id);
//        }
//
//        @PostMapping
//        public User createUser(@RequestBody User user) {
//            return userService.save(user);
//        }
//}