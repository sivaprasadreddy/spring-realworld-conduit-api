package conduit.articles.usecases.shared.models;

import java.util.List;

public record MultipleArticles(List<Article> articles, Integer articlesCount) {}
