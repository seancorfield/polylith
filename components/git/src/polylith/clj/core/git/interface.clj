(ns polylith.clj.core.git.interface
  (:require [polylith.clj.core.git.core :as core]))

(def repo core/repo)

(defn is-git-repo? [ws-dir]
  (core/is-git-repo? ws-dir))

(defn init [ws-dir git-repo?]
  (core/init ws-dir git-repo?))

(defn add [ws-dir filename]
  (core/add ws-dir filename))

(defn current-branch []
  (core/current-branch))

(defn root-dir []
  (core/root-dir))

(defn latest-polylith-sha
  ([]
   (latest-polylith-sha (core/current-branch)))
  ([branch]
   (core/latest-polylith-sha branch)))

(defn sha [ws-dir since tag-patterns]
  (core/sha ws-dir since tag-patterns))

(defn diff
  "Lists the changed files that has occurred between two SHAs in git.
   If the workspace lives inside the git root in a separate directory,
   also remove the inner workspace directory from the diff."
  [ws-dir ws-local-dir sha1 sha2]
  (core/diff ws-dir ws-local-dir sha1 sha2))

(defn diff-command
  "Returns the git diff command used to perform the diff."
  [sha1 sha2]
  (core/diff-command sha1 sha2))
