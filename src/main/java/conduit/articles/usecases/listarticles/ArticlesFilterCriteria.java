package conduit.articles.usecases.listarticles;

record ArticlesFilterCriteria(String tag, String author, String favoritedBy, Integer limit, Integer offset) {}
