(defproject yummly/schema "1.1.10.1-concurrency"
  :description "Clojure(Script) library for declarative data description and validation"
  :url "http://github.com/plumatic/schema"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.0"]
                                  [org.clojure/clojurescript "1.10.520"]
                                  [org.clojure/tools.nrepl "0.2.5"]
                                  [org.clojure/test.check "0.9.0"]
                                  [potemkin "0.4.1"]
                                  [com.sun.xml.bind/jaxb-core "2.2.11"]
                                  [javax.xml.bind/jaxb-api "2.2.12"]]
                   :plugins      [[com.keminglabs/cljx "0.6.0" :exclusions [org.clojure/clojure]]
                                  [codox "0.8.8"]
                                  [lein-cljsbuild "1.1.7"]
                                  [lein-release/lein-release "1.0.4"]
                                  [com.cemerick/clojurescript.test "0.3.3"]]
                   :cljx         {:builds [{:source-paths ["src/cljx"]
                                            :output-path  "target/generated/src/clj"
                                            :rules        :clj}
                                           {:source-paths ["src/cljx"]
                                            :output-path  "target/generated/src/cljs"
                                            :rules        :cljs}
                                           {:source-paths ["test/cljx"]
                                            :output-path  "target/generated/test/clj"
                                            :rules        :clj}
                                           {:source-paths ["test/cljx"]
                                            :output-path  "target/generated/test/cljs"
                                            :rules        :cljs}]}}
             :1.8 {:dependencies [[org.clojure/clojure "1.8.0"] [org.clojure/clojurescript "0.0-3308"]]}
             :1.9 {:dependencies [[org.clojure/clojure "1.9.0-alpha5"] [org.clojure/clojurescript "0.0-3308"]]}}

  :jvm-opts ["-Dclojure.spec.skip-macros=true"]

  :aliases {"all"    ["with-profile" "dev:dev,1.8:dev,1.9"]
            "deploy" ["do" "clean," "cljx" "once," "deploy" "clojars"]
            "test"   ["do" "clean," "cljx" "once," "test," "with-profile" "dev" "cljsbuild" "test"]}

  :jar-exclusions [#"\.cljx|\.swp|\.swo|\.DS_Store"]

  :lein-release {:deploy-via :shell
                 :shell      ["lein" "deploy"]}

  :auto-clean false

  :source-paths ["target/generated/src/clj" "src/clj"]

  :resource-paths ["target/generated/src/cljs"]

  :test-paths ["target/generated/test/clj" "test/clj"]

  :cljsbuild {:test-commands {"unit"           ["phantomjs" :runner
                                                "this.literal_js_was_evaluated=true"
                                                "target/unit-test.js"]
                              "unit-no-assert" ["phantomjs" :runner
                                                "this.literal_js_was_evaluated=true"
                                                "target/unit-test-no-assert.js"]}
              :builds
              {:dev            {:source-paths ["src/clj" "target/generated/src/cljs"]
                                :compiler     {:output-to     "target/main.js"
                                               :optimizations :whitespace
                                               :pretty-print  true}}
               :test           {:source-paths ["src/clj" "test/clj"
                                               "target/generated/src/cljs"
                                               "target/generated/test/cljs"]
                                :compiler     {:output-to     "target/unit-test.js"
                                               :optimizations :whitespace
                                               :pretty-print  true}}
               :test-no-assert {:source-paths ["src/clj" "test/clj"
                                               "target/generated/src/cljs"
                                               "target/generated/test/cljs"]
                                :assert       false
                                :compiler     {:output-to     "target/unit-test-no-assert.js"
                                               :optimizations :whitespace
                                               :pretty-print  true}}}}

  :codox {:src-uri-mapping           {#"target/generated/src/clj" #(str "src/cljx/" % "x")}
          :src-dir-uri               "http://github.com/plumatic/schema/blob/master/"
          :src-linenum-anchor-prefix "L"}
  :deploy-repositories [["releases" {:url "https://clojars.org/repo/" :sign-releases false}]
                        ["clojars" {:url "https://clojars.org/repo/" :sign-releases false}]]
  :repositories [["clojars" {:url "https://clojars.org/repo/" :sign-releases false}]]
  ;; :signing {:gpg-key "371F5787AAED559113CF32DDA25BB4189C3E659F"}
  )
