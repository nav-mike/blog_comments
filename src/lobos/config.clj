(ns lobos.config
  (:use lobos.connectivity))

(def db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :user "postgres"
   :password ""
   :subname "//db:5432/comments_database"})

(open-global db)
