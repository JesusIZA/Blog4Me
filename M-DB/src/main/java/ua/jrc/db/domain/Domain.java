package ua.jrc.db.domain;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.jrc.db.entity.Blog;
import ua.jrc.db.entity.Post;
import ua.jrc.db.entity.User;
import ua.jrc.db.repository.BlogRepository;
import ua.jrc.db.repository.PostRepository;
import ua.jrc.db.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

public class Domain {
    public static void main(String [] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserRepository userRepository = context.getBean(UserRepository.class);
        BlogRepository blogRepository = context.getBean(BlogRepository.class);
        PostRepository postRepository = context.getBean(PostRepository.class);

        System.out.println(userRepository.findAll());
        System.out.println(blogRepository.findAll());
        System.out.println(postRepository.findAll());
    }
}