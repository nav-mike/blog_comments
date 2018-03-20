(ns hello-clojure.graphql
  (:require [graphql-clj.executor :as executor]
            [hello-clojure.posts :as posts]))

; Load schema from .graphql
(defn schema
  []
  (slurp "./src/hello_clojure/graphql/index.graphql"))

; Schema string
(def schema-str
  (schema))
