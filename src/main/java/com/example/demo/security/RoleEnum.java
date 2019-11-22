package com.example.demo.security;

public enum RoleEnum {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private String name;

     RoleEnum( String name ){
        this.name = name;
    }

    public static RoleEnum fromString( String name){

        RoleEnum result = null ;

        for( RoleEnum roleEnum : values()){
            if( roleEnum.name().equals(name)){
                result = roleEnum;
                break;
            }
        }

        return result;
    }
}
