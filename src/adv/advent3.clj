(require '[adventofcodeclojure.utils :as utils])
(use '[clojure.string :only (join split)])





(def dirs (first (utils/get-lines "advent3.txt")))

;; (def dirs "^^<>>^v")

(defn todeltas
  [l]
  (map (fn [x]
         (cond
           (= x \v) '[0 1]
           (= x \^) '[0 -1]
           (= x \>) '[1 0]
           (= x \<) '[-1 0]
           )
         ) l) )

(defn addv
  [[a1 a2] [b1 b2]]
  [ (+ a1 b1) (+ a2 b2)]
  )

(def d (todeltas dirs))
                                        ; reduce  f val col 
(defn redfunc
  [a b]
  (cons (addv (first a) b) a)
  )

(count (set (reduce redfunc [[0 0]] d)))
                                        ; set it
(def twos (partition 2 d ))
(def santa (map (fn [x] (first x)) twos))
(def robot (map (fn [x] (second x)) twos))

(count (clojure.set/union
        (set (reduce redfunc [[0 0]] santa))
        (set (reduce redfunc [[0 0]] robot))))
