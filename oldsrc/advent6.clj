(require '[adventofcodeclojure.utils :as utils])
(use '[clojure.string :only (join split)])
(set! *unchecked-math* true)



(def instruction-strings (utils/get-lines "advent6.txt"))

;;(def re #"([a-z ]*) ([0-9]+,[0-9]+) through ([0-9]+,[0-9]+)")
(def re #"(turn on|turn off|toggle) ([0-9]*,[0-9]*) through ([0-9]*,[0-9]*)")

(def cmd "turn on 489,959 through 759,964")

(defn parse-int [s] (Integer/parseInt s))

(defn list-int
  "convert a list of strings to a list of ints"
  [l]
  (map parse-int l))

(defn toggle
  [a x1 x2 y1 y2]
  (doseq [x (range x1 (inc x2))
          y (range (+ y1 (* x 1000)) (+ y2 (* x 1000) 1))]
    (aset a y (inc(inc (aget a y))))))


(defn on
  [a x1 x2 y1 y2]
  (doseq [x (range x1 (inc x2))
          y (range (+ y1 (* x 1000)) (+ y2 (* x 1000) 1))]
    (aset a y (inc (aget a y)))))

(defn off
  [a x1 x2 y1 y2]
  (doseq [x (range x1 (inc x2))
          y (range (+ y1 (* x 1000)) (+ y2 (* x 1000) 1))]
    (aset a y
          (max (dec (aget a y)) 0))))


(defn string-to-command
  [s]
  (let [v1 (rest(first (re-seq re s)))
        [cstring c1s c2s] v1 ; c1s and c2s are now csv
        c1 (list-int (split c1s #","))
        c2 (list-int (split c2s #","))
        command (case cstring
                  "toggle" toggle
                  "turn on" on
                  "turn off" off)
        ]
    [command c1 c2]
    ))


(defn do-command
  "Loop over a single command rectangle and do it"
  [a s]
  (let [[command c1 c2] (string-to-command s)
        [x1 y1] c1
        [x2 y2] c2
        ]
    
    (command a x1 x2 y1 y2)))


(def a (int-array 1000000))

(defn doit
  [] (doseq [c instruction-strings]
       (do-command a c)))

;;(count (filter (fn [x] (= x 1))  a))
(defn count-on [grid]
  (areduce grid i cnt 0 (+ cnt (aget grid i))))

