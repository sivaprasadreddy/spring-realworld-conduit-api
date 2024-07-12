package conduit.articles.usecases.updatearticle;

record UpdateArticleCmd(String slug, String title, String description, String body) {}
