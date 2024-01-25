package com.example.forum.exceptions;

public class UserDontHaveAnyException extends RuntimeException{

    public UserDontHaveAnyException(String type){
        super(String.format("This user don't have any %s yet.", type));
    }
}
