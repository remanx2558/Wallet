package com.yashwant.gahlot.Wallet.exception;

public class APIException extends RuntimeException {
    //	The purpose of the serialVersionUID field is to provide
//	a version control mechanism for serialized objects. During
//	the serialization process, objects are converted into a stream
//	of bytes to be stored or transmitted. The serialVersionUID acts as
//	an identifier to ensure that the serialized object can be correctly deserialized even
//	if the class definition has changed between different versions of the program.
//	If the serialVersionUID of the serialized object matches the serialVersionUID
//	of the class at the time of deserialization, the process can proceed without issues.
//	However, if the serialVersionUID values don't match, an InvalidClassException
//	may occur, indicating a potential compatibility problem between different versions of the class.

    //1)private:field can only be accessed within the class itself.
    //2) field belongs to the class itself rather than to instances of the class.
    // This means that all instances of the class share the same serialVersionUID value.
    //3) field is a constant and cannot be changed after it is assigned a value. It means that the
    // serialVersionUID field will have a single value throughout the execution of the program.
    private static final long serialVersionUID = 1L;
    //super(message); is used to call the constructor of the superclass,
    // RuntimeException. By passing the message parameter to super,
    // the superclass is responsible for initializing its
    // own fields and performing any necessary setup.
    public APIException(String message) {
        super(message);
    }
}