(ns hello-clojure.posts
  (:use [korma.db]
        [korma.core])
  (:require [clojure.string :as str]))

(defdb pg (postgres {:db "posts_database"
                       :user "postgres"
                       :password ""
                       :host "db"
                       :port "5432"}))

(defentity posts
  (entity-fields :id :title :abstract :body :createdAt))
