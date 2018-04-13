(ns hello-clojure.core
  (:require [compojure.core :refer :all]
            [ring.middleware.reload :as reload]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-body]]
            [cheshire.core :refer :all]
            [hello-clojure.graphql :as cgraphql]
            [org.httpkit.server :refer [run-server]]))

(defroutes app-routes
  (GET "/" []
       {:status 200
        :headers {"Content-Type" "application/json"}
        :body "Hey. POST JSON to /sum, with {\"numbers:\" [1,2,3,4]}"})

  (GET "/endpoint" req
    (let [query (or (get-in req [:body :query])
                    (get-in req [:params :query])
                    [])
          variables (or (get-in req [:body :variables])
                        (get-in req [:params :variables])
                        [])
          operation_name (or (get-in req [:body :operationName])
                             (get-in req [:params :operationName]))]
      (let [response {:query query
                      :variables variables
                      :operation_name operation_name
                      :data (cgraphql/execute query variables operation_name)}]
        {:status 200
         :headers {"Content-Type" "application/json"}
         :body (generate-string response)})))

  (POST "/endpoint" req
    (println req)
    (let [query (or (get-in req [:body :query])
                    (get-in req [:params :query])
                    [])
          variables (or (get-in req [:body :variables])
                        (get-in req [:params :variables])
                        [])
          operation_name (or (get-in req [:body :operationName])
                             (get-in req [:params :operationName])
                             [])]
      (let [response {:query query
                      :variables variables
                      :operation_name operation_name
                      :data (cgraphql/execute query variables operation_name)}]
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
  (run-server (site #'app-routes) {:port 9000 :ip "0.0.0.0"}))
