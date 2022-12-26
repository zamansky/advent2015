;;(ns example
;;(:require [clojure.data.json :as json]))

(def input (slurp "advent12.txt"))
(defn parse-int [s] (Integer/parseInt s))

(require '[clojure.data.json :as json])
(require 'clojure.data)
(require 'clojure.data.json)
(def d (json/read-str input))



;; part 1 - just a cheesy regular expression to pull the numbers
(apply +  (map parse-int  (re-seq #"-?\d+" input)))


;; ok - part 2 we have to traverse

(defn parse-map
  [node]
  (let [v (clojure.set/map-invert node)]
    print v
    (if (contains? v "red" )
      0
      (apply + (map parse (vals node))))
    ))

(defn parse
  [node]
  (cond
    (number? node) node
    (vector? node) (apply + (map parse node))
    (map? node)  (parse-map node)
    :default 0
    ))

(def test "[1,2,3,{\"a\" : \"red\",2:3} ,4]")






;; (some #(= "red" %) (1 2 3))




