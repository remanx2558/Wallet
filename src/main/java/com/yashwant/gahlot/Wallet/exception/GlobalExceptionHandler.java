package com.yashwant.gahlot.Wallet.exception;
import java.sql.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
//(if /test endpoint is invoked, and the CustomException is thrown) --->  (the handleCustomException method in GlobalExceptionHandler is triggered) -----> (It returns a ResponseEntity with the exception message and an appropriate HTTP status code)
//whenever a CustomException is thrown from any controller method within the application, the GlobalExceptionHandler will intercept it and provide a unified response.
@ControllerAdvice //@ControllerAdvice, indicating that it provides centralized exception handling for multiple controllers in a Spring application.
//is an annotation that marks the class as a global exception handler. It intercepts exceptions thrown by controllers and handles them in a centralized manner.

//@ControllerAdvice can also provide other functionalities, such as model attribute methods, data binding configuration, or request/response preprocessing. These features allow you to encapsulate common logic or behavior related to controllers within a single class.
public class GlobalExceptionHandler {

    //1) ErrorDetails: In each exception handling method, an ErrorDetails object is created using the provided exception details, current timestamp , and request description.
    //2) ResponseEntity: This allows you to customize the HTTP response that will be sent back to the client.
    // The exception handling methods return a ResponseEntity object that wraps the ErrorDetails object and the appropriate HTTP status code (HttpStatus).
    // This allows the response to include the error details and the corresponding status code.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){

        ErrorDetails errorDetails =
                new ErrorDetails(new Date(0, 0, 0), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //Exception types: The exception handling methods within a @ControllerAdvice-annotated
    // class can be tailored to handle specific types of exceptions.
    // By using the @ExceptionHandler annotation on individual methods,
    // you can specify which exception types each method should handle.

    //It's important to note that when using @ExceptionHandler annotations,
    // the order of the exception handling methods can be significant. The most specific exception handlers
    // should be defined first, followed by more general exception handlers.
    // This ensures that the appropriate exception handler is invoked for a given exception based on the
    // inheritance hierarchy.

    //Annotation: @ExceptionHandler(Exception.class) specifies that this method should handle any
    // exception of type Exception or its subclasses.
    @ExceptionHandler(APIException.class)
    public ResponseEntity<?> handleAPIException(ResourceNotFoundException exception, WebRequest request){

        ErrorDetails errorDetails =
                new ErrorDetails(new Date(0, 0, 0), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    //Exception handling method signatures: Exception handling methods within a
    // @ControllerAdvice class typically have a consistent method signature.
    // They typically accept the exception type as a parameter and optionally additional
    // parameters like the request object or binding result. The return type of
    // these methods is typically a ResponseEntity or any other appropriate type for the response.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException exception){
        ErrorDetails errorDetails = new ErrorDetails(new Date(0, 0, 0),"validation error",exception.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }


//By using @ExceptionHandler(Exception.class) at the method level, any uncaught exceptions that occur within
// the application will be handled by this method.
// It provides a centralized place to handle unexpected or generic exceptions that may not have specific exception
// handling methods defined. This helps in gracefully handling and responding to unexpected errors and provides
// a consistent error response format across the application.

    //The @ExceptionHandler(Exception.class) annotation in the GlobalExceptionHandler
    // class is used to handle any uncaught exceptions that occur within the application.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request){

        //The getDescription(false) method provides a textual representation of the web request,
        // typically including information such as the HTTP method, request URI,
        // query parameters, and request headers. It can be useful for logging or error handling
        // purposes, allowing you to capture important details about the request.
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(0, 0, 0), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
