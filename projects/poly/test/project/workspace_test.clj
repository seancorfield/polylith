(ns project.workspace-test
  (:require [clojure.test :refer :all]
            [polylith.clj.core.change.interface :as change]
            [polylith.clj.core.common.interface :as common]
            [polylith.clj.core.deps.text-table.brick-deps-table :as brick-ifc-deps]
            [polylith.clj.core.deps.text-table.project-brick-deps-table :as brick-deps-table]
            [polylith.clj.core.deps.text-table.workspace-deps-table :as ws-ifc-deps-table]
            [polylith.clj.core.deps.text-table.project-deps-table :as ws-project-deps-table]
            [polylith.clj.core.lib.text-table.lib-table :as libs]
            [polylith.clj.core.user-input.interface :as user-input]
            [polylith.clj.core.workspace.interface :as ws]
            [polylith.clj.core.workspace.text-table.project-table :as project-table]
            [polylith.clj.core.workspace.text-table.ws-table :as ws-table]
            [polylith.clj.core.workspace-clj.interface :as ws-clj]
            [polylith.clj.core.ws-explorer.core :as ws-explorer]))

(defn workspace [& args]
  (-> (user-input/extract-params (concat ["info" (str "ws-dir:.") "color-mode:none" "since:0aaeb58"] args))
      ws-clj/workspace-from-disk
      ws/enrich-workspace
      change/with-changes))

(deftest project-table
  (is (= ["  project          alias  status   dev"
          "  ------------------------------   ---"
          "  api *            api     ---     ---"
          "  core *           core    ---     ---"
          "  deployer *       depl    ---     ---"
          "  poly *           poly    -x-     -x-"
          "  poly-migrator *  migr    ---     ---"
          "  development *    dev     x--     x--"]
         (project-table/table (workspace) false false))))

(deftest info
  (is (= ["  interface      brick             api  core  depl  poly  migr   dev"
          "  ------------------------------   ---------------------------   ---"
          "  api            api *             x--  ---   x--   ---   ---    xx-"
          "  change         change *          x--  x--   x--   xxx   ---    xx-"
          "  command        command *         ---  ---   ---   xxx   ---    xx-"
          "  common         common *          x--  x--   x--   x--   x--    x--"
          "  creator        creator *         ---  ---   ---   xxx   ---    xx-"
          "  deployer       deployer *        ---  ---   x--   ---   ---    x--"
          "  deps           deps *            x--  x--   x--   xxx   ---    xx-"
          "  file           file *            x--  x--   x--   xxx   x--    xx-"
          "  git            git *             x--  x--   x--   xxx   ---    xx-"
          "  help           help *            ---  ---   ---   x--   ---    x--"
          "  lib            lib *             x--  x--   x--   xxx   ---    xx-"
          "  migrator       migrator *        ---  ---   ---   ---   x--    xx-"
          "  path-finder    path-finder *     x--  x--   x--   xxx   ---    xx-"
          "  shell          shell *           x--  x--   x--   x--   ---    x--"
          "  test-helper    test-helper *     ---  ---   ---   -xx   ---    x--"
          "  test-runner    test-runner *     ---  ---   ---   xxx   ---    xx-"
          "  text-table     text-table *      x--  x--   x--   x--   ---    x--"
          "  user-config    user-config *     x--  x--   x--   x--   x--    x--"
          "  user-input     user-input *      x--  x--   x--   xxx   ---    xx-"
          "  util           util *            x--  x--   x--   xxx   x--    xx-"
          "  validator      validator *       x--  x--   x--   xxx   ---    xx-"
          "  version        version *         x--  ---   x--   x--   x--    x--"
          "  workspace      workspace *       x--  x--   x--   xxx   ---    xx-"
          "  workspace-clj  workspace-clj *   x--  ---   x--   xxx   ---    xx-"
          "  ws-explorer    ws-explorer *     x--  ---   x--   xxx   ---    xx-"
          "  ws-file        ws-file *         ---  ---   ---   xxx   ---    xx-"
          "  -              deployer-cli *    ---  ---   x--   ---   ---    x--"
          "  -              migrator-cli *    ---  ---   ---   ---   x--    x--"
          "  -              poly-cli *        ---  ---   ---   x--   ---    x--"]
         (ws-table/table (workspace) false false))))

