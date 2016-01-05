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
       (map  #(cons 0 (  cons 0  %)))
       (map  vec) ))

(defn updatedist
  [ tick [score dst name spd time rest] ]
  (let [ period (+ time rest)
        into (mod (-  period tick) period) 
        addto (if (< time into) spd 0)
        ]
    addto
    ))

(def rudolph [0 0 "z" 10 5 8])



(let [totaltime 2503]
  (defn dist
    [ [score dst name spd time rest]]
    (let [
          base (* (quot totaltime (+ time rest))
                  (* spd time))
          resttime (mod totaltime (+ time rest))
          addontime (min resttime time)
          ]
      [name  (+ base (* addontime spd))] )
    )

  (first (sort #(> (second %1) (second %2)) (map dist lines) )))



