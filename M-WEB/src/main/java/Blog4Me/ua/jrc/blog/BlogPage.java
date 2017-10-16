package Blog4Me.ua.jrc.blog;

import Blog4Me.ua.jrc.addPost.AddPostPage;
import Blog4Me.ua.jrc.editPost.EditPostPage;
import Blog4Me.ua.jrc.login.LoginPage;
import Blog4Me.ua.jrc.updateUser.UpdatePage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import ua.jrc.db.domain.StartDB;
import ua.jrc.db.entity.Blog;
import ua.jrc.db.entity.Post;
import ua.jrc.db.entity.User;
import ua.jrc.db.repository.BlogRepository;
import ua.jrc.db.repository.PostRepository;
import ua.jrc.db.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlogPage extends WebPage {
/*
    public BlogPage() {
        super();
        setResponsePage(LoginPage.class);
    }
*/
    public BlogPage(final PageParameters parameters) {
        super(parameters);
        //delete
        parameters.add("loginU", "Login2");
        parameters.add("blogU", "b1");
        //
        UserRepository userRepository = StartDB.getContext().getBean(UserRepository.class);
        PostRepository postRepository = StartDB.getContext().getBean(PostRepository.class);
        BlogRepository blogRepository = StartDB.getContext().getBean(BlogRepository.class);

        if(userRepository.findOne(parameters.get("loginU").toString()) == null || blogRepository.findOne(parameters.get("blogU").toString()) == null) {
            setResponsePage(LoginPage.class);
        }

        String loginDB = parameters.get("loginU").toString();
        User user =  userRepository.findOne(loginDB);

        String userInitials = user.getLogin() + "(" + user.getFirstName() + " " + user.getLastName() + ")";

        add(new Label("userInitials", userInitials));

        Form formUpdate = new Form("formUpdate");

        Button update = new Button("update") {
            @Override
            public void onSubmit(){
                super.onSubmit();

                setResponsePage(UpdatePage.class, parameters);
            }
        };

        add(formUpdate);
        formUpdate.add(update);

        Form formSignOut = new Form("formSignOut");

        Button signOut = new Button("signOut") {
            @Override
            public void onSubmit(){
                super.onSubmit();

                setResponsePage(LoginPage.class);
            }
        };

        add(formSignOut);
        formSignOut.add(signOut);

        add(new Label("subject", blogRepository.findOne(parameters.get("blogU").toString()).getSubject()));

        Form formAddMessage = new Form("formAddMessage");

        Button addMessage = new Button("addMessage") {
            @Override
            public void onSubmit(){
                super.onSubmit();

                setResponsePage(AddPostPage.class, parameters);
            }
        };

        add(formAddMessage);

        formAddMessage.add(addMessage);

        //MESSAGES
        List<Post> tempPostList = postRepository.findAll();
        List<Post> postList = new ArrayList<Post>();
        for(Post p: tempPostList){
            if(p.getBid().equals(parameters.get("blogU").toString())){
                postList.add(p);
            }
        }

        RepeatingView htmlPosts = new RepeatingView("post");
        htmlPosts.setOutputMarkupId(true);
        htmlPosts.setOutputMarkupPlaceholderTag(true);

        for(final Post post: postList){
            WebMarkupContainer informationBox = new WebMarkupContainer (htmlPosts.newChildId());

            String userLStr = post.getLogin();

            Label userL = new Label("userL", userLStr);

            Form formDeleteMessage = new Form("formDeleteMessage");
            Button deleteMessage = new Button("deleteMessage") {
                @Override
                public void onSubmit(){
                    super.onSubmit();
                    //POP-WINDOW
                }
            };
            formDeleteMessage.add(deleteMessage);
            formDeleteMessage.setVisible(false);

            Form formEditMessage = new Form("formEditMessage");
            Button editMessage = new Button("editMessage") {
                @Override
                public void onSubmit(){
                    super.onSubmit();
                    parameters.add("messageU", post.getMid());
                    setResponsePage(EditPostPage.class, parameters);
                }
            };
            formEditMessage.add(editMessage);
            formEditMessage.setVisible(false);

            Label message = new Label("message", post.getText());

            informationBox.add(userL);
            informationBox.add(formDeleteMessage);
            informationBox.add(formEditMessage);
            if(loginDB.equals(userLStr)){
                formDeleteMessage.setVisible(true);
                formEditMessage.setVisible(true);
            }
            informationBox.add(message);

            htmlPosts.add(informationBox);
        }

        add(htmlPosts);
    }
}
