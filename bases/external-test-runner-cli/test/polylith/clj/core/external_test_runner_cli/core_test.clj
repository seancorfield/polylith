(ns polylith.clj.core.external-test-runner-cli.core-test
  (:require [clojure.test :as test :refer :all]
            [polylith.clj.core.external-test-runner-cli.main :as main]))

(deftest dummy-test
  (is (= 1 1)))
