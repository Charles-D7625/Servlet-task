package com.example.enums;

public enum CategoryType {

    MAIN_DISH("Main dish");

    private String type;

    CategoryType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static CategoryType fromString(String text) {

        for (CategoryType os : CategoryType.values()) {
            if (os.type.equalsIgnoreCase(text.trim())) {
                return os;
            }
        }
        
        throw new IllegalArgumentException("No enum constant with text" + text);
    }
}
