(ns day17
    (:require
   [utils :as u]
   [hashp.core]
   [clojure.string :as str]
   [clojure.java.io :as io]
   [clojure.edn :as edn]
   [clojure.set :as set]
   [clojure.math :as m]
   [clojure.math.numeric-tower :as math :refer [expt abs]]
   [clojure.math.combinatorics :as comb]
   ))

;; (u/load-data 2015 17)

(def data (slurp "data/day17.dat"))

(def sample [20 15 10 5 5])

(def data [11 30 47 31 32 36 3 1 5 3 32 36 15 11 46 26 28 1 19 3])

(def a (atom [] ) )

(defn part1 [containers target used]
  (cond (= target 0)
        (do
          (print used)
          (swap! a concat (list used))
          1
          )
        (empty? containers)
        0

        :else 
        (+
         (part1 (rest containers) (- target (first containers)) (conj used (first containers)))
         (part1 (rest containers) target used))))


(defn part2 [containers used target]
  (cond (= target 0)
        used
        (empty? containers)
        []

        :else 
        (cons
         (part2 (rest containers) (cons used [(first containers)]  ) (- target (first containers)))
         (part2 (rest containers) used  target))))


(part1 sample 25)
(part1 data 150)


