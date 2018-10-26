(ns simplify)

;;tests
;;(def p1 '(and x (or x (and y (not z)))))
;;(evalexp p1 '{x false, z true})
;;(evalexp p1 '{z true})

;;(def p2 '(and (and z false) (or x true false)))
;;(evalexp p2 '{x false, z true})

;;(def p3 '(and x(or x (and y (not z)))))
;;(evalexp p3 '{x true, y true, z false})

(defn evalexp [exp bindings]
  (simplify (bind-values bindings exp)))

;(bind-values '{x false, z true} '(and x(or x(and y(not z)))))
(defn bind-values [binding exp]
  (map (fn [i]
         (if (seq? i)
           (bind-values binding i)
           (binding i i)))
       exp ))

(defn simplify [expression]
  (which-simplify
    (map (fn [i]
        (if(seq? i)
          (do
            (let[temp  (simplify i) ]
              temp))
          i))
      (distinct expression))))

(defn which-simplify [expression]
  (let [start (first expression)]
    (cond
      (= start 'or) (simplify-or expression)
      (= start 'and) (simplify-and expression)
      (= start 'not)  (simplify-not expression)
      :else (distinct expression))))

(defn simplify-or [expression]
  (let [start (first expression)
        after (rest expression)]
    (let [after-no-false (remove false? after)]
      (when (= start 'or)
        (cond
          (= 0  (count after-no-false)) false
          (= 1 (count after-no-false)) (first after)
          (some true? after) true
          :else (concat (list start) (distinct after-no-false)))))))

(defn simplify-and [expression]
  (let [start (first expression)
        after (rest expression)]
    (let [after-no-true (remove true? after)]
    (when (= start 'and)
      (cond
        (some false? after) false
        (= 1 (count after-no-true)) (first after)
        (= 0 (count after-no-true)) true
        :else (concat (list start) (distinct after-no-true)))))))

(defn simplify-not [expression]
  (let [start (first expression)
        after (rest expression)]
    (let [after-no-true (remove true? after)]
      (let [after-no-false (remove false? after)]
    (when (= start 'not)
      (cond
        (= 0 (count after-no-true)) false
        (= 0 (count after-no-false)) true
        :else (concat (list start) (distinct after))))))))