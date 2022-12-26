(ns day15
  (:require
   [utils :as u]
   [hashp.core]
   [clojure.string :as str]
   [com.rpl.specter :as sp]
   [clojure.set :as set]
   [clojure.edn :as edn]))

(def sample
  "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3")



(defn parse [line]
  (let [[cp dur flav text cal :as vals] (->> (re-seq #"\-?\d" line)
                                    (mapv read-string))
        [name & _] (str/split line #":" )
                 ]
  [name vals]
  ))

(def raw-data "Sprinkles: capacity 2, durability 0, flavor -2, texture 0, calories 3
Butterscotch: capacity 0, durability 5, flavor -3, texture 0, calories 3
Chocolate: capacity 0, durability 0, flavor 5, texture -1, calories 8
Candy: capacity 0, durability -1, flavor 0, texture 5, calories 8")


(def data
  (->> (str/split-lines raw-data)
       (mapv parse)))

(parse (first (str/split-lines sample)))

(defn calc-scores [[name values] qty]
   (map partial * qty) values)
data
(calc-score (first data) 1)


(defn calc-all [data x y z q]
  (->>
   (u/transpose (map second data))
   (map #(map * [x y z q] %))
   drop-last
   (map #(reduce + %))
   (map #(if (> % 0) % 0))
   (apply *)))

(def part1 (apply max (for [x (range 101)
                            y (range 101)
                            z (range 101)
                            q (range 101)
                            :when (= (+ x y z q) 100)]
                        (calc-all data x y z q))))

  (calc-all data 44 56 )


(def candidates  (for [x (range 101)
                       y (range 101)
                       z (range 101)
                       q (range 101)
                       :when  (= (+ x y z q) 100)
                       :when  (= 500 (reduce + (mapv * [x y z q] cals)))]
                   [x y z q]))

(def part2 (apply max (for [[x y z q] candidates
                            :when (= (+ x y z q) 100)]
                        (calc-all data x y z q))))

cals

(reduce + (mapv * [40 60] cals))

(def cals  (map last (map second  data)))
