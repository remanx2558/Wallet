package com.yashwant.gahlot.Wallet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}

//These getter and setter methods provide a way to encapsulate the
// instance variables and control their access, allowing other classes
// to interact with the ErrorDetails objects in a controlled manner.
//
//Overall, the ErrorDetails class is designed to represent information about an error,
// including the timestamp, message, and additional details. It provides a constructor
// to initialize these values and getter and setter methods to access and modify them.




//super(): The super() statement is used to call the constructor of the superclass.
// In this case, since ErrorDetails does not explicitly extend any class,
// it implicitly extends the Object class, which is the root of all classes in Java.
// By calling super(), it ensures that the superclass's constructor is executed.
// In this case, the super() statement doesn't provide any arguments,
// so it calls the default constructor of the Object class.

//calling the Object class constructor using super() in the constructor of
// ErrorDetails ensures proper initialization and follows the inheritance hierarchy Consistentently.
// It's a good practice to include this call to maintain consistent behavior and future-proof the code.

//Reasoning of it:-
//1) Initialization of Object class members: The Object class may have its own instance variables,
// methods, or other members that need to be initialized or set up before an instance
// of ErrorDetails is created. By calling super(), the Object class constructor
// is invoked to perform any necessary initialization steps.