package conduit.articles.usecases.deletecomment;

public record DeleteCommentCmd(String articleSlug, Long commentId) {}
