delete from comments;
delete from article_tag;
delete from article_favorite;
delete from articles;
delete from tags;
delete from user_follower;
delete from users;

INSERT INTO users (id, email, password, username, bio, image, created_at) VALUES
(1, 'admin@gmail.com', '{bcrypt}$2a$10$NzbW145jfI5j/CNoeZ3LH.QGM2AIQuiKDAJHsAm8jVWuEF6DV4RuG', 'admin', 'I am a system administrator', 'https://api.realworld.io/images/demo-avatar.png', CURRENT_TIMESTAMP),
(2, 'siva@gmail.com', '{bcrypt}$2a$10$NzbW145jfI5j/CNoeZ3LH.QGM2AIQuiKDAJHsAm8jVWuEF6DV4RuG', 'siva', 'I am a Software Architect', 'https://api.realworld.io/images/demo-avatar.png', CURRENT_TIMESTAMP),
(3, 'prasad@gmail.com', '{bcrypt}$2a$10$NzbW145jfI5j/CNoeZ3LH.QGM2AIQuiKDAJHsAm8jVWuEF6DV4RuG', 'prasad', 'I am a Software Developer', 'https://api.realworld.io/images/demo-avatar.png', CURRENT_TIMESTAMP),
(4, 'john@gmail.com', '{bcrypt}$2a$10$NzbW145jfI5j/CNoeZ3LH.QGM2AIQuiKDAJHsAm8jVWuEF6DV4RuG', 'john', 'I am a UI Developer', 'https://api.realworld.io/images/demo-avatar.png', CURRENT_TIMESTAMP),
(5, 'james@gmail.com', '{bcrypt}$2a$10$NzbW145jfI5j/CNoeZ3LH.QGM2AIQuiKDAJHsAm8jVWuEF6DV4RuG', 'james', 'I am a Java Developer', 'https://api.realworld.io/images/demo-avatar.png', CURRENT_TIMESTAMP)
;

insert into user_follower(from_id, to_id) values
(1,2),
(2,3),
(3,2),
(4,1),
(4,2),
(4,3);

insert into tags (id, name, created_at) values
(1, 'java', CURRENT_TIMESTAMP),
(2, 'spring-boot', CURRENT_TIMESTAMP),
(3, 'jpa', CURRENT_TIMESTAMP),
(4, 'jooq', CURRENT_TIMESTAMP),
(5, 'architecture', CURRENT_TIMESTAMP),
(6, 'golang', CURRENT_TIMESTAMP),
(7, 'general', CURRENT_TIMESTAMP),
(8, 'kubernetes', CURRENT_TIMESTAMP),
(9, 'testing', CURRENT_TIMESTAMP),
(10, 'nodejs', CURRENT_TIMESTAMP)
;

