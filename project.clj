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
                 [toucan "1.1.4"]
                 [clj-time "0.14.2"]
                 [lobos "1.0.0-beta3"]
                 [org.postgresql/postgresql "9.2-1002-jdbc4"]
                 [org.clojure/java.jdbc "0.7.5"]
                 [graphql-clj "0.2.6"]
                 [log4j "1.2.15" :exclusions [javax.mail/mail
                            javax.jms/jms
                            com.sun.jdmk/jmxtools
                            com.sun.jmx/jmxri]]]
  :main hello-clojure.core)
