;;   Copyright (c) Dragan Djuric. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Dragan Djuric"}
    uncomplicate.neanderthal.integer-test
  (:refer-clojure :exclude [abs])
  (:require [midje.sweet :refer [facts throws =>]]
            [uncomplicate.neanderthal
             [core :refer :all]
             [real-test :refer [test-group test-vctr-swap test-vctr-copy test-ge-copy]]]
            [uncomplicate.neanderthal.internal.api :refer [data-accessor index-factory]])
  (:import clojure.lang.ExceptionInfo
           [uncomplicate.neanderthal.internal.api MatrixImplementation]))

(defn test-vctr-integer-entry [factory]
  (facts "Vector integer entry."
         (entry (vctr factory [1 2 3 4]) 1) => 2
         (entry (vctr factory []) 0) => (throws ExceptionInfo)))

(defn test-vctr-integer-entry! [factory]
  (facts "Vector integer entry!."
         (entry (entry! (vctr factory [1 2 3 4]) 1 77) 1) => 77))

(defn test-vctr-integer-bulk-entry! [factory]
  (facts "Vector integer entry!."
         (sum (entry! (vctr factory [1 2 3]) 77)) => 231))

(defn test-vctr-integer-alter! [factory]
  (facts "Vector integer alter!."
         (entry (alter! (vctr factory [1 2 3 4]) 1
                        (fn ^long [^long val] (inc val))) 1) => 3
         (alter! (vctr factory [1 2 3 4])
                 (fn ^long [^long i ^long val] (long (+ i val)))) => (vctr factory [1 3 5 7])))

(defn test-vctr-amax [factory]
  (facts "BLAS 1 vector amax."
         (amax (vctr factory [1 2 3 -4])) => 4
         (amax (vctr factory [])) => 0))

(defn test-ge-amax [factory]
  (facts "BLAS 1 GE amax."
         (amax (ge factory 2 4 [1 2 3 -7 -3 1 3 0])) => 7
         (amax (ge factory 0 0 [])) => 0))

(defn test-basic-integer [factory]
  (test-group factory)
  (test-vctr-swap factory)
  (test-vctr-copy factory))

(defn test-basic-integer-host [factory]
  (test-vctr-integer-entry factory)
  (test-vctr-integer-entry! factory)
  (test-vctr-integer-alter! factory)
  (test-vctr-amax factory)
  (test-ge-copy factory)
  (test-ge-amax factory))
