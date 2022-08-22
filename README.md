# movieLibrary
This is a REST API to manage, search and like/dislike movies.

# Requirements
Java
Maven
Lombok
SpringBoot
MySQL

# Installation
To install project dependencies run:
`mvn install`

Create `movie` table in your local database with:
```sql
CREATE TABLE `movie` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `description` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `release_year` varchar(100) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `likes` int unsigned DEFAULT '0',
  `dislikes` int unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```
# Run
Run this command:
`mvn spring-boot:run`

# Test
You can see this document for API testing: https://documenter.getpostman.com/view/22930357/VUqrMGKH
