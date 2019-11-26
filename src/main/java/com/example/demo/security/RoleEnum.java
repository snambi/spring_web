package com.example.demo.security;

public enum RoleEnum {
    ROLE_USER("USER"),
    ROLE_MODERATOR("MODERATOR"),
    ROLE_STAFF("STAFF"),
    ROLE_ADMIN("ADMIN"),

    ROLE_API_USER("API_USER"),
    ROLE_API_MODERATOR("API_MODERATOR"),
    ROLE_API_APPLICATION("API_APPLICATION");

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
