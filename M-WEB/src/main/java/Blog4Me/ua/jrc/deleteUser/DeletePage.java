package Blog4Me.ua.jrc.deleteUser;

import Blog4Me.ua.jrc.login.LoginPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import ua.jrc.db.domain.StartDB;
import ua.jrc.db.entity.User;
import ua.jrc.db.repository.UserRepository;

public class DeletePage extends WebPage {

    public DeletePage() {
        super();
        setResponsePage(LoginPage.class);
    }

    public DeletePage(final PageParameters parameters) {
        super();

        UserRepository userRepository2 = StartDB.getContext().getBean(UserRepository.class);

        if(userRepository2.findOne(parameters.get("loginU").toString()) == null) {
            setResponsePage(LoginPage.class);
        }

        final User user = new User();

        Form formDelete = new Form("formDelete");

        final Label loginM = new Label("loginM", "");
        loginM.setOutputMarkupId(true);
        loginM.setOutputMarkupPlaceholderTag(true);
        loginM.setVisible(false);

        final Label passwordM = new Label("passwordM", "");
        passwordM.setOutputMarkupId(true);
        passwordM.setOutputMarkupPlaceholderTag(true);
        passwordM.setVisible(false);

        final TextField login = new TextField("login", new PropertyModel(user, "login"));
        login.setOutputMarkupId(true);
        login.setDefaultModelObject(parameters.get("loginU").toString());
        login.setEnabled(false);

        final TextField password = new TextField("password", new PropertyModel(user, "password"));
        password.setOutputMarkupId(true);

        AjaxButton delete = new AjaxButton("delete"){
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form){
                super.onSubmit();

                    //Check for empty area password
                    if(password.getInput() != "") {

                        //Check password for a syntax error
                        char [] passwordC = password.getInput().toCharArray();
                        boolean passwordSyntaxError = false;
                        for(char c: passwordC){
                            if(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122){
                            } else passwordSyntaxError = true;
                        }
                        if(!passwordSyntaxError){

                            //If syntax is right
                            //Connect to DB
                            UserRepository userRepository = StartDB.getContext().getBean(UserRepository.class);

                            //Check for password
                            try{
                                if(password.getInput().equals(userRepository.findOne(parameters.get("loginU").toString()).getPassword())) {

                                    //All checks is true
                                    //Delete user
                                    userRepository.delete(parameters.get("loginU").toString());
                                    //Go to LoginPage
                                    setResponsePage(LoginPage.class);

                                } else {
                                    passwordM.setDefaultModelObject("Password is not correct!");
                                    passwordM.setVisible(true);
                                }
                            } catch (NullPointerException e){
                                setResponsePage(LoginPage.class);
                            }
                        } else {
                            passwordM.setDefaultModelObject("Password must be composed only of latin and numbers!");
                            passwordM.setVisible(true);
                        }
                    } else {
                        passwordM.setDefaultModelObject("Password is empty!");
                        passwordM.setVisible(true);
                    }

                //Update message label for password
                target.add(passwordM);

            }
        };

        add(formDelete);

        formDelete.add(login);
        formDelete.add(loginM);

        formDelete.add(password);
        formDelete.add(passwordM);

        formDelete.add(delete);

    }
}
