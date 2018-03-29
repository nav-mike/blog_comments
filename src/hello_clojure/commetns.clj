(ns hello-clojure.comments
  (:use [korma.db]
        [korma.core])
  (:import java.util.Date)
  (:require [clojure.string :as str]))

(defdb pg (postgres {:db "comments_database"
                       :user "postgres"
                       :password ""
                       :host "db"
                       :port "5432"}))

(defentity comments
  (entity-fields :id :body :created_on :post_id :author))

; GraphQL methods

; comments(postId: Int!): [Comment]
(defn comments [post_id]
  (-> (select comments)
      (where {:post_id post_id})
      (order :created_on)))

; comments(postId: Int!, createdOn: String!): [Comment]
(defn comments-by-date [post_id created_on]
  (-> (select comments)
      (where (and {:post_id post_id
                   :created_on created_on}))
      (order :created_on)))

; comments(postId: Int!, startDate: String!, endDate: String!): [Comment]
(defn comments-by-date [post_id start_date end_date]
  (-> (select comments)
      (where (and {:post_id post_id
                   :created_on [> start_date]
                   :created_on [< end_date]}))
      (order :created_on)))

(defn offset
  [limit page]
  (* limit (- page 1)))

; comments(postId: Int!, limit: Int!, page: Int!): [Comment]
(defn comments [post_id limit page]
  (-> (select comments)
      (where {:post_id post_id})
      (order :created_on)
      (limit limit)
      (offset (offset page))))

; comment(id: Int!): Comment
(defn comment [id]
  (-> (select comments)
      (where {:id id})
      (limit 1)))

; createComment(body: String!, postId: Int!, author: String!)
(defn create [body, post_id, author]
  (insert comments
    (values {:body body :post_id post_id :author author})))
