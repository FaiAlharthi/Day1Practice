package com.example.day1practice.Service;

import com.example.day1practice.Api.ApiException;
import com.example.day1practice.Model.Todo;
import com.example.day1practice.Model.User;
import com.example.day1practice.Repository.AuthRepository;
import com.example.day1practice.Repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final AuthRepository userRepository;

    public List<Todo> getAllTodos(Integer adminID){
        return todoRepository.findAll();
    }

    public List<Todo> getMyTodos (Integer userId){
        User user = userRepository.findUserById(userId);
        if(user == null){
            throw new ApiException("user not found");
        }
        return todoRepository.findAllByUser(user);
    }

    public void createTodo(Todo todo, Integer userId){
        User user = userRepository.findUserById(userId);
        if(userId == null){
            throw new ApiException("user not found");
        }
        todo.setUser(user);
        todoRepository.save(todo);
    }

    public void deleteTodo( Integer userId, Integer todoId){
        User user = userRepository.findUserById(userId);
        if(user == null){
            throw new ApiException("user not found");
        }

        Todo todo = todoRepository.findTodoById(todoId);
        if(todo == null){
            throw new ApiException("todo not exist");
        }

        if(user.equals(todo.getUser())){
            throw new ApiException("can't update");
        }

        todoRepository.delete(todoRepository.findTodoById(todoId));
    }

    public void updateTodo (Integer userId ,Integer todoId, Todo todo){
        User user = userRepository.findUserById(userId);
        if(userId == null){
            throw new ApiException("user not found");
        }
        Todo todo1 = todoRepository.findTodoById(todoId);
        if(todo == null){
            throw new ApiException("todo not exist");
        }
        if(user.equals(todo1.getUser())){
            throw new ApiException("can't update");
        }
        todo1.setTitle(todo.getTitle());
        todo1.setStatus(todo.getStatus());
        todoRepository.save(todo1);
    }

    public Todo getTodo ( Integer todoId){
        Todo todo = todoRepository.findTodoById(todoId);
        if(todo == null){
            throw new ApiException("todo not exist");
        }
        return todo;
    }

}
