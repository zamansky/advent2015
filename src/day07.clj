(ns day07
    (:require
   [utils :as u]
   [hashp.core]
   [clojure.string :as str]
   [com.rpl.specter :as sp]
   [clojure.set :as set]
   [clojure.edn :as edn]
   [clojure.spec.alpha :as spec]
   [clojure.core.match :as match]
  ))


(def raw-data (u/load-data 2015 7))

(def raw-data-2 (slurp "data/day07-2.dat"))

(def raw-sample "123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i")

(defn parse [line]
  (let [l (str/split line #" ")]
    (match/match l
      [_ "->" _] {:op :set :val (read-string (l 0)) :result (l 2)}
      [_ "AND" _ "->" _] {:op :and :w1 (l 0) :w2 (l 2) :result (l 4)}
      [_ "OR" _ "->" _] {:op :or :w1 (l 0) :w2 (l 2) :result (l 4)}
      [_ "LSHIFT" _ "->" _] {:op :lshift :wire (l 0) :val (read-string (l 2)) :result (l 4)}
      [_ "RSHIFT" _ "->" _] {:op :rshift :wire (l 0) :val (read-string (l 2)) :result (l 4)}
      ["NOT" _ "->" _] {:op :not :wire (l 1) :result (l 3)}
      :else (str "Nope: " (apply str l)))))

(map parse (str/split-lines raw-sample))

(map parse (str/split-lines raw-data))

(comment
 val  ->  wire
wire  AND wire -> wire
 wire  OR wire -> wire
 wire LSHIFT VAL -> wire
 wire RSHIFT VAL -> wire
 NOT wire -> wire

  )


(defn process-op [state {:keys [op result] :as opcode}]
  (case op
  :set (cond (int? (:val opcode)) (assoc state result (:val opcode))
             (nil? (state (:val opcode))) state
             :else (assoc state result (state (:val opcode))))
  :and (let [left  (:w1 opcode)
             right (:w2 opcode)]
           (if (or (nil? (state left)) (nil? (state right)))
             state
             (assoc state result (bit-and (state left) (state right)))))
    :or (let [left (:w1 opcode)
              right (:w2 opcode)]
          (if (or (nil? (state left)) (nil? (state right)))
            state
            (assoc state result (bit-or (state left) (state right)))))
    :lshift (let [left (:wire opcode)
                  val  (:val opcode)]
              (if (nil? (state left))
                state
                (assoc state result (bit-shift-left (state left) val))))
    :rshift (let [left (:wire opcode)
                  val (:val opcode)]
              (if (nil? (state left))
                state
                (assoc state result (bit-shift-right (state left) val))))
    :not (let [left  (:wire opcode)]
           (if (nil? (state left))
             state
             (assoc state result  (bit-not  (state left)))))

    state)
)

(defn part1 [data target]
  (let [opcodes (map parse (str/split-lines data))]
    (loop [state {"zvariable" 1} opcodes opcodes]
    (if (and (> (count opcodes) 1) (nil?  (state target)))
      (let [newstate (reduce process-op state opcodes)
            newopcodes (filter #(nil? (newstate  (:result %))) opcodes)
            ]
        (recur  newstate newopcodes))
        state))))

(def z (part1 raw-data "a"))
(println (z "a"))
(get z "a")

(def z2 (part1 raw-data-2 "a"))
(get z2 "lx")

;; part1 3176
;; part2 14710

;; (Integer/toUnsignedString (Integer/remainderUnsigned 3176 (+ 32767 32768)   ))

