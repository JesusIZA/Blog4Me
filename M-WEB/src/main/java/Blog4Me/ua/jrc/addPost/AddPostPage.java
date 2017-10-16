package Blog4Me.ua.jrc.addPost;

import Blog4Me.ua.jrc.blog.BlogPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import ua.jrc.db.domain.StartDB;
import ua.jrc.db.entity.Blog;
import ua.jrc.db.entity.Post;
import ua.jrc.db.entity.User;
import ua.jrc.db.repository.BlogRepository;
import ua.jrc.db.repository.PostRepository;
import ua.jrc.db.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddPostPage extends WebPage {
    public AddPostPage(final PageParameters parameters) {
        super(parameters);
        System.out.println(parameters);

        Form formAdd = new Form("formAdd");

        final Label messageM = new Label("messageM", "");
        messageM.setOutputMarkupId(true);
        messageM.setOutputMarkupPlaceholderTag(true);
        messageM.setVisible(false);

        Post post = new Post();
        final TextArea<String> message = new TextArea("message", Model.of(""));
        message.setOutputMarkupId(true);

        AjaxButton add = new AjaxButton("add") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);

                if(message.getInput().length() < 12){
                    messageM.setDefaultModelObject("Length must be more than 11 symbols!!!");
                    messageM.setVisible(true);
                } else {
                    PostRepository postRepository = StartDB.getContext().getBean(PostRepository.class);

                    List postL = postRepository.findAll();

                    Post newPost = new Post();
                    newPost.setMid("m" + (postL.size() + 1));
                    newPost.setBid(parameters.get("blogU").toString());
                    newPost.setLogin(parameters.get("loginU").toString());
                    newPost.setText(message.getInput().toString());

                    postRepository.save(newPost);

                    setResponsePage(BlogPage.class, parameters);
                }

                target.add(messageM);
            }
        };

        formAdd.add(messageM);
        formAdd.add(message);
        formAdd.add(add);

        add(formAdd);
    }
}
