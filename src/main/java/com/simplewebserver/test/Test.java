package com.simplewebserver.test;

import com.simplewebserver.util.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class Test {
    static class User {
        String name;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        int age;
    }

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User("test" + i, i * 10));
        }
        map.put("code", 120);
        map.put("message", "result json");
        map.put("data", users);
        System.out.println(JsonUtil.toJSON(map));
    }
}
