(ns unicorn-rainbow.reader
  (:require 
            [clojure.string]
            [clojure.walk]))

(defmacro 🦄🌈 [body]
  (eval 
   (clojure.walk/postwalk 
    (partial 
     (fn [emoji-pattern x]
       (if (seqable? x)
         ((fn [emoji-pattern coll]
            (let [
                  removed-in-coll (remove (partial (fn [emoji-pattern x]
                                                     (and 
                                                      (symbol? x) 
                                                      (boolean 
                                                       (re-find 
                                                        emoji-pattern 
                                                        (clojure.string/trim (str x))))))
                                                   emoji-pattern) coll)]
              (cond
                (map? coll) (into {} removed-in-coll)
                (set? coll) (into #{} removed-in-coll)
                (vector? coll) (into [] removed-in-coll)
                :else removed-in-coll))) 
          emoji-pattern
          x)
         x)) 
     (re-pattern (str "[" (apply str (slurp "emojis.txt")) "]"))) 
    body)))