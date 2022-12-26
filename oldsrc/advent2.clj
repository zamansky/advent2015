(require '[adventofcodeclojure.utils :as utils])
(use '[clojure.string :only (join split)])



(def package-list (get-lines "advent2.txt"))

(defn parse-int [s] (Integer/parseInt s))

(defn list-int
  "convert a list of strings to a list of ints"
  [l]
  (map parse-int l))

(defn dimstring_to_dimvector
  [l]
  (map (fn [x] (split x #"x")) l))



(defn calc-paper
  "convert a triple into the amount of paper we need"
  [lst]
  (let [[l w h] lst
        dims [ (* l w) (* w h) (* h l) ] ]
    (+ (* 2 l w) (* 2 w h) (* 2 h l) (apply min dims))
    ))

;; put it all together
(->> (dimstring_to_dimvector package-list)
     (map list-int)
     (map calc-paper)
     (reduce +))


(->> (dimstring_to_dimvector package-list)
     (map list-int)
     (map sort)
     (map (fn [x]
            (let [ [a b c] x ]
              (+ a a b b (apply * x)))))
     (reduce +))
