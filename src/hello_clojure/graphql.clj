(ns hello-clojure.graphql
  (:require [graphql-clj.executor :as executor]
            [hello-clojure.comments :as comments]
            [clojure.core.match :as match]))

; Load schema from .graphql
(defn schema
  []
  (slurp "./src/hello_clojure/graphql/index.graphql"))

; Schema string
(def schema-str
  (schema))

; methods
(defn create-comment [args]
  (let [body (get args "body")
        post_id (get args "postId")
        author (get args "author")]
    (comments/create-with body post_id author)))

(defn comment [args]
  (let [id (get args "id")]
    (comments/find-by-id id)))

(defn comments-by-post [args]
  (let [post_id (get args "postId")]
    (comments/where-post post_id)))

(defn comments-by-date [args]
  (let [post_id (get args "postId")
        created_on (get args "createdOn")]
    (comments/where-post-date post_id created_on)))

(defn comments-by-dates [args]
  (let [post_id (get args "postId")
        start_date (get args "startDate")
        end_date (get args "endDate")]
    (comments/where-post-dates post_id start_date end_date)))

(defn comments-with-limit [args]
  (let [post_id (get args "postId")
        limit (get args "limit")
        page (get args "page")]
    (comments/where-pagination post_id limit page)))

; Resolver
(defn resolver-fn [type_name field_name]
  (match/match
    [type_name field_name]
    ["Query" "commentsByPost"] (fn [context parent args]
                                 (comments-by-post args))
    ["Query" "commentsByDate"] (fn [context parent args]
                                 (comments-by-dates args))
    ["Query" "commentsByDates"] (fn [context parent args]
                                  (comments-by-dates args))
    ["Query" "commentsWithLimit"] (fn [context parent args]
                                     (comments-with-limit args))
    ["Query" "comment"] (fn [context parent args]
                          (comment args))
    ["Mutation" "createComment"] (fn [context parent args]
                                   (create-comment args))
    :else nil))
