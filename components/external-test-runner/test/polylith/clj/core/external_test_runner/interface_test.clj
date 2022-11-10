(ns polylith.clj.core.external-test-runner.interface-test
  (:require [clojure.test :as test :refer :all]
            [polylith.clj.core.external-test-runner.interface :as external-test-runner]))

(deftest dummy-test
  (is (= 1 1)))
