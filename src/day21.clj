(ns day21
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

(def weapons
  [{:name "Dagger" :cost 8 :damage 4 :armor 0}
   {:name "Shortsword" :cost  10 :damage    5 :armor    0}
   {:name "Warhammer"  :cost  25 :damage    6 :armor    0}
   {:name "Longsword"  :cost  40 :damage    7 :armor    0}
   {:name "Greataxe"   :cost  74 :damage    8 :armor    0}])
(def armor
  [{:name "none" :cost 0 :damage 0 :armor 0}
   {:name "Leather"     :cost 13  :damage   0 :armor 1}
   {:name "Chainmail"   :cost 31  :damage   0 :armor 2}
   {:name "Splintmail"  :cost 53  :damage   0 :armor 3}
   {:name "Bandedmail"  :cost 75  :damage   0 :armor 4}
   {:name "Platemail"   :cost 102 :damage   0 :armor 5}])

(def rings1
  [{:name "none" :cost 0 :damage 0 :armor 0}
  { :name "Damage +1"  :cost  25 :damage    1 :armor 0}
  { :name "Damage +2"  :cost  50 :damage    2 :armor 0}
  { :name "Damage +3"  :cost 100 :damage    3 :armor 0}
  { :name "Defense +1" :cost  20 :damage    0 :armor 1}
  { :name "Defense +2" :cost  40 :damage    0 :armor 2}
  { :name "Defense +3" :cost  80 :damage    0 :armor 3}])

(def rings2
  [{:name "none" :cost 0 :damage 0 :armor 0}
  { :name "Damage +1"  :cost  25 :damage    1 :armor 0}
  { :name "Damage +2"  :cost  50 :damage    2 :armor 0}
  { :name "Damage +3"  :cost 100 :damage    3 :armor 0}
  { :name "Defense +1" :cost  20 :damage    0 :armor 1}
  { :name "Defense +2" :cost  40 :damage    0 :armor 2}
  { :name "Defense +3" :cost  80 :damage    0 :armor 3}])

(def data  (for [w weapons
      a armor
      r1 rings1
      r2 rings1]
                [w a r1 r2]))
(def sorted-data(sort-by first (map 
#(eval (conj (conj  (map (fn [ {:keys [cost damage armor]}] [cost damage armor]) %) +) map)) data)))

;; 104 d:8 a:1

(defn sim [hp [cost damage armor] [boss-hp boss-damage boss-armor]]
  (loop [hp hp boss-hp boss-hp]
    (cond

      (<= boss-hp 0)
      true
      (<= hp 0)
      false


      :else
      (recur  (- hp (max (-  boss-damage armor)0))  (- boss-hp (max 0 (-  damage  boss-armor))
      )))))


(reduce (fn [c [cost damage armor :as next]]
          (if (sim 100 next [104 8 1])
            (reduced cost)
            cost)) sorted-data)


(reduce (fn [c [cost damage armor :as next]]
          (if (not (sim 100 next [104 8 1]))
            (reduced cost)
            cost)) (reverse sorted-data))

