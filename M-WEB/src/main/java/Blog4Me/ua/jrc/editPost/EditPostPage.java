package Blog4Me.ua.jrc.editPost;

import Blog4Me.ua.jrc.blog.BlogPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import ua.jrc.db.domain.StartDB;
import ua.jrc.db.entity.Post;
import ua.jrc.db.repository.PostRepository;

public class EditPostPage extends WebPage {

    public EditPostPage(final PageParameters parameters) {
        super(parameters);
        final PostRepository postRepository = StartDB.getContext().getBean(PostRepository.class);

        Form formEdit = new Form("formEdit");

        final Label messageM = new Label("messageM", "");
        messageM.setOutputMarkupId(true);
        messageM.setOutputMarkupPlaceholderTag(true);
        messageM.setVisible(false);

        Post post = new Post();
        final TextArea<String> message = new TextArea("message", Model.of(""));
        message.setOutputMarkupId(true);
        message.setDefaultModelObject(postRepository.findOne(parameters.get("messageU").toString()).getText());

        AjaxButton edit = new AjaxButton("edit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);

                if(message.getInput().length() < 12){
                    messageM.setDefaultModelObject("Length must be more than 11 symbols!!!");
                    messageM.setVisible(true);
                } else {
                    Post newPost = new Post();
                    newPost.setMid(parameters.get("messageU").toString());
                    newPost.setBid(parameters.get("blogU").toString());
                    newPost.setLogin(parameters.get("loginU").toString());
                    newPost.setText(message.getInput().toString());

                    postRepository.delete(parameters.get("messageU").toString());
                    postRepository.save(newPost);

                    setResponsePage(BlogPage.class, parameters);
                }

                target.add(messageM);
            }
        };

        Form formDeleteMessage = new Form("formDeleteMessage");
        Button deleteMessage = new Button("deleteMessage") {
            @Override
            public void onSubmit(){
                super.onSubmit();
                postRepository.delete(parameters.get("messageU").toString());

                parameters.remove("messageU");
                setResponsePage(BlogPage.class, parameters);
            }
        };
        formDeleteMessage.add(deleteMessage);


        formEdit.add(messageM);
        formEdit.add(message);
        formEdit.add(edit);

        add(formEdit);
        add(formDeleteMessage);
    }
}