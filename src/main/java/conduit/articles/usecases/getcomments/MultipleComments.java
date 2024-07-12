package conduit.articles.usecases.getcomments;

import conduit.articles.usecases.shared.models.Comment;
import java.util.List;

record MultipleComments(List<Comment> comments) {}
