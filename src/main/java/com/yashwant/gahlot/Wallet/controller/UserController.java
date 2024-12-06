package com.yashwant.gahlot.Wallet.controller;

import java.util.ArrayList;


import com.yashwant.gahlot.Wallet.entity.User;
import com.yashwant.gahlot.Wallet.exception.ResourceNotFoundException;
import com.yashwant.gahlot.Wallet.repository.UserRepository;
import com.yashwant.gahlot.Wallet.utilities.PostValidator;
import com.yashwant.gahlot.Wallet.utilities.PutValidator;
import com.yashwant.gahlot.Wallet.utilities.UtilityMethods;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RequestMapping("api/users")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @GetMapping
    public ArrayList<User> getAllusers() {

        logger.log(Level.INFO, "list of all users returned at " + UtilityMethods.getCurrentTime());
        return (ArrayList<User>) this.userRepository.findAll();

    }

    @GetMapping("/{uid}")
    public User getUserById(@PathVariable(value = "uid") long userId) throws ResourceNotFoundException {

        return this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not Found with id : " + userId));
    }


    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws ResourceNotFoundException {// we will be requiring request body to get data to fill
        PostValidator.postResponseMessageUser(user, userRepository);//checking conditions
        logger.log(Level.INFO, user.toString());
        return this.userRepository.save(user);
    }


    @PutMapping("/{uid}")
    public User updateUser(@RequestBody User user, @PathVariable("uid") long userId) throws ResourceNotFoundException {

        User existingUser = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not Fondd with id : " + userId));

        PutValidator.canBeUpdatedUser(user, existingUser, userRepository);//condition checking
//        if(!PutValidator.canBeUpdatedUser(user, existingUser)) {
//            logger.log(Level.INFO, "Only email and address can be updated");
//            throw new ResourceNotFoundException("Only email and address can be updated");
//        }
        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }
        if (user.getAddress1() != null) {
            existingUser.setAddress1(user.getAddress1());
        }
        if (user.getAddress2() != null) {
            existingUser.setAddress2(user.getAddress2());
        }
        if (user.getMobile() != null) {
            existingUser.setMobile(user.getMobile());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getGender() != null) {
            existingUser.setGender(user.getGender());
        }
        ///balance is not updated here
        ///same number or email in data base cannot be changed
        ///pk cannot be changed

        logger.log(Level.INFO, user.toString());

        return this.userRepository.save(existingUser);//this will directly save into database
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<User> deleteUser(@PathVariable("uid") long userId) throws ResourceNotFoundException {

        User existingUser = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not Fondd with id : " + userId));
        this.userRepository.delete(existingUser);
        logger.log(Level.INFO, existingUser.toString() + "deleted");
        return ResponseEntity.ok().build();


    }

    @DeleteMapping(value = "/admin/alluser")
    public ResponseEntity<?> deleteAll() {
        logger.log(Level.INFO, "all users deleted at " + UtilityMethods.getCurrentTime());
        String responseBody = new String("all users deleted");
        userRepository.deleteAll();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}

/*
* 1. @RequestMapping("api/users"): This annotation is used to specify the base URL mapping for the controller.all the HTTP endpoints in this controller will be relative to the base URL "api/users".
* 2.@RestController annotation: This annotation is used to indicate that the class is a RESTful controller that handles HTTP requests and returns the response directly.
* 3. @Autowired annotation: This annotation is used to inject an instance
 * 4. @GetMapping without a specific path, which means it will handle GET requests to the base URL "api/users".
 * 5.  The @Valid annotation is used for validating the input data based on any validation annotations specified in the User class, such as @NotNull, @NotBlank, or @Size.
 * 6. @RequestBody User user: This annotation binds the request body to the user parameter, which represents the updated user information sent in the PUT request. The User object contains the updated values for the user's properties.
 * 7.  @PathVariable("uid") long userId: This annotation binds the path variable {uid} to the userId parameter. It retrieves the user ID from the URL and assigns it to the userId variable.
 * */


/* Path variable vs Request Variable
	    @PathVariable:
        @PathVariable is used to extract a specific part of the URL path and bind it to a method parameter.
        It is typically used to capture dynamic values from the URL, such as IDs or names.
        Example: @GetMapping("/users/{id}"), where {id} is the path variable.

    Request Variable:
        Request variables are used to extract data from the query parameters or the request body.
        Query parameters are key-value pairs appended to the URL, such as ?key1=value1&key2=value2.
        Request variables can also be used to extract form data or JSON data from the request body.
        Example: @GetMapping("/users"), where the request can include query parameters like /users?key=value.

Here's a comparison between @PathVariable and request variables:

    Usage:
        @PathVariable is used when you want to extract a specific part of the URL path.
        Request variables are used when you want to extract data from query parameters or the request body.

    Syntax:
        @PathVariable is annotated on a method parameter, preceded by the variable name in curly braces within the URL mapping.
        Request variables can be accessed using annotations like @RequestParam, @RequestBody, or implicitly using method parameters.

    Data Source:
        @PathVariable extracts data from the URL path.
        Request variables extract data from query parameters or the request body.

    Example:
        For a URL like /users/{id}, you can use @PathVariable to extract the id from the URL path.
        For a URL like /users?key=value, you can use request variables to extract the key and value from the query parameters.

In summary, @PathVariable is used to extract data from the URL path, while request variables are used to extract data from query parameters or the request body. The choice between them depends on where the data is located within the request.
	* */