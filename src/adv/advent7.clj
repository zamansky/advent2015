

(use '[clojure.string :only (join split)])
(defn parse-int [s] (Integer/parseInt s))

;; (split (slurp  "z.txt") #"\n" )




(defn parse-2op
  [s]
  (let [
        re #"(.+) ([A-Z]+) (.+)"
        [orig o1 cmd o2] (re-find re s)
        ]
    [cmd o1 o2]    
    ))

(defn parse-not
  [s]
  (let [
        re #"NOT (.+)"
        [orig o2] (re-find re s)
        ]
    ["NOT" o2]
    ))

(defn parse-val
  [s]
  (let [
        re #"([0-9]+)"
        [orig d] (re-find re s)
        ]
    ["VAL" (parse-int d)]
    ))
(defn parse-assign
  [s]
  (let [
        re #"([a-z0-9]+)"
        [orig d] (re-find re s)
        ]
    ["ASN" d]
    ))


(def mask16 2r000000000000000001111111111111111)


(defn do-not
  [solnset goal clist r]
  (let [ op (first r)
        result (cond
                 (contains? solnset op) (bit-not (second (solnset op)))
                 :else (bit-not (second ((process-command solnset clist op) op)))
                 )
        ]
    (bit-and mask16 result)
    ))

(defn do-2shift
  [op solnset goal clist r]
  (let [ [op1 op2] r
        result (cond
                 (contains? solnset op1)  (op (second (solnset op1))
                                              (parse-int op2))
                 :else (op (second ((process-command solnset clist op1) op1))
                           (parse-int op2))
                 )]
    (bit-and mask16 result)
    ))

(defn do-2op
  [op solnset goal clist r]
  (let [ [op1 op2] r
        result (cond 
                 (and (contains? solnset op1)
                      (contains? solnset op2)) (op (second (solnset op1))
                                                   (second (solnset op2)))
                 (contains? solnset op1)       (op (second (solnset op1))
                                                   (second ((process-command solnset clist op2 ) op2)))
                 (contains? solnset op2)       (op (second (solnset op2))
                                                   (second ((process-command solnset clist op1 ) op1)))
                 
                 :else (op (second ((process-command solnset clist op2) op2))
                           (second ((process-command solnset clist op1) op1))))
        ]
    (bit-and mask16 result)
    ))



(defn process-command
  [solnset clist goal]
  (let [ [cmd & r ] (clist goal)
        
        ]
    (cond
      (contains? solnset goal) solnset
      (= cmd "VAL") (do (print r)
                        (assoc solnset goal ["VAL" (first r)]))
      (= cmd "AND") (assoc solnset goal (do-2op bit-and solnset goal clist r))
      (= cmd "OR") (assoc solnset goal (do-2op bit-or solnset goal clist r))
      (= cmd "LSHIFT") (assoc solnset goal (do-2shift bit-shift-left solnset goal clist r))
      (= cmd "RSHIFT") (assoc solnset goal (do-2shift bit-shift-right solnset goal clist r))
      (= cmd "NOT") (assoc solnset goal (do-not solnset goal clist r))
      (= cmd "ASN") (assoc solnset goal ["VAL" (process-command solnset clist cmd)])
      )))

(defn parse-command
  [s]
  (cond
    (re-find #"AND|OR|LSHIFT|RSHIFT" s) (parse-2op  s)
    (re-find #"NOT" s) (parse-not s)
    (re-find #"\d+" s) (parse-val s)
    :else (parse-assign s)
    ))

(def input (map  (fn [x] (split x #" -> ")) (split (slurp "advent7.txt") #"\n")))

(def d(into {}
            (for [ [cmd dst] input]
              [dst (parse-command cmd)])))



