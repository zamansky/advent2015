(require '[adventofcodeclojure.utils :as utils])
(use '[clojure.string :only (join split)])

(defn countparens
  [s]
  (let [ open (count (filter (fn [c] (= c \( )) s))
        close (count (filter (fn [c] (= c \) )) s))
        ]
    (- open close)))

(defn b2
  [l turn floor]
  (let [ newfloor (+ floor (first l)) ] 
    (if
        (= newfloor 0) turn
        (b2 (rest l) (+ turn 1) newfloor )
        )))

(defn basement
  [s]
  (let [ newlist (map (fn [x] (if (= x \( ) 1 -1)) s) ]
    (b2 newlist 1 1)))


(def s  (first(utils/get-lines "advent1.txt")))


(countparens s)
(basement s)
