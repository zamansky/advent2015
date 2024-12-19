(ns day19
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

;; (u/load-data 2015 19)

(def data (slurp "data/day19.dat"))

(def raw-rules (first (str/split data #"\n\n")))
(def molecule (second (str/split data #"\n\n")))

(def rules (->> raw-rules
     str/split-lines
     (mapv #(re-find  #"(.*) => (.*)" %))
     (mapv #(drop 1 %))
     ))


(def rules [["H" "HO"] ["H" "OH"] ["O" "HH"]])

(defn generate-replacements [word s d]
  (loop [i 0 r (str/index-of word s) res []]
    (if (nil? r)
      res
      (recur r
             (str/index-of word s (inc r))
             (conj res (apply str (concat (apply str(take r word)) d (drop (+ (count s)r) word))))))))


(generate-replacements "HOH" "H" "HO")
(generate-replacements "HOH" "H" "OH")
(generate-replacements "HOH" "O" "H")
(apply str (concat (apply str (take 0 "HOH")) "OH" (drop 1 "HOH")))

(defn gen-reps [word rules]
  (into #{} (flatten
  (for [[s d] rules] 
    (generate-replacements word s d)))))

(gen-reps "HOH" rules)
(count (gen-reps "HOHOHO" rules))

(count (gen-reps molecule rules))
// 674 too high
