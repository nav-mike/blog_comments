(ns hello_clojure.lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema
                config helpers)))

; create posts table
(defmigration add-posts-table
  (up [] (create
    (tbl :posts
      (text :body)
      (integer :post_id)
      (varchar :author))))
  (down [] (drop (table :posts))))
