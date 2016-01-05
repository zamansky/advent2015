(require '[clojure.string :as String])
(defn parse-int [s] (Integer/parseInt s))

(def input (String/split  (slurp "advent14.txt") #"\n"))

(def splitter #"([A-Z][a-z]+) [a-z \/]+([0-9]+) [a-z \/]+([0-9]+) [a-z, ]+ ([0-9]+)")


(defn intify [x]
  "convert ints from strinnt to int in list"
  (map #(if (re-find #"\d+" %) (parse-int %)% ) x))


(def lines
  (->> input
       (map #(drop 1 (re-find splitter %)))
       (map  intify)
       (map  #(cons 0 %))
       ))




(def totaltime 2503)
(defn dist
  [ [name spd time rest]]
  (let [
        base (* (quot totaltime (+ time rest))
                (* spd time))
        resttime (mod totaltime (+ time rest))
        addontime (min resttime time)
        ]
    [name  (+ base (* addontime spd))] )
  )


(map dist lines)

(sort #(> (second %1) (second %2)) (map dist lines) )

lines
