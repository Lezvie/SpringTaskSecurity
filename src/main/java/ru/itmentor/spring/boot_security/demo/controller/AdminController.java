package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;
@Controller
public class AdminController {

   private final UserService userService;
   private final RoleService roleService;

   @Autowired
   public AdminController(UserService userService, RoleService roleService) {
      this.userService = userService;
      this.roleService = roleService;
   }

   @GetMapping("/admin")
   public String getAllUser(Model model) {
      model.addAttribute("users", userService.getUserAndRoles());
      return "index";
   }
   @GetMapping("/admin/new")
   public String saveUserForm(Model model) {
      model.addAttribute("roles", roleService.getAllRoles());
      model.addAttribute("user", new User());
      return "new";
   }
   @PostMapping("/admin")
   public String create(@ModelAttribute("user") User user,@RequestParam(value = "nameRoles", required = false) String[] roles) {
      userService.setRoleByUser(user,roles);
      userService.create(user);
      return "redirect:/admin";
   }
   @DeleteMapping("/admin/{id}")
   public String deleteUser(@PathVariable("id") Long id) {
      userService.delete(id);
      return "redirect:/admin";
   }
   @GetMapping("/admin/{id}/edit")
   public String edit(Model model, @PathVariable("id") Long id) {
      model.addAttribute("roles",roleService.getAllRoles());
      model.addAttribute("user", userService.getUserById(id));
      return "edit";
   }
   @PatchMapping("/admin/{id}")
   public String updateUser(@ModelAttribute("user") User user, @RequestParam(value = "nameRoles", required = false) String[] roles) {
      userService.setRoleByUser(user,roles);
      userService.create(user);
      return "redirect:/admin";
   }
   @GetMapping("/admin/{id}")
   public String getUserById(@PathVariable("id") Long id, Model model) {
      model.addAttribute("user", userService.getUserById(id));
      return "show";

   }
}