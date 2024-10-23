package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Петр", "Петров", (byte) 28);
        userService.saveUser("Иван", "Иванов", (byte) 32);
        userService.saveUser("Сергей", "Сергеев", (byte) 48);
        userService.saveUser("Андрей", "Андреев", (byte) 53);

        System.out.println("Список всех пользователей:");
        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
