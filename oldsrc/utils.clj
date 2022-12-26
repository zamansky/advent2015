(ns adventofcodeclojure.utils)

(use 'clojure.java.io)
(defn get-lines [fname]
  (with-open [r (reader fname)]
    (doall (line-seq r))))
