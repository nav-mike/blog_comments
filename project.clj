(defproject hello-clojure "0.1.0-SNAPSHOT"
  :description "a quick run through of clojure, ring, and docker"
  :url "https://github.com/knomedia/hello-clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.0"]
                 [ring "1.6.3"]
                 [ring/ring-json "0.4.0"]
                 [cheshire "5.8.0"]
                 [http-kit "2.2.0"]
                 [graphql-clj "0.2.6"]]
  :main hello-clojure.core)
