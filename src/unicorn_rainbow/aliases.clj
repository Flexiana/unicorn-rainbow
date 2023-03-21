(ns unicorn-rainbow.aliases
  (:require [clojure.set :as set]
            [clojure.string :as string]))

(defn wowsies [strinput] (println strinput))

(defn furry-add [& args] (apply + args))
(defn fluffy-subtract [& args] (apply - args))
(defn magical-multiply [& args] (apply * args))
(defn wobbly-divide [& args] (apply / args))

(defn jiggly-map [f coll] (map f coll))
(defn wiggly-filter [pred coll] (filter pred coll))
(defn sparkly-reduce [f coll] (reduce f coll))

(defn hoppy-first [coll] (first coll))
(defn wavy-last [coll] (last coll))
(defn squishy-rest [coll] (rest coll))

(defn bouncy-conj [coll x] (conj coll x))
(defn glittery-into [to from] (into to from))

(defn rainbow-merge [& maps] (apply merge maps))
(defn sunshine-union [& sets] (apply set/union sets))
(defn moonlight-intersection [& sets] (apply set/intersection sets))

(defn twirly-split [s sep] (string/split s sep))
(defn whirly-join [coll sep] (string/join sep coll))
(defn twinkly-replace [s match replacement] (string/replace s match replacement))
(defn starry-reverse [s] (apply str (reverse s)))
(defn dreamy-upper [s] (string/upper-case s))
