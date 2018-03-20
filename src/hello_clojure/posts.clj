(ns hello-clojure.posts
  (:use [korma.db]
        [korma.core])
  (:import java.util.Date)
  (:require [clojure.string :as str]))

(defdb pg (postgres {:db "posts_database"
                       :user "postgres"
                       :password ""
                       :host "db"
                       :port "5432"}))

(defentity posts
  (entity-fields :id :title :abstract :body :createdAt))

; posts: [Post]
(def posts (-> (select posts)
               (order :createdAt)))

; posts(createdAt: Date): [Post]
(defn posts-by-date
  [date]
  (-> (select posts)
      (where {:createdAt date})
      (order :createdAt)))

; posts(startDate: Date, endDate: Date): [Post]
(defn posts-by-date
  [start-date end-date]
  (-> (select posts)
      (where (and {:createdAt [> start-date]
                   :createdAt [< end-date]}))
      (order :createdAt)))

(defn offset
  [limit page]
  (* limit (- page 1)))

; posts(limit: Int, page: Int): [Post]
(defn posts
  [limit page]
  (-> (select posts)
      (order :createdAt)
      (limit limit)
      (offset (offset page))))

; post(id: Int): Post
(defn post
  [id]
  (-> (select posts)
      (where {:id id})
      (limit 1)))

(defn today
  []
  (new Date))

; createPost(title: String, abstract: String, body: String)
(defn create
  [title abstract body]
  (insert posts
    (values {:title title :abstract abstract :body body :createdAt (today)})))
