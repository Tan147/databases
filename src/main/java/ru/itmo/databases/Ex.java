package ru.itmo.databases;

import java.util.ArrayList;
import java.util.List;

public class Ex {
}




class User {
    private int id;
    private List<Post> posts = new ArrayList<>();
}

class Post {
    private int id;
    private  List<User> aurhors = new ArrayList<>();
    private  List<Comment> comments = new ArrayList<>();
}

class Comment {
    private int id;

}
