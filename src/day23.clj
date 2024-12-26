(ns day23
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

;; (u/load-data 2023 23)

(def data (slurp "src/day23.dat"))
(def sample (slurp "src/sample23.dat"))

(def state {\a 1 \b 0 :ip 0})

(defn doop [opcode reg offset state]
  (case opcode
    :hlf (-> (update state reg #(/ % 2))
             (update :ip inc))
    :tpl (-> (update state reg #(* % 3))
             (update :ip inc))
    :inc (-> (update state reg inc)
             (update :ip inc))
    :jmp (update state :ip #(+ % reg))
    :jie (if (even? (state reg))
           (update state :ip #(+ % offset))
           (update state :ip inc))
    :jio (if (= 1  (state reg))
           (update state :ip #(+ % offset))
           (update state :ip inc))))

(defn parse [[op reg &rest]]
  [(keyword op) (if (= op "jmp") (edn/read-string reg) (first reg)) (edn/read-string &rest)])

(defn data->program [data]
(->> (str/split-lines data)
     (map #(str/split % #" ") )
     (map #(parse %))))

(data->program sample)

(def program (data->program data))

 
(loop [ip (state :ip) state state]
  (let [ [op reg offset] (nth program ip [nil nil nil])]
    (if (or (> 0 ip) (>=  ip   (count program)))
    state
    (let [state (doop  op  reg offset state)]
      (recur (state :ip) state)))))

