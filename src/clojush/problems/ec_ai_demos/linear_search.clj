;; squirrel_play.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Nic McPhee, mcphee@morris.umn.edu, 2016

(ns clojush.problems.ec-ai-demos.linear-search
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

;;;;;;;;;;;;
;; The squirrels in Palo Alto spend most of the day playing. In particular,
;; they play if the temperature is between 60 and 90 (inclusive). Unless it
;; is summer, then the upper limit is 100 instead of 90.
;; Given an int temperature and a boolean is_summer, return true if the
;; squirrels play and false otherwise.
;; Taken from CodingBat: http://codingbat.com/prob/p135815

(def input-set
  [
    [[7,5,4,8,1,2,3], 4]
    [[5,5,5,5,5,5,5,5,5], 2]
    [[8,3,0,5,8,9,4,4], 9]
    [[8,3,0,5,8,9,4,45,4,8686,454,765,85], 0]
    [[8,3,0,5,8,9,4,46,3,73,237,67,83,637], 1]
    [[3,3,3,3,3,3,3], 3]
    [[87,35,0,55,82,976,5354,674,56,45,24], 56]
    [[], 8]
    ])

;Looks to see if the target exists in elements
(defn expected-output-old
  [inputs]
  (let [[elements target] inputs]
    (if (< (.indexOf elements target) 0)
      false
      true)))

;Returns the index of an element in a vector, negative if nonexistent
(defn expected-output
  [inputs]
  (let [[elements target] inputs]
    (.indexOf elements target)))

; Make a new push state, and then add every
; input to the special `:input` stack.
; You shouldn't have to change this.
(defn make-start-state
  [inputs]
  (reduce (fn [state input]
            (push-item input :input state))
          (make-push-state)
          inputs))

; The only part of this you'd need to change is
; which stack(s) the return value(s) come from.
(defn actual-output
  [program inputs]
  (let [start-state (make-start-state inputs)
        end-state (run-push program start-state)
        result (top-item :integer end-state)]
    result))

;Checks actual versus expected and returns an error value
(defn all-errors
  [program]
  (doall
    (for [inputs input-set]
      (let [expected (expected-output inputs)
            actual (actual-output program inputs)]
        (if (= expected actual)
          0
          1)))))

(def atom-generators
  (concat
    ; Include all the instructions that act on integers and booleans
    ; Could have :exec here, but I just am limiting things to exec-if
    (registered-for-stacks [:integer :boolean :exec])
    ;(list 'exec_if)
    ; The two inputs
    (list 'in1 'in2)))

(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   :population-size 500
   })
