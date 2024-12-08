package com.example.toeicwebsite.enumeration;

public enum ERole {
    ADMIN("Role_Admin"),
//    LIBRARY_MANAGER("Role_LibraryManager"),
//    LECTURER("Role_Lecturer"),
    STUDENT("Role_Student");

    public static long roleAdmin = 1;
//    public static long roleLibrary_manager = 2;
//    public static long roleLecture = 3;
    public static long roleStudent = 2;

    private final String name;

    ERole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
