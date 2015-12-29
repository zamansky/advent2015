(require '[adventofcodeclojure.utils :as utils])
(use '[clojure.string :only (join split)])

(import 'java.security.MessageDigest
        'java.math.BigInteger)

;; grabbed from gist
(defn md5 [s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
    (str padding sig)))

(def input "iwrupvqb")

(defn continue
  "true if first 5 chars are 0"
  [x]
  (not= "00000" (subs x 0 5)))

(def ans (count (take-while continue (map md5(map (fn [x] (str input x)) (range))))))
;(->> (range) (map (fn [x] (str input x))) (map md5) (take-while continue) count)

(defn continue6
  "true if first 6 chars are 0"
  [x]
  (not= "000000" (subs x 0 6)))

(def ans2 (count (take-while continue6 (map md5(map (fn [x] (str input x)) (range))))))
