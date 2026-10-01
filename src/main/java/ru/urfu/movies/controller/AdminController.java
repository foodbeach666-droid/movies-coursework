package ru.urfu.movies.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.urfu.movies.entity.Role;
import ru.urfu.movies.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/roles")
    public String manageRoles(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin_roles";
    }

    @PostMapping("/roles/update")
    public String updateRoles(@RequestParam Long userId,
                              @RequestParam(required = false) List<String> roles) {

        Set<Role> roleSet = new HashSet<>();

        if (roles != null && !roles.isEmpty()) {
            roleSet = roles.stream()
                    .map(Role::valueOf)
                    .collect(Collectors.toCollection(HashSet::new));
        }

        userService.updateUserRoles(userId, roleSet);
        return "redirect:/admin/roles";
    }
}