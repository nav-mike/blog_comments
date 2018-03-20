(ns hello-clojure.graphql
  (:require [graphql-clj.executor :as executor]))

; Load schema from .graphql
(defn schema
  []
  (slurp "./src/hello_clojure/graphql/index.graphql"))
