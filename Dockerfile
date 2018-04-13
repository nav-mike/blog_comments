FROM clojure:lein-2.8.1
COPY db/init.sql /docker-entrypoint-initdb.d/
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY project.clj /usr/src/app/
RUN lein deps
COPY . /usr/src/app
# CMD ["lein", "run"]
