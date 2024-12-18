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

