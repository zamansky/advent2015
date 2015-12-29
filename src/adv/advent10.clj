
(def input "1113222113")

(def r #"(\d)\1*")

(defn lands
  [s]
  (let [x (re-seq r s)]
    (apply str (mapcat #(str (count (first %)) (second %)) x))))

(print (count (first  (drop 40 (iterate lands input)))))
(print (count (first  (drop 50 (iterate lands input)))))

