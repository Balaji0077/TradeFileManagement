package com.mph.TradeFileManagement.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mph.TradeFileManagement.dao.UserRepository;
import com.mph.TradeFileManagement.model.AuthRequest;
import com.mph.TradeFileManagement.model.AuthResponse;

import com.mph.TradeFileManagement.model.User;
import com.mph.TradeFileManagement.service.JwtUtil;
import com.mph.TradeFileManagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins="http://localhost:4200",methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("/user")
public class AuthController {
	// Pre built method for authentication
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary="Sigin as User")
    public ResponseEntity<?> createToken(@io.swagger.v3.oas.annotations.parameters.RequestBody(description="Provide username and password to login.") @RequestBody AuthRequest request) throws Exception {
    	try 
    	{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
         UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
         String jwt = jwtUtil.generateToken(userDetails);
         return ResponseEntity.ok(new AuthResponse(jwt));
    	}
    	catch(BadCredentialsException e) {
    		throw new BadCredentialsException("Username and Password are incorrect!");
    	}
    }
    
    @PostMapping("/register")
    @Operation(summary="Signup as a new User",description="Create a new user by providing username,password and role")
    public ResponseEntity<String> register(@io.swagger.v3.oas.annotations.parameters.RequestBody(description="Provide Username,Password and Role") @RequestBody @Valid  AuthRequest request) {
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
        	return ResponseEntity.badRequest().body("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        String role = (request.getRole()==null||request.getRole().isBlank())?"Role_user":request.getRole();
        user.setRole(role); 
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
    
    @GetMapping("/details/{username}")
    public ResponseEntity<Optional<User>> getUserDetails(@PathVariable String username){
    	 Optional<User> user = userService.getRole(username);
    	 return ResponseEntity.ok(user);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll(){
    	List<User> users= userService.getAllUsers();
    	return ResponseEntity.ok(users);
    }
    
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
    	userService.deleteUserById(id);
    	return "Deleted Successfully";
    }
    
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User userRequest){
    	User user= userService.addUserByAdmin(userRequest);
       return new ResponseEntity<User>(user,HttpStatus.CREATED);
    }
}
