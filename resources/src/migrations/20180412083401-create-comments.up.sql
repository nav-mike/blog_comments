CREATE TABLE comments (
  id SERIAL PRIMARY KEY,
  body TEXT NOT NULL,
  post_id INTEGER NOT NULL,
  author VARCHAR(50) NOT NULL
);
--;;
CREATE INDEX comments_index ON comments(post_id);
