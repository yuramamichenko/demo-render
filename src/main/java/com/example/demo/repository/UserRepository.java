package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import com.example.demo.entity.User;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private static final List<User> storage = new CopyOnWriteArrayList<User>() {
        {
            add(new User("John Doe", "john.doe@example.com"));
            add(new User("Alice Smith", "alice.smith@example.com"));
            add(new User("Michael Johnson", "michael.johnson@example.com"));
            add(new User("Emily Davis", "emily.davis@example.com"));
            add(new User("David Brown", "david.brown@example.com"));
            add(new User("Jennifer Wilson", "jennifer.wilson@example.com"));
            add(new User("Christopher Lee", "christopher.lee@example.com"));
            add(new User("Jessica Martinez", "jessica.martinez@example.com"));
            add(new User("Matthew Taylor", "matthew.taylor@example.com"));
            add(new User("Sarah Anderson", "sarah.anderson@example.com"));
        }
    };

    public List<User> getAll(int pageNumber, int pageSize) {
        return storage.stream()
                .sorted(Comparator.comparingLong(User::getId))
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public int getAllCount() {
        return storage.size();
    }

    public User save(User task) {
        task.setId(getMaxId() + 1);
        storage.add(task);
        return task;
    }

    public User update(User user) {
        storage.remove(findById(user.getId()).get());
        storage.add(user);
        return user;
    }

    public Optional<User> findById(long id) {
        return storage.stream().filter(task -> id == task.getId()).findFirst();
    }

    public void delete(User task) {
        storage.remove(task);
    }

    private long getMaxId() {
        return storage.stream()
                .map(User::getId)
                .max(Long::compareTo)
                .orElse(1L);
    }

    public List<User> findAll() {
        return storage.stream().toList();
    }
}