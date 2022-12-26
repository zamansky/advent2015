(require '[clojure.string :as String])
(defn parse-int [s] (Integer/parseInt s))


(def input (String/split  (slurp "advent13.txt") #"\n"))

(def splitter
  #"([A-Z][a-z]+).* (lose|gain) (\d+).* to ([A-Z][a-z]+)\.")

(def linetmp

  (map #(drop 1  (re-find splitter %)) input)
  )

(def lines
  (for [ [a b c d] linetmp ]
    (if (= "gain" b)
      [a d (parse-int c)]
      [a d (* -1 (parse-int c))]
      )))

;; build m into the map
(def m 
  (reduce
   (fn [ m [a b c]] (assoc m [a b] c))  {} lines))

;; build  a list of people
(def people  (into #{} (map #(% 0) lines)))

(defn p
  [l sofar]
  (if (empty? l)
    sofar
    (flatten    (for [x l]
                  (let [
                        r (remove #(= % x) l)
                        ]
                    (p r (conj sofar x)
                       ))))))


(defn permute
  [l]
  (partition (count l) (p l [])))

(def perms  (permute people))

(defn calcsum
  [x]
  (reduce + (map #(m %) (partition 2 1 x)))
  )
(defn makecycle
  [x]
  (cons (last x) x)
  )

(defn calclength [item]
  (+ (calcsum (makecycle item))
     (calcsum (makecycle (reverse item)))
     ))

(def ans  (map calclength perms))
;; make a cycle for lengths (cons (last people) people)
;; then reverse
;; calc both lengths

;;(reduce + (map #(m % ) (partition 2 1 people)))
(print (apply max ans))
(print ans)
