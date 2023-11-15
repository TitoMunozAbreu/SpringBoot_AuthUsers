package com.example.authusers.controllers;

import com.example.authusers.models.Role;
import com.example.authusers.models.UserLoginRequest;
import com.example.authusers.models.UserRequest;
import com.example.authusers.services.RoleService;
import com.example.authusers.services.RoleServiceImpl;
import com.example.authusers.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/auth-user")
public class UserController {

    private UserServiceImpl userService;
    private RoleServiceImpl roleService;

    public UserController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("userLogin", new UserLoginRequest());
        return "login";

    }

    @PostMapping("/login")
    public String checkUser(@ModelAttribute("userLogin")
                            @Valid UserLoginRequest userLoginRequest,
                            BindingResult result,
                            RedirectAttributes attributes){
        //comprobar si existen errores de validacion
        if(result.hasErrors()){
            return "login";
        }
        //validar usuario
        boolean isUser = this.userService.existsUserByEmailAndAndPassword(userLoginRequest);

        if(isUser){
            attributes.addFlashAttribute("success", "User authenticated");
            String roleName = this.userService.findUserRoleByEmail(userLoginRequest.getEmail());
            return "redirect:/auth-user/" + roleName + "";
        }else {
            attributes.addFlashAttribute("danger", "Invalid user");
            return "redirect:/auth-user/login";
        }
    }

    @GetMapping("/register")
    public String register(Model model){
        List<Role> roles = this.roleService.findAll();
        //pasar a la vista el userRequest
        model.addAttribute("userRequest", new UserRequest());
        //pasar lista de roles
        model.addAttribute("availableRoles", roles);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userRequest")
                               @Valid UserRequest userRequest,
                               BindingResult result,
                               RedirectAttributes attributes){
        //comprobar si existen errores de validacion
        if(result.hasErrors()){
            return "register";
        }
        //registrar alumno en la BBDD
        boolean isUserCreate = this.userService.save(userRequest);

        if(isUserCreate){
            attributes.addFlashAttribute("success", "User created");
            return "redirect:/auth-user/";
        }else {
            attributes.addFlashAttribute("danger", "¡User mail already exist!");
            return "redirect:/auth-user/register";
        }
    }

    @GetMapping("/role-admin")
    public String getUsersConfig(Model model){
        model.addAttribute("users", this.userService.findAllUsers());
        return "users-config";
    }

}
