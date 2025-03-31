package com.xcmg.i18n.test.mq;

import lombok.Data;

@Data
public class User {

    private String id;
    private String name;

    public static User getUser() {
        User user = new User();
        user.setId("007");
        user.setName("James Bond");
        return user;
    }

}
