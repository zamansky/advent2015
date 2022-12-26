(ns day16
  (:require
   [utils :as u]
   [hashp.core]
   [clojure.string :as str]
   [com.rpl.specter :as sp]
   [clojure.set :as set]
   [clojure.edn :as edn]
   [clojure.spec.alpha :as spec]))

(def data (slurp "data/day16.dat"))



(comment

  Part 1 specs

(spec/def :sue/children #(or (nil? (:children %)) (= (:children %) 3)))
(spec/def :sue/cats #(or (nil? (:cats %))(= (:cats %) 7)))
(spec/def :sue/samoyeds #(or (nil? (:samoyeds %)) (= (:samoyeds %) 2 )))
(spec/def :sue/pomeranians  #(or (nil? (:pomeranians %)) (= (:pomeranians %) 3 )))
(spec/def :sue/akitas  #(or (nil? (:akitas %)) (= (:akitas %) 0 )))
(spec/def :sue/vizslas  #(or (nil? (:vizslas %)) (= (:vizslas %) 0 )))
1(spec/def :sue/goldfish  #(or (nil? (:goldfish %)) (= (:goldfish %) 5 )))
(spec/def :sue/trees  #(or (nil? (:trees %)) (= (:trees %) 3 )))
(spec/def :sue/cars  #(or (nil? (:cars %)) (= (:cars %) 2 )))
(spec/def :sue/perfumes  #(or (nil? (:perfumes %)) (= (:perfumes %) 1 )))
)



(comment

  part 2 specs

(spec/def :sue/children #(or (nil? (:children %)) (= (:children %) 3)))
(spec/def :sue/cats #(or (nil? (:cats %))(> (:cats %) 7)))
(spec/def :sue/samoyeds #(or (nil? (:samoyeds %)) (= (:samoyeds %) 2 )))
(spec/def :sue/pomeranians  #(or (nil? (:pomeranians %)) (< (:pomeranians %) 3 )))
(spec/def :sue/akitas  #(or (nil? (:akitas %)) (= (:akitas %) 0 )))
(spec/def :sue/vizslas  #(or (nil? (:vizslas %)) (= (:vizslas %) 0 )))
1(spec/def :sue/goldfish  #(or (nil? (:goldfish %)) (< (:goldfish %) 5 )))
(spec/def :sue/trees  #(or (nil? (:trees %)) (> (:trees %) 3 )))
(spec/def :sue/cars  #(or (nil? (:cars %)) (= (:cars %) 2 )))
(spec/def :sue/perfumes  #(or (nil? (:perfumes %)) (= (:perfumes %) 1 )))

  )

(spec/def :sue/present (spec/and
                        :sue/children
                        :sue/cats
                        :sue/samoyeds
                        :sue/pomeranians
                        :sue/akitas
                        :sue/vizslas
                        :sue/goldfish
                        :sue/trees
                        :sue/cars
                        :sue/perfumes))


(defn convert [[_ k v]]
  [(keyword k) (read-string v)]
  )
(defn parse [line]
  (let [[_ num  rest] (re-find #"Sue (\d+):(.*)" line)
        num (read-string num)
        rest (re-seq #"([a-z]+): (\d+)" rest)
        rest (map convert rest)
        dict (reduce (fn [acc [k v]] (assoc acc k v)) {} rest)
        ]
    dict))

(defn answer [data]
  (->> (str/split-lines data)
                        (map parse)
                        (map #(spec/valid? :sue/present %))
                        (map-indexed (fn [k v] [k v]))
                        (filter #(= (second %) true))
                        first
                        first
                        inc))



(spec/valid? :sue/present {:cats 8 :akitas 1 :vizslas 8})
;; Sue 129: cats: 8, akitas: 1, vizslas: 8
dataq