insert into articles(id, title, slug, description, content, author_id, created_at) values
(1, 'How (not) to ask for Technical Help?', 'how-to-not-to-ask-for-technical-help', 'Here I would like share my thoughts on what are the better ways to ask for help? and what are some patterns that you should avoid while asking for help.', 'Here I would like share my thoughts on what are the better ways to ask for help? and what are some patterns that you should avoid while asking for help.', 1, '2020-01-01'),
(2, 'Getting Started with Kubernetes', 'getting-started-with-kubernetes', 'In this article we will learn Creating a docker image from a SpringBoot application, Local kubernetes setup using Minikube, Run the SpringBoot app in a Pod, Scaling the application using Deployment, Exposing the Deployment as a Service', 'In this article we will learn Creating a docker image from a SpringBoot application, Local kubernetes setup using Minikube, Run the SpringBoot app in a Pod, Scaling the application using Deployment, Exposing the Deployment as a Service', 1, '2020-01-02'),
(3, 'Few Things I learned in the HardWay in 15 years of my career', 'few-things-i-learned-the-hardway-in-15-years-of-my-career', 'I started my career as a Java developer in 2006, and it is almost 15 years that I have been continuing as a software developer', 'I started my career as a Java developer in 2006, and it is almost 15 years that I have been continuing as a software developer. Along the way I have experienced some amazing things, and a lot of shitty things as well. As I am not a super smart person by birth I ended up learning many things in the HardWay. In this article I would like to share some of my learnings.', 2, '2020-01-03'),
(4, 'Testing REST APIs using Postman and Newman', 'testing-rest-apis-with-postman-newman', 'Testing REST APIs using Postman and Newman', 'Testing REST APIs using Postman and Newman https://sivalabs.in/testing-rest-apis-with-postman-newman', 1, '2020-01-04'),
(5, 'Why SpringBoot?', 'why-springboot', 'Why SpringBoot?', 'https://sivalabs.in/why-springboot', 1, '2020-01-05'),
(6, 'Testing SpringBoot Applications', 'spring-boot-testing', 'Testing SpringBoot Applications', 'Testing SpringBoot Applications https://sivalabs.in/spring-boot-testing', 1, '2020-01-06'),
(7, 'Imposing Code Structure Guidelines using ArchUnit', 'impose-architecture-guidelines-using-archunit', 'Imposing Code Structure Guidelines using ArchUnit', 'Imposing Code Structure Guidelines using ArchUnit https://sivalabs.in/impose-architecture-guidelines-using-archunit', 2, '2020-01-07'),
(8, 'One-Stop Guide to Mapping with MapStruct', 'one-stop-guide-to-mapping-with-mapstruct', 'One-Stop Guide to Mapping with MapStruct', 'One-Stop Guide to Mapping with MapStruct https://reflectoring.io/reactive-architecture-with-spring-boot', 1, '2020-01-08'),
(9, 'Getting Started with Spring WebFlux', 'getting-started-with-spring-webflux', 'Getting Started with Spring WebFlux', 'Getting Started with Spring WebFlux https://reflectoring.io/getting-started-with-spring-webflux', 1, '2020-01-09'),
(10, 'Complete Guide to the Immutables Java Library', 'immutables-library', 'Complete Guide to the Immutables Java Library', 'Complete Guide to the Immutables Java Library https://reflectoring.io/immutables-library', 4, '2020-01-10'),
(11, 'Tracing with Spring Boot, OpenTelemetry, and Jaeger', 'spring-boot-tracing', 'Tracing with Spring Boot, OpenTelemetry, and Jaeger', 'Tracing with Spring Boot, OpenTelemetry, and Jaeger https://reflectoring.io/spring-boot-tracing', 2, '2020-01-11'),
(12, 'Laws and Principles of Software Development', 'laws-and-principles-of-software-development', 'Laws and Principles of Software Development', 'Laws and Principles of Software Development https://reflectoring.io/laws-and-principles-of-software-development', 3, '2020-01-12'),
(13, 'Authentication and Authorization in a Node API using Fastify, tRPC and Supertokens', 'authentication-and-authorization-in-a-node-api-using-fastify-trpc-and-supertokens-3cgn', 'Authentication and Authorization in a Node API using Fastify, tRPC and Supertokens', 'Authentication and Authorization in a Node API using Fastify, tRPC and Supertokens https://dev.to/franciscomendes10866/authentication-and-authorization-in-a-node-api-using-fastify-trpc-and-supertokens-3cgn', 1, '2020-01-13'),
(14, 'Golang tutorial series', 'learn-golang-series', 'Golang tutorial series', 'Golang tutorial series https://golangbot.com/learn-golang-series/', 4, '2020-01-14')
;

insert into article_favorite(article_id, user_id, created_at) VALUES
(1, 3, CURRENT_TIMESTAMP),
(1, 4, CURRENT_TIMESTAMP),
(1, 5, CURRENT_TIMESTAMP),
(2, 3, CURRENT_TIMESTAMP),
(2, 4, CURRENT_TIMESTAMP),
(4, 3, CURRENT_TIMESTAMP)
;

insert into article_tag(article_id, tag_id, created_at) VALUES
(1, 7, CURRENT_TIMESTAMP),
(2, 8, CURRENT_TIMESTAMP),
(3, 7, CURRENT_TIMESTAMP),
(4, 9, CURRENT_TIMESTAMP),
(5, 1, CURRENT_TIMESTAMP),(5, 2, CURRENT_TIMESTAMP),
(6, 1, CURRENT_TIMESTAMP),(6, 2, CURRENT_TIMESTAMP),(6, 9, CURRENT_TIMESTAMP),
(7, 5, CURRENT_TIMESTAMP),
(8, 1, CURRENT_TIMESTAMP),
(9, 2, CURRENT_TIMESTAMP),
(10, 1, CURRENT_TIMESTAMP),
(11, 1, CURRENT_TIMESTAMP),(11, 2, CURRENT_TIMESTAMP),
(12, 7, CURRENT_TIMESTAMP),
(13, 10, CURRENT_TIMESTAMP),
(14, 6, CURRENT_TIMESTAMP)
;

insert into comments(id, article_id, author_id, content, created_at) VALUES
(1, 1, 4, 'Nice article', CURRENT_TIMESTAMP),
(2, 1, 5, 'Nice article, thanks for sharing', CURRENT_TIMESTAMP),
(3, 2, 5, 'Thanks for sharing', CURRENT_TIMESTAMP)
;