package Blog4Me.ua.jrc.main;

import Blog4Me.ua.jrc.blog.BlogPage;
import Blog4Me.ua.jrc.login.LoginPage;
import Blog4Me.ua.jrc.updateUser.UpdatePage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import ua.jrc.db.domain.StartDB;
import ua.jrc.db.entity.Blog;
import ua.jrc.db.entity.User;
import ua.jrc.db.repository.BlogRepository;
import ua.jrc.db.repository.UserRepository;

import java.util.List;

public class MainPage extends WebPage {

	public MainPage() {
		super();
		setResponsePage(LoginPage.class);
	}

	public MainPage(final PageParameters parameters) {
		super(parameters);
		UserRepository userRepository = StartDB.getContext().getBean(UserRepository.class);

		if(userRepository.findOne(parameters.get("loginU").toString()) == null) {
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

		//BLOGS
		final BlogRepository blogRepository = StartDB.getContext().getBean(BlogRepository.class);

		List<Blog> blogList = blogRepository.findAll();

		RepeatingView htmlBlogs = new RepeatingView("strip");
		htmlBlogs.setOutputMarkupId(true);
		htmlBlogs.setOutputMarkupPlaceholderTag(true);

		//Blogs refers
		parameters.add("blogU", "b1");
		int i = 1;
		for(Blog blog: blogList){
			WebMarkupContainer informationBox = new WebMarkupContainer ("content");
			informationBox.add(new Label("label", blog.getSubject()));
			informationBox.add(new Image("img", "blogs/str" + i + ".jpg"));
			final int b = i;
			Link blogRef = new Link(htmlBlogs.newChildId()) {
				@Override
				public void onClick() {
					//REFER
					parameters.set("blogU", "b" + b);

					setResponsePage(BlogPage. class, parameters);
				}
			};

			i++;
			blogRef.add(informationBox);
			htmlBlogs.add(blogRef);
		}

		add(htmlBlogs);
	}
}
