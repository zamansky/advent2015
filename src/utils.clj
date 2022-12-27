(ns utils
  (:require [clojure.java.io :as io]
            [clj-http.client :as client]
            [hashp.core ]
            [clojure.edn :as edn]))

(defn download-data
  "if file doesn't already exist, download the data and store in
  data/day#.dat"
  [year day]
  (let [fname (format "day%02d.dat" day)
        url (str "https://adventofcode.com/" year "/day/" day "/input")
        cookie "session=53616c7465645f5ff2f2ea0388b04abc20195ecbb99e518565324766a302dcc3515588dc96addae64b8b5c68ff40b0278d48d170f284d747d9391e77ad397f45"
        outfile (format "data/%s" fname)
        ]
    (if (not (.exists (io/file outfile)))
      (let [data (:body (client/get url {:headers {:Cookie cookie}}))]
        (spit outfile data))
      "File already exists"
      )))
    


(defn transpose [m]
  (apply mapv vector m))

(defn load-data [year day]
  (download-data year day)
  (let [fname (format "day%02d.dat" day)]
  (slurp (str "data/" fname))))

(defn parse-bigint [s]
  "string->int"
  (biginteger (re-find  #"\-?\d+" s )))



(defn parse-int
  "string->int"
  ([s] (parse-int s nil))
  ([s default]
  (try (Integer. (re-find  #"\-?\d+" s ))
       (catch Exception r default))
  ))



(defn char->int [c]
  (Long/parseLong (String/valueOf c)))

(defn gcd [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn lcm [a b]
  (/ (abs (* a b))
     (gcd a b)))