(deftest libs
  (is (= ["                                                                                                        w   "
          "                                                                                                        o   "
          "                                                                                                        r  w"
          "                                                                                                        k  s"
          "                                                                                                     v  s  -"
          "                                                                                            d        a  p  e"
          "                                                                                            e        l  a  x"
          "                                                                                            p        i  c  p"
          "                                                                                            l        d  e  l"
          "                                                                                            o  d  f  a  -  o"
          "                                                                                            y  e  i  t  c  r"
          "                                                                                            e  p  l  o  l  e"
          "  library                       version  type      KB   api  core  depl  poly  migr   dev   r  s  e  r  j  r"
          "  ---------------------------------------------------   ---------------------------   ---   ----------------"
          "  me.raynes/fs                  1.4.6    maven     10   x-    x-    x-    x-    x-    x-    .  .  x  .  .  ."
          "  metosin/malli                 0.1.0    maven     27   x-    x-    x-    x-    --    x-    .  .  .  x  .  ."
          "  mount/mount                   0.1.16   maven      8   --    --    --    --    --    x-    .  .  .  .  .  ."
          "  mvxcvi/puget                  1.3.1    maven     15   x-    --    x-    x-    --    x-    .  .  .  .  .  x"
          "  org.clojure/clojure           1.10.1   maven  3,816   x-    x-    x-    x-    x-    x-    .  .  .  .  .  ."
          "  org.clojure/tools.deps.alpha  0.8.695  maven     46   x-    x-    x-    x-    --    x-    .  x  .  .  x  ."
          "  org.slf4j/slf4j-nop           1.7.25   maven      3   --    --    x-    x-    --    x-    .  .  .  .  .  ."
          "  slipset/deps-deploy           0.1.0    maven      2   --    --    x-    --    --    x-    x  .  .  .  .  ."]
         (libs/table (workspace) false))))

