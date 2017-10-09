package Blog4Me.ua.jrc.updateUser;

import Blog4Me.ua.jrc.deleteUser.DeletePage;
import Blog4Me.ua.jrc.login.LoginPage;
import Blog4Me.ua.jrc.main.MainPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.jrc.db.domain.StartDB;
import ua.jrc.db.entity.User;
import ua.jrc.db.repository.UserRepository;

public class UpdatePage extends WebPage {

    public UpdatePage() {
        super();
        setResponsePage(LoginPage.class);
    }

    public UpdatePage(final PageParameters parameters) {
        super();

        UserRepository userRepository2 = StartDB.getContext().getBean(UserRepository.class);

        if(userRepository2.findOne(parameters.get("loginU").toString()) == null) {
            setResponsePage(LoginPage.class);
        }

        final User user = new User();

        Form formUpdate = new Form("formUpdate");
        UserRepository userRepository = StartDB.getContext().getBean(UserRepository.class);

        final Label loginM = new Label("loginM", "");
        loginM.setOutputMarkupId(true);
        loginM.setOutputMarkupPlaceholderTag(true);
        loginM.setVisible(false);

        final Label firstNameM = new Label("firstNameM", "");
        firstNameM.setOutputMarkupId(true);
        firstNameM.setOutputMarkupPlaceholderTag(true);
        firstNameM.setVisible(false);

        final Label lastNameM = new Label("lastNameM", "");
        lastNameM.setOutputMarkupId(true);
        lastNameM.setOutputMarkupPlaceholderTag(true);
        lastNameM.setVisible(false);

        final Label passwordM = new Label("passwordM", "");
        passwordM.setOutputMarkupId(true);
        passwordM.setOutputMarkupPlaceholderTag(true);
        passwordM.setVisible(false);

        final TextField login = new TextField("login", new PropertyModel(user, "login"));
        login.setOutputMarkupId(true);
        login.setDefaultModelObject(parameters.get("loginU").toString());
        login.setEnabled(false);

        final TextField firstName = new TextField("firstName", new PropertyModel(user, "firstName"));
        try{
            firstName.setDefaultModelObject(userRepository.findOne(parameters.get("loginU").toString()).getFirstName());
        } catch (Exception e){
            setResponsePage(LoginPage.class);
        }
        firstName.setOutputMarkupId(true);

        final TextField lastName = new TextField("lastName", new PropertyModel(user, "lastName"));
        try{
            lastName.setDefaultModelObject(userRepository.findOne(parameters.get("loginU").toString()).getLastName());
        } catch (Exception e){
            setResponsePage(LoginPage.class);
        }
        lastName.setOutputMarkupId(true);

        final TextField password = new TextField("password", new PropertyModel(user, "password"));
        try{
            password.setDefaultModelObject(userRepository.findOne(parameters.get("loginU").toString()).getPassword());
        } catch (Exception e){
            setResponsePage(LoginPage.class);
        }
        password.setOutputMarkupId(true);

        AjaxButton update = new AjaxButton("update") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                super.onSubmit();

                //Check for empty area firstName
                if (firstName.getInput() != "") {
                    //Check for empty area lastName
                    if (lastName.getInput() != "") {
                        //Check for empty area password
                        if (password.getInput() != "") {

                            //If each area are filled
                            //Check for length area password
                            if(password.getInput().length() >= 8) {

                                //Check firstName for a syntax error
                                char [] firstNameC = firstName.getInput().toCharArray();
                                boolean firstNameSyntaxError = false;
                                for(char c: firstNameC){
                                    if(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122){
                                    } else firstNameSyntaxError = true;
                                }
                                if(!firstNameSyntaxError) {

                                    //Check lastName for a syntax error
                                    char[] lastNameC = lastName.getInput().toCharArray();
                                    boolean lastNameSyntaxError = false;
                                    for (char c : lastNameC) {
                                        if (c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122) {
                                        } else lastNameSyntaxError = true;
                                    }
                                    if (!lastNameSyntaxError) {

                                        //Check password for a syntax error
                                        char[] passwordC = password.getInput().toCharArray();
                                        boolean passwordSyntaxError = false;
                                        for (char c : passwordC) {
                                            if (c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122) {
                                            } else passwordSyntaxError = true;
                                        }
                                        try{if (!passwordSyntaxError) {
                                            //If syntax is right
                                            UserRepository userRepository1 = StartDB.getContext().getBean(UserRepository.class);

                                            userRepository1.delete(parameters.get("loginU").toString());

                                            User tempUser = new User();
                                            tempUser.setLogin(parameters.get("loginU").toString());
                                            tempUser.setFirstName(firstName.getInput());
                                            tempUser.setLastName(lastName.getInput());
                                            tempUser.setPassword(password.getInput());

                                            userRepository1.save(tempUser);

                                            setResponsePage(MainPage.class, parameters);

                                        } else {
                                            passwordM.setDefaultModelObject("Password must be composed only of latin and numbers!");
                                            passwordM.setVisible(true);
                                            lastName.setVisible(false);
                                            firstNameM.setVisible(false);
                                        }
                                        } catch (Exception e){
                                            setResponsePage(LoginPage.class);
                                        }
                                    } else {
                                        lastName.setDefaultModelObject("Last name must be composed only of latin and numbers!");
                                        lastName.setVisible(true);
                                        passwordM.setVisible(false);
                                        firstNameM.setVisible(false);
                                    }
                                } else {
                                    firstNameM.setDefaultModelObject("First name must be composed only of latin and numbers!");
                                    firstNameM.setVisible(true);
                                    lastName.setVisible(false);
                                    passwordM.setVisible(false);
                                }
                            } else {
                                passwordM.setDefaultModelObject("Password is too short! (must be more then 7 symbols)");
                                passwordM.setVisible(true);
                                firstNameM.setVisible(false);
                                lastNameM.setVisible(false);
                            }
                        } else {
                            passwordM.setDefaultModelObject("Password is empty!");
                            passwordM.setVisible(true);
                            firstNameM.setVisible(false);
                            lastNameM.setVisible(false);
                        }
                    } else {
                        lastNameM.setDefaultModelObject("Last name is empty!");
                        lastNameM.setVisible(true);
                        passwordM.setVisible(false);
                        firstNameM.setVisible(false);
                    }
                } else {
                    firstNameM.setDefaultModelObject("First name is empty!");
                    firstNameM.setVisible(true);
                    lastNameM.setVisible(false);
                    passwordM.setVisible(false);
                }

                //Update message labels for firstName, lastName and password
                target.add(firstNameM);
                target.add(lastNameM);
                target.add(passwordM);
            }
        };

        add(formUpdate);

        formUpdate.add(login);
        formUpdate.add(loginM);

        formUpdate.add(firstName);
        formUpdate.add(firstNameM);

        formUpdate.add(lastName);
        formUpdate.add(lastNameM);

        formUpdate.add(password);
        formUpdate.add(passwordM);

        formUpdate.add(update);

        Form formDelete = new Form("formDelete");

        Button delete = new Button("delete") {
            @Override
            public void onSubmit(){
                super.onSubmit();
                setResponsePage(DeletePage.class, parameters);
            }
        };

        add(formDelete);

        formDelete.add(delete);
    }
}
