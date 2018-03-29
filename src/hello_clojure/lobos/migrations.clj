(ns hello_clojure.lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema
                config helpers)))

; create comments table
(defmigration add-comments-table
  (up [] (create
    (tbl :comments
      (text :body)
      (integer :post_id)
      (varchar :author))))
  (down [] (drop (table :comments))))