(deftest ifc-deps-table
  (is (= ["                                                                                      w      "
          "                                                                                      o      "
          "                                                     p     t  t     u                 r  w   "
          "                                                     a     e  e  t  s  u              k  s   "
          "                                                     t     s  s  e  e  s     v     w  s  -   "
          "                                d                 m  h     t  t  x  r  e     a     o  p  e   "
          "                       c     c  e                 i  -     -  -  t  -  r     l  v  r  a  x  w"
          "                    c  o  c  r  p                 g  f     h  r  -  c  -     i  e  k  c  p  s"
          "                    h  m  o  e  l                 r  i  s  e  u  t  o  i     d  r  s  e  l  -"
          "                    a  m  m  a  o  d  f     h     a  n  h  l  n  a  n  n  u  a  s  p  -  o  f"
          "                 a  n  a  m  t  y  e  i  g  e  l  t  d  e  p  n  b  f  p  t  t  i  a  c  r  i"
          "                 p  g  n  o  o  e  p  l  i  l  i  o  e  l  e  e  l  i  u  i  o  o  c  l  e  l"
          "  brick          i  e  d  n  r  r  s  e  t  p  b  r  r  l  r  r  e  g  t  l  r  n  e  j  r  e"
          "  -------------------------------------------------------------------------------------------"
          "  api            .  x  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  x  x  x  ."
          "  change         .  .  .  .  .  .  .  .  x  .  .  .  x  .  .  .  .  .  .  x  .  .  .  .  .  ."
          "  command        .  x  .  x  x  .  x  x  x  x  x  .  .  .  .  x  .  x  .  x  x  x  x  x  x  x"
          "  common         .  .  .  .  .  .  .  x  .  .  .  .  .  .  .  .  .  x  .  x  .  .  .  .  .  ."
          "  creator        .  .  .  x  .  .  .  x  x  .  .  .  .  .  t  .  .  .  .  x  .  .  .  .  .  ."
          "  deployer       x  .  .  .  .  .  .  x  .  .  .  .  .  x  .  .  .  .  .  .  .  x  .  .  .  ."
          "  deps           .  .  .  x  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  x  .  .  .  .  .  ."
          "  file           .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
          "  git            .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  x  .  .  .  .  .  ."
          "  help           .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  x  .  .  .  ."
          "  lib            .  .  .  x  .  .  .  x  .  .  .  .  .  .  t  .  x  x  .  x  .  .  .  .  .  ."
          "  migrator       .  .  .  x  .  .  .  x  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
          "  path-finder    .  .  .  .  .  .  .  x  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
          "  shell          .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  ."
          "  test-helper    .  .  x  .  .  .  .  x  .  .  .  .  .  .  .  .  .  x  x  .  .  .  .  .  .  ."
          "  test-runner    .  .  .  x  .  .  x  .  .  .  .  .  .  .  .  .  .  .  .  x  x  .  .  .  .  ."
          "  text-table     .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
          "  user-config    .  .  .  .  .  .  .  x  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
          "  user-input     .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
          "  util           .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  ."
          "  validator      .  .  .  x  .  .  x  .  .  .  .  .  x  .  .  .  .  .  .  x  .  .  .  .  .  ."
          "  version        .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  ."
          "  workspace      .  .  .  x  .  .  x  x  .  .  .  .  x  .  t  .  x  .  .  x  x  t  .  .  .  ."
          "  workspace-clj  .  .  .  x  .  .  .  x  x  .  x  .  x  .  .  .  .  x  .  x  x  x  .  .  .  ."
          "  ws-explorer    .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
          "  ws-file        .  .  .  .  .  .  .  x  x  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  ."
          "  deployer-cli   .  .  .  .  .  x  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  ."
          "  migrator-cli   .  .  .  .  .  .  .  x  .  .  .  x  .  .  .  .  .  .  .  .  .  .  .  .  .  ."
          "  poly-cli       .  .  x  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  x  .  .  .  .  .  ."]
         (ws-ifc-deps-table/table (workspace)))))

(deftest project-deps-table
  (let [ws (workspace)
        projects (:projects ws)
        project (common/find-project "poly" projects)]
    (is (= ["                                                                             w      "
            "                                                                             o      "
            "                                            p     t  t     u                 r  w   "
            "                                            a     e  e  t  s  u              k  s   "
            "                                            t     s  s  e  e  s     v     w  s  -   "
            "                                            h     t  t  x  r  e     a     o  p  e   "
            "                    c     c                 -     -  -  t  -  r     l  v  r  a  x  w"
            "                 c  o  c  r                 f     h  r  -  c  -     i  e  k  c  p  s"
            "                 h  m  o  e                 i  s  e  u  t  o  i     d  r  s  e  l  -"
            "                 a  m  m  a  d  f     h     n  h  l  n  a  n  n  u  a  s  p  -  o  f"
            "                 n  a  m  t  e  i  g  e  l  d  e  p  n  b  f  p  t  t  i  a  c  r  i"
            "                 g  n  o  o  p  l  i  l  i  e  l  e  e  l  i  u  i  o  o  c  l  e  l"
            "  brick          e  d  n  r  s  e  t  p  b  r  l  r  r  e  g  t  l  r  n  e  j  r  e"
            "  ----------------------------------------------------------------------------------"
            "  change         .  .  .  .  .  +  x  .  .  x  +  .  .  .  .  .  x  .  .  .  .  .  ."
            "  command        x  .  x  x  x  x  x  x  x  +  +  .  x  +  x  .  x  x  x  x  x  x  x"
            "  common         .  .  .  .  .  x  .  .  .  .  .  .  .  .  x  .  x  .  .  .  .  .  ."
            "  creator        -  -  x  -  -  x  x  -  -  -  +  t  -  -  +  -  x  -  -  -  -  -  -"
            "  deps           .  .  x  .  .  +  .  .  .  .  .  .  .  x  +  .  x  .  .  .  .  .  ."
            "  file           .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
            "  git            .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  x  .  .  .  .  .  ."
            "  help           .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  x  .  .  .  ."
            "  lib            -  -  x  -  -  x  -  -  -  -  -  t  -  x  x  -  x  -  -  -  -  -  -"
            "  path-finder    .  .  .  .  .  x  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
            "  shell          .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  ."
            "  test-helper    +  x  +  +  +  x  +  +  +  +  +  .  +  +  x  x  +  +  +  +  +  +  +"
            "  test-runner    .  .  x  .  x  +  .  .  .  +  .  .  .  +  +  .  x  x  .  .  .  .  ."
            "  text-table     .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
            "  user-config    .  .  .  .  .  x  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
            "  user-input     .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
            "  util           .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  ."
            "  validator      .  .  x  .  x  +  .  .  .  x  .  .  .  +  +  .  x  .  .  .  .  .  ."
            "  version        .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  ."
            "  workspace      -  -  x  -  x  x  -  -  -  x  -  t  -  x  +  -  x  x  t  -  -  -  -"
            "  workspace-clj  .  .  x  .  +  x  x  .  x  x  +  .  .  +  x  .  x  x  x  .  .  .  ."
            "  ws-explorer    .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  .  .  .  .  .  ."
            "  ws-file        .  .  .  .  .  x  x  .  .  .  +  .  .  .  .  .  +  .  x  .  .  .  ."
            "  poly-cli       +  x  +  +  +  +  +  +  +  +  +  .  +  +  +  x  x  +  +  +  +  +  +"]
           (ws-project-deps-table/table (workspace) project false)))))

