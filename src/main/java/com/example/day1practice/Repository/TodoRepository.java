package com.example.day1practice.Repository;

import com.example.day1practice.Model.Todo;
import com.example.day1practice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Todo findTodoById(Integer id);
    List<Todo> findAllByUser(User user);
}
