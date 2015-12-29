(use '[clojure.string :only (join split replace)])
(defn parse-int [s] (Integer/parseInt s))

(def input (split (slurp  "advent8.txt") #"\n" ))


(def charcode #"\\x[A-Z0-9a-f][A-Z0-9a-f]")
(def slashcode #"\\\\")
(def quotecode #"\\\"")



(def lines (count input))



(defn makereplregex
  [s c]
  (fn [x] (replace  x s c)))




(def total (reduce + (map count input)))

(def filtered (->> input
                   (map (makereplregex quotecode "Z"))
                   (map (makereplregex slashcode "Y"))
                   (map (makereplregex charcode "X"))
                   
                   ))

(defn countesc
  [x]
  (reduce +(->> input
                (map (partial re-seq x))
                (map count))))


(def ans1 (- total (- (reduce + (map count filtered)) (* 2 lines))))

(def instring (join "" input))
(def ans2pt1 (count (join ""(map (fn [x]
                                   (case x
                                     \" "\\\""
                                     \\ "\\\\"
                                     x
                                     )
                                   ) instring)
                          )))
(def ans2 (- (+ ans2pt1 600) 6202))