(deftest project-and-brick-deps
  (let [{:keys [components projects] :as ws} (workspace)
        project (common/find-project "poly" projects)
        brick (common/find-component "workspace" components)]
    (is (= ["  used by  <  workspace  >  uses           "
            "  -------                   ---------------"
            "  command                   common         "
            "                            deps           "
            "                            file           "
            "                            path-finder    "
            "                            test-helper (t)"
            "                            text-table     "
            "                            util           "
            "                            validator      "
            "                            version (t)    "]
           (brick-deps-table/table ws project brick "none")))))

(deftest project-brick-deps
  (let [{:keys [components] :as ws} (workspace)
        brick (common/find-component "workspace" components)]
    (is (= ["  used by  <  workspace  >  uses           "
            "  -------                   ---------------"
            "  api                       common         "
            "  command                   deps           "
            "                            file           "
            "                            path-finder    "
            "                            test-helper (t)"
            "                            text-table     "
            "                            util           "
            "                            validator      "
            "                            version (t)    "]
           (brick-ifc-deps/table ws brick)))))

(deftest poly-project-deps
  (is (= {"change"        {:src  {:direct   ["git"
                                             "path-finder"
                                             "util"]
                                  :indirect ["file"
                                             "shell"]}
                           :test {:direct   ["git"
                                             "path-finder"
                                             "util"]
                                  :indirect ["file"
                                             "shell"]}}
          "command"       {:src  {:direct   ["change"
                                             "common"
                                             "creator"
                                             "deps"
                                             "file"
                                             "git"
                                             "help"
                                             "lib"
                                             "test-runner"
                                             "user-config"
                                             "util"
                                             "validator"
                                             "version"
                                             "workspace"
                                             "workspace-clj"
                                             "ws-explorer"
                                             "ws-file"]
                                  :indirect ["path-finder"
                                             "shell"
                                             "text-table"]}
                           :test {:direct   ["change"
                                             "common"
                                             "creator"
                                             "deps"
                                             "file"
                                             "git"
                                             "help"
                                             "lib"
                                             "test-runner"
                                             "user-config"
                                             "util"
                                             "validator"
                                             "version"
                                             "workspace"
                                             "workspace-clj"
                                             "ws-explorer"
                                             "ws-file"]
                                  :indirect ["path-finder"
                                             "shell"
                                             "text-table"]}}
          "common"        {:src  {:direct ["file"
                                           "user-config"
                                           "util"]}
                           :test {}}
          "creator"       {:src  {:direct   ["common"
                                             "file"
                                             "git"
                                             "util"]
                                  :indirect ["shell"
                                             "user-config"]}
                           :test {:direct   ["common"
                                             "file"
                                             "git"
                                             "test-helper"
                                             "util"]
                                  :indirect ["change"
                                             "command"
                                             "creator"
                                             "deps"
                                             "help"
                                             "lib"
                                             "path-finder"
                                             "shell"
                                             "test-runner"
                                             "text-table"
                                             "user-config"
                                             "user-input"
                                             "validator"
                                             "version"
                                             "workspace"
                                             "workspace-clj"
                                             "ws-explorer"
                                             "ws-file"]}}
          "deps"          {:src  {:direct   ["common"
                                             "text-table"
                                             "util"]
                                  :indirect ["file"
                                             "user-config"]}
                           :test {:direct   ["common"
                                             "text-table"
                                             "util"]
                                  :indirect ["file"
                                             "user-config"]}}
          "file"          {:src  {:direct ["util"]}
                           :test {}}
          "git"           {:src  {:direct ["shell"
                                           "util"]}
                           :test {:direct ["shell"
                                           "util"]}}
          "help"          {:src  {:direct ["util"
                                           "version"]}
                           :test {}}
          "lib"           {:src  {:direct ["common"
                                           "file"
                                           "text-table"
                                           "user-config"
                                           "util"]}
                           :test {:direct   ["common"
                                             "file"
                                             "test-helper"
                                             "text-table"
                                             "user-config"
                                             "util"]
                                  :indirect ["change"
                                             "command"
                                             "creator"
                                             "deps"
                                             "git"
                                             "help"
                                             "lib"
                                             "path-finder"
                                             "shell"
                                             "test-runner"
                                             "user-input"
                                             "validator"
                                             "version"
                                             "workspace"
                                             "workspace-clj"
                                             "ws-explorer"
                                             "ws-file"]}}
          "path-finder"   {:src  {:direct ["file"
                                           "util"]}
                           :test {:direct ["file"
                                           "util"]}}
          "poly-cli"      {:src  {:direct   ["command"
                                             "user-input"
                                             "util"]
                                  :indirect ["change"
                                             "common"
                                             "creator"
                                             "deps"
                                             "file"
                                             "git"
                                             "help"
                                             "lib"
                                             "path-finder"
                                             "shell"
                                             "test-runner"
                                             "text-table"
                                             "user-config"
                                             "validator"
                                             "version"
                                             "workspace"
                                             "workspace-clj"
                                             "ws-explorer"
                                             "ws-file"]}
                           :test {}}
          "shell"         {:src  {}
                           :test {}}
          "test-helper"   {:src  {:direct   ["command"
                                             "file"
                                             "user-config"
                                             "user-input"]
                                  :indirect ["change"
                                             "common"
                                             "creator"
                                             "deps"
                                             "git"
                                             "help"
                                             "lib"
                                             "path-finder"
                                             "shell"
                                             "test-runner"
                                             "text-table"
                                             "util"
                                             "validator"
                                             "version"
                                             "workspace"
                                             "workspace-clj"
                                             "ws-explorer"
                                             "ws-file"]}
                           :test {}}
          "test-runner"   {:src  {:direct   ["common"
                                             "deps"
                                             "util"
                                             "validator"]
                                  :indirect ["file"
                                             "path-finder"
                                             "text-table"
                                             "user-config"]}
                           :test {}}
          "text-table"    {:src  {:direct ["util"]}
                           :test {}}
          "user-config"   {:src  {:direct ["file"
                                           "util"]}
                           :test {}}
          "user-input"    {:src  {:direct ["util"]}
                           :test {:direct ["util"]}}
          "util"          {:src  {}
                           :test {}}
          "validator"     {:src  {:direct   ["common"
                                             "deps"
                                             "path-finder"
                                             "util"]
                                  :indirect ["file"
                                             "text-table"
                                             "user-config"]}
                           :test {:direct   ["common"
                                             "deps"
                                             "path-finder"
                                             "util"]
                                  :indirect ["file"
                                             "text-table"
                                             "user-config"]}}
          "version"       {:src  {}
                           :test {}}
          "workspace"     {:src  {:direct   ["common"
                                             "deps"
                                             "file"
                                             "path-finder"
                                             "text-table"
                                             "util"
                                             "validator"]
                                  :indirect ["user-config"]}
                           :test {:direct   ["common"
                                             "deps"
                                             "file"
                                             "path-finder"
                                             "test-helper"
                                             "text-table"
                                             "util"
                                             "validator"
                                             "version"]
                                  :indirect ["change"
                                             "command"
                                             "creator"
                                             "git"
                                             "help"
                                             "lib"
                                             "shell"
                                             "test-runner"
                                             "user-config"
                                             "user-input"
                                             "workspace"
                                             "workspace-clj"
                                             "ws-explorer"
                                             "ws-file"]}}
          "workspace-clj" {:src  {:direct   ["common"
                                             "file"
                                             "git"
                                             "lib"
                                             "path-finder"
                                             "user-config"
                                             "util"
                                             "validator"
                                             "version"]
                                  :indirect ["deps"
                                             "shell"
                                             "text-table"]}
                           :test {:direct   ["common"
                                             "file"
                                             "git"
                                             "lib"
                                             "path-finder"
                                             "user-config"
                                             "util"
                                             "validator"
                                             "version"]
                                  :indirect ["deps"
                                             "shell"
                                             "text-table"]}}
          "ws-explorer"   {:src  {:direct ["util"]}
                           :test {:direct ["util"]}}
          "ws-file"       {:src  {:direct   ["file"
                                             "git"
                                             "version"]
                                  :indirect ["shell"
                                             "util"]}
                           :test {}}}
         (ws-explorer/extract (workspace) ["projects" "poly" "deps"]))))

