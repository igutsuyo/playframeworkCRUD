package controllers;

import java.util.*;
import models.User;
import models.UserDao;
import play.mvc.*;
import play.data.*;
import play.api.*;

import javax.inject.Inject;
import play.db.Database;
import javax.inject.Named;
import play.i18n.MessagesApi;
import play.i18n.Messages;

public class UserController extends Controller {

    @Inject
    @Named("userDao")
    UserDao dao;

    private Database db;
    private FormFactory formFactory;
    private MessagesApi messagesApi;
    private Form<UserForm> form;

    @Inject
    public UserController(Database db, FormFactory formFactory, MessagesApi messagesApi) {
        this.db = db;
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
    }

    public Result index() {
        List<User> users = this.db.withConnection((conn) -> {
            return this.dao.search(conn);
        });
        return ok(views.html.user.index.render(users));
    }

    public Result detail(long id) {
        User user = this.db.withConnection((conn) -> {
            return this.dao.find(id, conn);
        });
        return ok(views.html.user.detail.render(user));
    }
    // 作成ページへの移動
    public Result createPage(Http.Request request) {
        Form<UserForm> form = formFactory.form(UserForm.class);
        Messages messages = this.messagesApi.preferred(request);
        return ok(views.html.user.create.render(form, request, messages));
    }

    // 作成
    public Result create(Http.Request request) {
      
            Form<UserForm> form = formFactory.form(UserForm.class).bindFromRequest(request);
            Messages messages = this.messagesApi.preferred(request);
            if (form.hasErrors()) {
                return badRequest(views.html.user.create.render(form, request, messages));
            }else {
            UserForm userForm = form.get();
            User user = new User(0, userForm.getName(), userForm.getAge());

            this.db.withTransaction((conn) -> {
            return this.dao.create(user, conn);
            });
   
            return ok(views.html.user.created.render(user));
            }
        }

    // 編集ページの移行
    public Result updatePage(Http.Request request, Long id) {
        User user = this.db.withConnection((conn) -> {
            return this.dao.find(id, conn);
        });
        Form<UserForm> form = formFactory.form(UserForm.class);
        Messages messages = this.messagesApi.preferred(request);
        UserForm userForm = new UserForm();
        if(user != null) {
            userForm.setName(user.getName());
            userForm.setAge(user.getAge());
        }
        Form<UserForm> filledForm = form.fill(userForm);
        return ok(views.html.user.update.render(user, filledForm, request, messages));
    }

    // 編集
    public Result update(Http.Request request, Long id) {
        Form<UserForm> form = formFactory.form(UserForm.class).bindFromRequest(request);
        Messages messages = this.messagesApi.preferred(request);
        if (form.hasErrors()) {
            User user = this.db.withConnection((conn) -> {
                return this.dao.find(id, conn);
            });
            return badRequest(views.html.user.update.render(user, form, request, messages));
        }else {
        UserForm userForm = form.get();

        User user = new User(id, userForm.getName(), userForm.getAge());

        this.db.withTransaction((conn) -> {
            return this.dao.update(id, user, conn);
        });

        return ok(views.html.user.updated.render(user));
    }
}

    // 削除ページの移行
    public Result deletePage(Http.Request request, long id) {
        User user = this.db.withConnection((conn) -> {
            return this.dao.find(id, conn);
        });
        return ok(views.html.user.delete.render(user, request));
    }

    // 削除
    public Result delete(long id) {
        User user = this.db.withTransaction((conn) -> {
            return this.dao.delete(id, conn);
        });

        return ok(views.html.user.deleted.render());
    }

}