(ns hello-clojure.core
  (:require [compojure.core :refer :all]
            [ring.middleware.reload :as reload]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-body]]
            [cheshire.core :refer :all]
            [graphql-clj.executor :as executor]
            [hello-clojure.graphql :as posts]
            [hello-clojure.posts :as db]
            [org.httpkit.server :refer [run-server]]))

; Define GraphQL schema
(def schema-str "type User {
    name: String
    age: Int
  }

  type QueryRoot {
    user: User
  }

  schema {
    query: QueryRoot
  }")

(defn resolver-fn [type-name field-name]
  (get-in {"QueryRoot" {"user" (fn [context parent args]
                                {:name "test user name"
                                 :age 30})}}
    [type-name field-name]))

(defn sum-values "sum-values" [nums]
  (reduce + nums))

(defroutes app-routes
  (GET "/" []
    (println (posts/schema))
       {:status 200
        :headers {"Content-Type" "application/json"}
        :body "Hey. POST JSON to /sum, with {\"numbers:\" [1,2,3,4]}"})

  (GET "/endpoint" req
    (let [query (or (get-in req [:body :query])
                    (get-in req [:params :query])
                    [])]
      (let [response {:query query
                      :data (executor/execute nil schema-str resolver-fn query)}]
        {:status 200
         :headers {"Content-Type" "application/json"}
         :body (generate-string response)})))

  (POST "/endpoint" req
    (let [query (or (get-in req [:body :query])
                    (get-in req [:params :query])
                    [])]
      (let [response {:query query
                      :data (executor/execute nil schema-str resolver-fn query)}]
        {:status 200
         :headers {"Content-Type" "application/json"}
         :body (generate-string response)})))

  (POST "/sum" req
        (let [numbers (or (get-in req [:body :numbers])
                          (get-in req [:params :numbers])
                          [])]
          (let [response {:numbers numbers
                          :sum (sum-values numbers)}]
            {:status 200
             :headers {"Content-Type" "application/json"}
             :body (generate-string response)})))
  (route/not-found {:status 404
                    :headers {"Content-Type" "application/json"}
                    :body (generate-string {:error "Not found!"})}))

(defn clojure-env []
  (or (get-in (System/getenv) ["CLOJURE_ENV"])
      "development"))

(defn -main []
  (println (str "Running in: " (clojure-env) " mode"))
  (let [handler (if (= (clojure-env) "production")
                  (wrap-json-body (site app-routes))
                  (reload/wrap-reload (wrap-json-body (site #'app-routes) {:keywords? true})))]
  (run-server handler {:port 9000})))