(deftest poly-project-src-paths
  (is (= ["bases/poly-cli/src"
          "components/change/src"
          "components/command/src"
          "components/common/src"
          "components/creator/resources"
          "components/creator/src"
          "components/deps/src"
          "components/file/src"
          "components/git/src"
          "components/help/src"
          "components/lib/src"
          "components/path-finder/src"
          "components/shell/src"
          "components/test-runner/src"
          "components/text-table/src"
          "components/user-config/src"
          "components/user-input/src"
          "components/util/src"
          "components/validator/src"
          "components/version/src"
          "components/workspace-clj/src"
          "components/workspace/src"
          "components/ws-explorer/src"
          "components/ws-file/src"]
         (ws-explorer/extract (workspace) ["projects" "poly" "paths" "src"]))))

(deftest poly-project-test-paths
  (is (= ["components/change/test"
          "components/command/test"
          "components/creator/test"
          "components/deps/test"
          "components/file/test"
          "components/git/test"
          "components/lib/test"
          "components/path-finder/test"
          "components/test-helper/src"
          "components/test-runner/test"
          "components/user-input/test"
          "components/util/test"
          "components/validator/test"
          "components/workspace-clj/test"
          "components/workspace/test"
          "components/ws-explorer/test"
          "components/ws-file/test"
          "projects/poly/test"]
         (ws-explorer/extract (workspace) ["projects" "poly" "paths" "test"]))))

(deftest poly-project-lib-imports
  (is (= {:src  ["clojure.java.io"
                 "clojure.java.shell"
                 "clojure.pprint"
                 "clojure.set"
                 "clojure.stacktrace"
                 "clojure.string"
                 "clojure.tools.deps.alpha"
                 "clojure.tools.deps.alpha.util.maven"
                 "clojure.walk"
                 "java.io"
                 "java.net"
                 "java.nio.file"
                 "java.util"
                 "malli.core"
                 "malli.error"
                 "me.raynes.fs"
                 "org.eclipse.aether.util.version"
                 "puget.printer"]
          :test ["clojure.test"
                          "clojure.tools.deps.alpha.util.maven"]}
         (ws-explorer/extract (workspace) ["projects" "poly" "lib-imports"]))))