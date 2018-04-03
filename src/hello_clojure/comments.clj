(ns hello-clojure.comments
  (:require [toucan.db :as db]
            [toucan.models :as models]
            [clj-time.core :as t]
            [clj-time.format :as f])
  (:import java.sql.Timestamp))

(db/set-default-db-connection!
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "//db:5432/comments_database"
   :user "postgres"})

(models/defmodel Comment :comments)

; Helpers

(defn offset-value [limit page] (* limit (- page 1)))

(def date-formatter (f/formatter "yyyy.MM.dd"))

(defn date-parser [date-string] (f/parse date-formatter date-string))

(defn start-date [date-string] (t/minus (date-parser date-string) (t/days 1)))

(defn end-date [date-string] (t/plus (date-parser date-string) (t/days 1)))

(defn build-timestamp [date] (Timestamp. (. date getMillis)))

; GraphQL

; comments(postId: Int!): [Comment]
(defn where-post [post_id] (Comment :post_id post_id))

; comments(postId: Int!, createdOn: String!): [Comment]
(defn where-post-date
  [post_id created_on]
  (db/select Comment :created_on [:> (build-timestamp (start-date created_on))
                                  :< (build-timestamp (end-date created_on))]
                     :post_id post_id))

; comments(postId: Int!, startDate: String!, endDate: String!): [Comment]
(defn where-post-dates
  [post_id start_date end_date]
  (db/select Comment :created_on [:> (build-timestamp (date-parser start_date))
                                  :< (build-timestamp (date-parser end_date))]
                     :post_id post_id))

; comments(postId: Int!, limit: Int!, page: Int!): [Comment]
(defn where-pagination
  [post_id limit page]
  (db/select Comment :post_id post_id {:limit limit
                                       :offset (offset-value limit page)
                                       :order_by "created_on"}))

; comment(id: Int!): Comment
(defn find-by-id [id] (Comment id))

; createComment(body: Strin!, postId: String!, author: String!)
(defn create-with
  [body post_id author]
  (db/insert! Comment {:body body :post_id post_id :author author}))
