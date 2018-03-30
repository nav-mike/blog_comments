(ns lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema
                config helpers)))

; (use 'lobos.core 'lobos.connectivity 'lobos.migration 'lobos.config 'lobos.migrations)
; (migrate)

; create comments table
(defmigration add-comments-table
  (up [] (create
    (tbl :comments
      (text :body)
      (integer :post_id)
      (varchar :author 100))))
  (down [] (drop (table :comments))))
