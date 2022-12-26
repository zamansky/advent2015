(use '[clojure.string :only (join split)])
(defn parse-int [s] (Integer/parseInt s))

(def input (split (slurp  "advent9.txt") #"\n" ))

(def one-way (for [ line input]
               (let [ [orig from to miles] (re-find #"(.*) to (.*) = (\d+)"line )
                     ]
                 

                 [from to (parse-int miles)]
                 )     ))

(def other (map (fn [ [a b c]] [b a c]) one-way))

(def f (concat one-way other))
(def m (map (fn [[a b c]] (hash-map a {b c})) f))

;; make an adjacency list
(def alist (reduce (fn [a b] (merge-with merge a b)) m))


(def solnset (clojure.set/union 
              (into #{} (map (fn [x] (first x)) f))
              (into #{} (map (fn [x] (second x)) f))))

(defn solver
  [alist remaining used currentpath routes]
  
  )

(solver alist solnset #{} [] [])


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


(def tmp (permute solnset))

(defn routelengths
  [routes]
  (for [rt routes]
    (let [r (partition 2 1 rt)]
      (->>
       (for [ [a b]  r]
         (get-in alist [a b] 1000000))
       (reduce +))
      )))

