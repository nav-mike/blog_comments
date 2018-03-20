(ns hello_clojure.lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema
                config helpers)))

; create posts table
(defmigration add-posts-table
  (up [] (create
    (tbl :posts
      (varchar :title 100 :unique)
      (varchar :abstract 300)
      (text :body)
      (check :title (> (length :title) 1)))))
  (down [] (drop (table :posts))))
