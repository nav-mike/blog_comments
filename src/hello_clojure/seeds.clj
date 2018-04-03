(ns hello-clojure.seeds
  (:require [hello-clojure.comments :as comments]))

(defn build-comment [index]
  (comments/create-with (str "Some comment" index) (inc (rand-int 2)) (str "author" index)))

(defn builder [] (dotimes [n (inc (rand-int 10))] (build-comment (inc n))))
