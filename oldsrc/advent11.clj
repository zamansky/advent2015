
;; (x - 97)+1


(def input "cqjxjnds")

(def i2(->> input
            (map #(- (int %) 97))))

(defn strtoints
  [x]
  (->> x (map #(- (int %) 97))))


(defn tento26list [x] (loop [x x
                             r [] ]
                        print x
                        (if (= 0 x)
                          r
                          (recur (quot x 26) (cons (mod x 26 ) r)))))

(defn nextcode [x]
  (->> x
       strtoints
       reverse
       (map-indexed (fn [a b] [a b]))
       (map (fn [[a b]] (* b (Math/pow 26 a ))))
       (apply +)
       long
       inc
       tento26list
       (map #(+ 97 %))
       (map  char)
       (apply str)
       
       ))

(defn inc3
  [[x y c]]
  (let [a (inc (inc x))
        b (inc y)
        ]
    (= a b c)))

(defn threep
  [input]
  (->> input
       strtoints
       (partition 3 1)
       (map inc3)
       (some true?)
       ))


(def blacklist #"i|o|l")
(defn notblacklist
  [input]
  (not (re-find blacklist input)))

;; two non overlapping pairs
(def pair #".*(.)\1.*(.)\2.*")
(defn haspair
  [input]
  (re-find pair input))

;; part 1
;; (take 1 (filter haspair (filter notblacklist (filter threep (iterate nextcode input)))))
