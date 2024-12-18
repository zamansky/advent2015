(ns day18
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

;; (u/load-data 2015 18)

(def data (slurp "src/day18.dat"))

(def sample  ".#.#.#
...##.
#....#
..#...
#.#..#
####..")


(defn string->2dpoints [s convmap]
  (let [grid (vec (map vec (str/split-lines s)))
        rows (count grid)
        cols (count (first grid))
        points (for [r (range rows) c (range cols)]
                 [r c (convmap (get-in grid [r c]))])
        ]
    (reduce (fn [d [r c val]]
              (assoc d [r c] val)) {} points)))


(def neighbors6 [[-1 -1] [-1 0] [-1 1]
                 [0 -1] [0 1]
                 [1 -1] [1 0] [1 1]])

(defn count-neighbors [point board]
  (let [test-points (map (partial mapv + point) neighbors6)
        ]
    (->> (map board test-points)
         (filter #(= % :on))
         count
         )))

(defn get-next-gen [board]
  (reduce-kv (fn [d k v]
               (let [neighbors (count-neighbors k board)]
                 (cond
                   (and (= v :on) (or (= neighbors 2) (= neighbors 3)))
                   (assoc d k :on)

                   (= v :on)
                   (assoc d k :off)

                   (and (= v :off) (= neighbors 3))
                   (assoc d k :on)

                   :else
                   (assoc d k :off))
               )) {} board))



(defn get-next-gen2 [board]
  (reduce-kv (fn [d k v]
               (let [neighbors (count-neighbors k board)]
                 (cond
                   (or (= k [0 0])
                       (= k [(dec ROWS) (dec COLS)])
                       (= k [0 (dec COLS)])
                       (= k [(dec ROWS) 0]))
                   (assoc d k :on)
                          

                   
                   (and (= v :on) (or (= neighbors 2) (= neighbors 3)))
                   (assoc d k :on)

                   (= v :on)
                   (assoc d k :off)

                   (and (= v :off) (= neighbors 3))
                   (assoc d k :on)

                   :else
                   (assoc d k :off))
               )) {} board))




(def ROWS (count (str/split-lines data)))
(def COLS (count (first (str/split-lines data))))
(def b (string->2dpoints data {\# :on \. :off}))

(def ROWS (count (str/split-lines sample)))
(def COLS (count (first (str/split-lines sample))))
(def b (string->2dpoints sample {\# :on \. :off}))

(defn print-board [board rows cols]
  (let [empty (vec (for [line (range rows)]
                (vec (repeat cols \.))))]
    (reduce (fn [b [k val]]
              (assoc-in b k val))
            empty board)))
       

(print-board b ROWS COLS)


(get-neighbors [1 4] b)

