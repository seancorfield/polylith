(ns polylith.clj.core.external-test-runner.interface
  (:require [polylith.clj.core.external-test-runner.core :as core]))

(defn create [opts]
  (core/create opts))
