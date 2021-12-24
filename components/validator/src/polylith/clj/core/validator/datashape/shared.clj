(ns polylith.clj.core.validator.datashape.shared
  (:refer-clojure :exclude [alias]))

(def alias [:map
            [:extra-paths {:optional true}
             [:vector string?]]
            [:extra-deps {:optional true} [:map-of symbol? :map]]])
