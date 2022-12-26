(require '[adventofcodeclojure.utils :as utils])
(use '[clojure.string :only (join split)])


(def words (utils/get-lines "advent5.txt"))



;; at least 3 vowels
;; one letter appearing twice

(defn threevowels
  [w]
  (let [vowels  #{\a \e \i \o \u}]
    (>= (count (filter (fn [x] (contains? vowels x)) w))3)))

(defn twice
  "returns 2 if the word has a letter appearing twice"
  [w]
  (some true? (map (fn [[a b]] (= a b))(map vector w (subs w 1)))))

(defn badstrings
  [w]
  (not(re-find #"ab|cd|pq|xy" w)))

(count
 (filter (fn [w]
           (and (threevowels w)
                (twice w)
                (badstrings w))) words))
(defn appearstwice
  [w]
  (re-find #".*(..).*\1" w))

(defn oneletterbetween
  [w]
  (re-find #".*(.).\1",w))

(count
 (filter (fn [w]
           (and (appearstwice w)
                (oneletterbetween w))) words))
