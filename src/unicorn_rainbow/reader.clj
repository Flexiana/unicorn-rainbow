(ns unicorn-rainbow.reader
  (:require 
            [clojure.java.io :as io]
            [clojure.string]
            [clojure.walk]))

(defn load-emojis [filename]
  (set (map str (with-open [rdr (io/reader filename)]
                  (doall (line-seq rdr))))))

(defn identify-emoji [emoji-pattern x]
  (and (symbol? x) (boolean (re-find emoji-pattern (clojure.string/trim (str x))))))

(defn remove-emojis-from-collection [coll emoji-pattern]
  (cond
    (map? coll) (into {} (remove (comp identify-emoji emoji-pattern second) coll))
    (set? coll) (into #{} (remove (partial identify-emoji emoji-pattern) coll))
    (vector? coll) (into [] (remove (partial identify-emoji emoji-pattern) coll))
    (list? coll) (into '() (remove (partial identify-emoji emoji-pattern) coll))
    :else (throw (IllegalArgumentException. "Unsupported collection type"))))

(defn remove-emojis2 [body emojis]
  (let [
        emoji-pattern (re-pattern (str "[" (apply str emojis) "]"))
        
        replace-emojis (fn [x]
                         (println x (seqable? x))
                         (if (seqable? x)
                           (remove-emojis-from-collection x emoji-pattern)
                           x))]
    (clojure.walk/postwalk replace-emojis body)
    ))

(defmacro ur [body]
  (let [emojis (load-emojis "emojis.txt")
        cleaned-body (remove-emojis2 body emojis)]
    (println (pr-str cleaned-body))
    (eval cleaned-body)))

(ur (+ 1 1 #_1 #_🌈 🌈))